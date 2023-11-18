package users

import (
	"encoding/json"
	"net/http"
)

func Login(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	contactInfo := Contact{
		Name:     "Rickie Karp",
		Email:    "contact@rickiekarp.net",
		Role:     "Software Developer",
		Location: "Karlsruhe",
	}
	json.NewEncoder(w).Encode(contactInfo)
}
