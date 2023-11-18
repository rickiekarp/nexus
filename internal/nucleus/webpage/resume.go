package webpage

import (
	"encoding/json"
	"net/http"
)

func ServeExperience(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	b := [5]Education{
		{
			Id:        8,
			StartDate: "2022-07-01",
			EndDate:   "",
			Name:      "Gameforge",
			JobTitle:  "Software Developer",
			Description: EducationDescription{Description: "At Gameforge I am currently involved in",
				Tasks: []string{
					"Implementing UI and gameplay features for OGame Mobile (Unity)",
					"Designing layout, logic and flow for mobile game user interfaces",
				}},
		},
		{
			Id:        7,
			StartDate: "2018-12-01",
			EndDate:   "2022-06-30",
			Name:      "Gameforge",
			JobTitle:  "DevOps Engineer",
			Description: EducationDescription{Description: "At Gameforge I have been involved in",
				Tasks: []string{
					"Operation and maintenance of the payment system, the company's own and company-wide platform for payment processing",
					"Optimization of the system in terms of performance, stability, reliability, availability and security",
					"Identify, analyze, and coordinate on issues with external payment providers and games",
					"Development of quick problem solutions (repair scripts, hotfixes, etc.) and sustainable strategies",
					"Technical configuration management of the systems with Ansible",
					"Technical release management through optimized processes/scripts and CD pipelines",
					"Co-design of the technical oepration of the payment system and the various processes",
					"Further development, definition and optimization of the monitoring and supervision of the payment system for the timely detection of problems and errors",
					"Coordination and communication in case of technical problems and emergencies (incident management)",
					"Support in optimizing data sets and ensuring data consistency",
					"24/7 on-call service to ensure system availability",
					"Internal and external technical contact for the operation of the payment system, including, among others, third-level support for billing support and developers",
					"Participation in internal projects and consulting for development and operations on system and application-related topics",
					"Development of CI/CD pipelines",
					"Development of sustainable anti-fraud measures and support of the corresponding specialist department",
					"Creation of technical documentation and reference prototypes for external developer studios for the processing of payments on mobile devices",
					"Implementation of UI and gameplay features for OGame Mobile (Unity)",
				}},
		},
		{
			Id:        6,
			StartDate: "2018-08-01",
			EndDate:   "2018-11-30",
			Name:      "HanseMerkur Insurance Group",
			JobTitle:  "Software Development Engineer",
			Description: EducationDescription{Description: "While working for HanseMerkur I",
				Tasks: []string{
					"Developed features for a REST/JMS based microservice architecture",
					"Prototyped a new Java/Angular Enterprise application",
					"Profiled and improved backend/frontend performance in multiple different subsystems",
					"Decoupled dependencies to allow a quicker modernization of the technology stack. Achieved removal of dependency on the Eclipse IDE",
					"Optimized software build and continuous integration processes",
				}},
		},
		{
			Id:        5,
			StartDate: "2016-02-01",
			EndDate:   "2018-07-31",
			Name:      "XYRALITY GmbH",
			JobTitle:  "Software Engineering Trainee",
			Description: EducationDescription{Description: "At Xyrality I",
				Tasks: []string{
					"Developed tests for Android, iOS and Browser game clients",
					"Created and maintained Jenkins build scripts",
					"Maintained Jenkins slave machines for continuous integration",
					"Led the test automation project which included evaluating and prioritizing software requirements and creating tasks for individual developers",
					"Implemented a Spring based REST service",
					"Designed and implemented a Spring/Hibernate based web application using Java/Kotlin and the Vaadin Framework",
				}},
		},
		{
			Id:        4,
			StartDate: "2013-10-01",
			EndDate:   "2015-02-28",
			Name:      "Goodgame Studios",
			JobTitle:  "Quality Assurance Engineer",
			Description: EducationDescription{Description: "During my time at Goodgame Studios I",
				Tasks: []string{
					"Tested alpha versions of games and company-internal systems",
					"Supervised a team of up to 30 testers (internal and external)",
					"Created and improved test plans",
					"Prepared QA reports for project management",
					"Analyzed test processes and realized process improvements",
					"Maintained the Atlassian Jira bug database",
					"Provided feedback in regards to game design and gameplay",
				}},
		},
	}

	json.NewEncoder(w).Encode(b)
}

type Education struct {
	Id          int                  `json:"id"`
	StartDate   string               `json:"startDate"`
	EndDate     string               `json:"endDate"`
	Name        string               `json:"name"`
	JobTitle    string               `json:"jobTitle"`
	Description EducationDescription `json:"description"`
}

type EducationDescription struct {
	Description string   `json:"description"`
	Tasks       []string `json:"tasks"`
}

func ServeEducation(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	b := [3]Education{
		{
			Id:          3,
			StartDate:   "2015-08-01",
			EndDate:     "2016-02-01",
			Name:        "University of Hamburg",
			JobTitle:    "Computer Science",
			Description: EducationDescription{Description: "Attended computer science classes for one semester", Tasks: []string{}},
		},
		{
			Id:          2,
			StartDate:   "2011-08-01",
			EndDate:     "2012-06-01",
			Name:        "Vocational School Elmshorn",
			JobTitle:    "Berufsoberschule (Field of studies - Economics)",
			Description: EducationDescription{Description: "General qualification for university entrance", Tasks: []string{}},
		},
		{
			Id:          1,
			StartDate:   "2009-08-01",
			EndDate:     "2011-06-01",
			Name:        "Vocational School Elmshorn",
			JobTitle:    "Berufsfachschule (Field of studies - Foreign languages)",
			Description: EducationDescription{Description: "University of applied sciences entrance qualification", Tasks: []string{}},
		},
	}

	json.NewEncoder(w).Encode(b)
}

type Skill struct {
	Id   int    `json:"id"`
	Text string `json:"text"`
}

func ServeSkills(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	b := [27]Skill{
		{Id: 1, Text: "Java"},
		{Id: 2, Text: "Kotlin"},
		{Id: 3, Text: "Android"},
		{Id: 4, Text: "Git"},
		{Id: 6, Text: "Docker"},
		{Id: 7, Text: "DevOps"},
		{Id: 8, Text: "Kubernetes"},
		{Id: 9, Text: "Ansible"},
		{Id: 12, Text: "Angular"},
		{Id: 13, Text: "MySQL"},
		{Id: 14, Text: "Nginx"},
		{Id: 15, Text: "Apache"},
		{Id: 16, Text: "Spring Boot"},
		{Id: 17, Text: "Bash"},
		{Id: 19, Text: "PHP"},
		{Id: 20, Text: "Golang"},
		{Id: 21, Text: "Jenkins"},
		{Id: 22, Text: "Linux"},
		{Id: 23, Text: "Grafana"},
		{Id: 24, Text: "Graylog"},
		{Id: 25, Text: "Icinga"},
		{Id: 26, Text: "Sonarqube"},
		{Id: 27, Text: "Graphite"},
		{Id: 28, Text: "Azure"},
		{Id: 29, Text: "Airflow"},
		{Id: 30, Text: "Unity"},
		{Id: 31, Text: "C#"},
	}

	json.NewEncoder(w).Encode(b)
}
