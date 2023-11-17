package webpage

import (
	"encoding/json"
	"net/http"
)

func ServeContactInfo(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	json.NewEncoder(w).Encode("ServeContactInfo")
}
