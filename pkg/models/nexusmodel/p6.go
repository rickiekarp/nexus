package nexusmodel

type P6Module struct {
	Name    string `yaml:"name"`
	Type    string `yaml:"type"`
	Enabled bool   `yaml:"enabled"`
	Observe string `yaml:"observe"`
	Event   string `yaml:"event"`
	Logfile string `yaml:"logfile"`
	Target  string `yaml:"target,omitempty"`
}
