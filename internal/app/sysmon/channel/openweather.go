package channel

import (
	"encoding/json"
	"io/ioutil"
	"net/http"
	"time"

	"git.rickiekarp.net/rickie/home/internal/app/sysmon/config"
	"git.rickiekarp.net/rickie/home/internal/app/sysmon/utils"
	"git.rickiekarp.net/rickie/home/pkg/monitoring/graphite"
	"git.rickiekarp.net/rickie/home/pkg/profiler"
	"github.com/sirupsen/logrus"
)

var (
	WeatherChannel chan bool
)

type weatherApiData struct {
	Coord struct {
		Lon float64 `json:"lon"`
		Lat float64 `json:"lat"`
	} `json:"coord"`
	Weather []struct {
		ID          int    `json:"id"`
		Main        string `json:"main"`
		Description string `json:"description"`
		Icon        string `json:"icon"`
	} `json:"weather"`
	Base string `json:"base"`
	Main struct {
		Temp      float64 `json:"temp"`
		FeelsLike float64 `json:"feels_like"`
		TempMin   float64 `json:"temp_min"`
		TempMax   float64 `json:"temp_max"`
		Pressure  int     `json:"pressure"`
		Humidity  int     `json:"humidity"`
		SeaLevel  int     `json:"sea_level"`
		GrndLevel int     `json:"grnd_level"`
	} `json:"main"`
	Visibility int `json:"visibility"`
	Wind       struct {
		Speed float64 `json:"speed"`
		Deg   int     `json:"deg"`
		Gust  float64 `json:"gust"`
	} `json:"wind"`
	Clouds struct {
		All int `json:"all"`
	} `json:"clouds"`
	Dt  int `json:"dt"`
	Sys struct {
		Type    int    `json:"type"`
		ID      int    `json:"id"`
		Country string `json:"country"`
		Sunrise int    `json:"sunrise"`
		Sunset  int    `json:"sunset"`
	} `json:"sys"`
	Timezone int    `json:"timezone"`
	ID       int    `json:"id"`
	Name     string `json:"name"`
	Cod      int    `json:"cod"`
}

func ScheduleWeatherUpdate() {

	logrus.Info("Loading weather monitoring config")
	err := config.ReadWeatherConfig()
	if err != nil {
		logrus.Error("There was an error reading the weather config")
		return
	}

	if !config.WeatherConf.Enabled {
		logrus.Info("Weather monitoring is disabled!")
		return
	}

	logrus.Info(config.WeatherConf)

	var apiUrls []string
	for _, value := range config.WeatherConf.CityIds {
		apiUrls = append(apiUrls, "http://api.openweathermap.org/data/2.5/weather?id="+value+"&appid="+config.WeatherConf.ApiKey+"&units=metric")
	}

	if len(apiUrls) == 0 {
		logrus.Warn("Could not find any apis to call! Exiting ScheduleWeatherUpdate!")
		return
	}

	WeatherChannel = make(chan bool)

	for {
		// blocks until one of its cases can run
		select {
		// exit goroutine if sysmonChannel is closed
		case <-WeatherChannel:
			logrus.Info("stopping ScheduleWeatherUpdate")
			return
		// make sure this case is executed every 10 seconds
		// do not use a time.Sleep here since it causes a SIGKILL if the SIGTERM takes too long
		case <-time.After(time.Duration(config.WeatherConf.Interval) * time.Minute):
			logrus.Info("Calling weather api")
			for _, apiUrl := range apiUrls {

				resp, err := http.Get(apiUrl)
				if err != nil {
					logrus.Error(err)
					continue
				}

				body, err := ioutil.ReadAll(resp.Body)
				if err != nil {
					logrus.Error(err)
					continue
				}

				res := weatherApiData{}
				err = json.Unmarshal([]byte(body), &res)
				if err != nil {
					logrus.Error("Could not unmarshal weather data! ", err)
					continue
				}

				logrus.Info(res)

				metric := map[string]float64{
					"main.temp":       res.Main.Temp,
					"main.feels_like": res.Main.FeelsLike,
					"main.temp_min":   res.Main.TempMin,
					"main.temp_max":   res.Main.TempMax,
					"main.pressure":   float64(res.Main.Pressure),
					"main.humidity":   float64(res.Main.Humidity),
					"main.sea_level":  float64(res.Main.SeaLevel),
					"main.grnd_level": float64(res.Main.GrndLevel),
					"visibility":      float64(res.Visibility),
					"wind.speed":      float64(res.Wind.Speed),
					"wind.deg":        float64(res.Wind.Deg),
					"wind.gust":       float64(res.Wind.Gust),
					"clouds.all":      float64(res.Clouds.All),
				}

				prefix := config.WeatherConf.GraphitePrefix + "." + res.Name
				graphite.SendMetric(metric, prefix)
			}

			profiler.PrintMemUsage()
		}
	}
}

func StopWeatherMonitorEndpoint(w http.ResponseWriter, r *http.Request) {
	logrus.Print("called StopWeatherMonitorEndpoint")
	w.WriteHeader(200)

	if !utils.IsChannelOpen(WeatherChannel) {
		logrus.Info("channel is closed, ignoring")
		return
	}

	close(WeatherChannel)
}

func StartWeatherMonitorEndpoint(w http.ResponseWriter, r *http.Request) {
	logrus.Print("called StartWeatherMonitorEndpoint")

	if utils.IsChannelOpen(WeatherChannel) {
		logrus.Info("channel open, ignoring")
		return
	}

	if config.WeatherConf.Enabled {
		WeatherChannel = make(chan bool)
		go ScheduleWeatherUpdate()
		w.WriteHeader(200)
		w.Write([]byte("started"))
	} else {
		w.WriteHeader(202)
		w.Write([]byte("feature disabled"))
	}

}

func WeatherMonitorStatusEndpoint(w http.ResponseWriter, r *http.Request) {
	logrus.Print("called WeatherMonitorStatusEndpoint")
	w.WriteHeader(200)

	if utils.IsChannelOpen(WeatherChannel) {
		w.Write([]byte("true"))
	} else {
		w.Write([]byte("false"))
	}
}
