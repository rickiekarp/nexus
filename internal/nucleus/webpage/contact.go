package webpage

import (
	"encoding/json"
	"net/http"
)

type Contact struct {
	Name     string `json:"name"`
	Email    string `json:"email"`
	Role     string `json:"role"`
	Location string `json:"location"`
}

func ServeContactInfo(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	contactInfo := Contact{
		Name:     "Rickie Karp",
		Email:    "contact@rickiekarp.net",
		Role:     "Software Developer",
		Location: "Karlsruhe",
	}
	json.NewEncoder(w).Encode(contactInfo)
}
