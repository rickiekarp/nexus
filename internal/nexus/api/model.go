package api

type Version struct {
	Version          string `json:"version"`
	Build            string `json:"build"`
	MinClientVersion string `json:"minClientVersion"`
}
