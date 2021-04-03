import { Component, OnInit } from '@angular/core';
import { SkillDto } from '../../model/skill.model';
import { ResumeDto } from '../../model/resume.model';
import { ResumeService } from '../../service/resume.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-resume',
  templateUrl: './resume.component.html',
  styleUrls: ['./resume.component.less']
})
export class ResumeComponent implements OnInit {
  skillsInfo: SkillDto[] = [];
  experienceInfo: ResumeDto[] = [];
  educationInfo: ResumeDto[] = [];

  constructor(private resumeService: ResumeService, private titleService: Title) {  }

  ngOnInit() {
    this.titleService.setTitle( "Resume - Rickie Karp" );

    this.resumeService.getAllExperience().subscribe(result => {
      this.experienceInfo = result
    });
    this.resumeService.getAllEducation().subscribe(result => {
      this.educationInfo = result
    });
    this.resumeService.getAllSkills().subscribe(result => {
      this.skillsInfo = result 
    });
  }

}
