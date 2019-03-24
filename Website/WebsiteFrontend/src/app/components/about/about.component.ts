import { Component, OnInit } from '@angular/core';
import { SkillDto } from '../../model/skill.model';
import { ResumeDto } from '../../model/resume.model';
import { ResumeService } from '../../service/resume.service';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent implements OnInit {
  skillsInfo: SkillDto[] = [];
  experienceInfo: ResumeDto[] = [];
  educationInfo: ResumeDto[] = [];

  constructor(private resumeService: ResumeService) { }

  ngOnInit() {
    this.getExperience();
    this.getEducation();
    this.getSkills();

    console.log(this.experienceInfo)
  }

  getExperience(): void {
    this.resumeService.getAllExperience().subscribe(
      data => {
        let body = data.text()
        let dat = JSON.parse(body)
        this.experienceInfo = <ResumeDto[]> dat;
        console.log(this.experienceInfo);
              },
      error=> { 
                console.log("Error in recieving data"); 
              },
      ()   => {
                //console.log( "Test" );
              }
    );

  }

  getEducation(): void {
    this.resumeService.getAllEducation().subscribe(
      data => {
        let body = data.text()
        let dat = JSON.parse(body)
        this.educationInfo = <ResumeDto[]> dat;
              },
      error=> { 
                console.log("Error in recieving data"); 
              },
      ()   => {
                //console.log( "Test" );
              }
    );

  }

  getSkills(): void {
    this.resumeService.getAllSkills().subscribe(
      data => {
        let body = data.text()
        let dat = JSON.parse(body)
        this.skillsInfo = <SkillDto[]> dat;
              },
      error=> { 
                console.log("Error in recieving data"); 
              },
      ()   => {
                //console.log( "Test" );
              }
    );

  }
}
