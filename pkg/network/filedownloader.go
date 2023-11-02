package network

import (
	"io"
	"net/http"
	"os"

	"git.rickiekarp.net/rickie/home/pkg/config"
)

var (
	fullURLFile string
)

func DownloadFile(fileUrl string, targetFileName string) error {

	fullURLFile = config.FileStorageBaseUrl + "/" + fileUrl

	// Create blank file
	file, err := os.Create(targetFileName)
	if err != nil {
		return err
	}
	defer file.Close()

	client := http.Client{
		CheckRedirect: func(r *http.Request, via []*http.Request) error {
			r.URL.Opaque = r.URL.Path
			return nil
		},
	}

	resp, err := client.Get(fullURLFile)
	if err != nil {
		return err
	}
	defer resp.Body.Close()

	_, err = io.Copy(file, resp.Body)
	if err != nil {
		return err
	}

	return nil
}
