package channel

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
	"time"

	"git.rickiekarp.net/rickie/home/internal/app/sysmon/utils"
	"git.rickiekarp.net/rickie/home/pkg/apm"
	"git.rickiekarp.net/rickie/home/pkg/monitoring/graphite"
	"github.com/sirupsen/logrus"
	"gopkg.in/yaml.v2"
)

var (
	WeatherChannel chan bool = make(chan bool)
)

type WeatherApiData struct {
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

type WeatherApi struct {
	GraphiteHost   string   `json:"graphitehost"`
	GraphitePort   int      `json:"graphiteport"`
	GraphitePrefix string   `json:"graphiteprefix"`
	ApiKey         string   `json:"apikey"`
	CityIds        []string `json:"cityids"`
	Interval       int      `json:"interval"`
}

var WeatherConf WeatherApi

func ScheduleWeatherUpdate() {

	err := readConfig("config/sysmon/weather.yaml")
	if err != nil {
		logrus.Error("There was an error reading the weather config")
		return
	}
	logrus.Info(WeatherConf)

	var apiUrls []string

	for _, value := range WeatherConf.CityIds {
		apiUrls = append(apiUrls, "http://api.openweathermap.org/data/2.5/weather?id="+value+"&appid="+WeatherConf.ApiKey+"&units=metric")
	}

	for {
		// blocks until one of its cases can run
		select {
		// exit goroutine if sysmonChannel is closed
		case <-WeatherChannel:
			fmt.Println("stopping ScheduleWeatherUpdate")
			return
		// make sure this case is executed every 10 seconds
		// do not use a time.Sleep here since it causes a SIGKILL if the SIGTERM takes too long
		case <-time.After(time.Duration(WeatherConf.Interval) * time.Minute):
			logrus.Info("Calling weather api")
			for idx, apiUrl := range apiUrls {
				fmt.Println(idx, apiUrl)

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

				res := WeatherApiData{}
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

				prefix := WeatherConf.GraphitePrefix + "." + res.Name
				SendMetric(metric, prefix)
			}

			apm.PrintMemUsage()
		}
	}
}

// readConfig reads the given config file
func readConfig(configFile string) error {

	// read configfile
	yamlFile, err := ioutil.ReadFile(configFile)
	if err != nil {
		logrus.Error("yamlFile.Get err: ", err)
		return err
	}

	// unmarshal config file depending on given configStruct
	err = yaml.Unmarshal(yamlFile, &WeatherConf)

	if err != nil {
		logrus.Error("Unmarshal failed: ", err)
		return err
	}

	return nil
}

// SendMetric sends a metric to graphite
func SendMetric(metric map[string]float64, prefix string) {

	graphiteClient := graphite.NewClient(WeatherConf.GraphiteHost, WeatherConf.GraphitePort, prefix, "tcp")

	// sends the given metric map to the configured graphite
	if err := graphiteClient.SendData(metric); err != nil {
		logrus.Error(fmt.Sprintf("Error sending metrics: %v", err))
	}
}

func StopWeatherMonitorEndpoint(w http.ResponseWriter, r *http.Request) {
	logrus.Print("called StopWeatherMonitorEndpoint")
	w.WriteHeader(200)
	w.Write([]byte("RestartLineEndpoint Response"))

	if !utils.IsChannelOpen(WeatherChannel) {
		logrus.Info("channel is already closed, ignoring")
		return
	}

	close(WeatherChannel)
}

func StartWeatherMonitorEndpoint(w http.ResponseWriter, r *http.Request) {
	logrus.Print("called StartWeatherMonitorEndpoint")
	w.WriteHeader(200)
	w.Write([]byte("StartWeatherMonitorEndpoint Response"))

	if utils.IsChannelOpen(WeatherChannel) {
		logrus.Info("channel open already")
		return
	}

	WeatherChannel = make(chan bool)
	go ScheduleWeatherUpdate()
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
