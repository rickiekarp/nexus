package nexusmodel

import (
	"crypto/sha1"
	"encoding/hex"
	"fmt"
)

type FileStorageEventMessage struct {
	Id             *int64                                   `json:"id,omitempty"`
	Path           string                                   `json:"path,omitempty"`
	Name           string                                   `json:"name,omitempty"`
	Size           int64                                    `json:"size,omitempty"`
	Mtime          int64                                    `json:"mtime,omitempty"`
	Checksum       *string                                  `json:"checksum,omitempty"`
	Owner          *string                                  `json:"owner,omitempty"`
	Inserttime     *int64                                   `json:"inserttime,omitempty"`
	Lastupdate     *int64                                   `json:"lastupdate,omitempty"`
	AdditionalData *[]FileStorageAdditionalDataEventMessage `json:"additional_data,omitempty"`
}

type FileStorageAdditionalDataEventMessage struct {
	FilesId  *int64 `json:"file_id,omitempty"`
	Property string `json:"property,omitempty"`
	Value    string `json:"value,omitempty"`
}

func (k FileStorageEventMessage) Sha1() string {
	s := fmt.Sprintf("%d-%d", k.Size, k.Mtime)
	h := sha1.New()
	h.Write([]byte(s))
	sha1_hash := hex.EncodeToString(h.Sum(nil))
	return sha1_hash
}
