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

  constructor(private resumeService: ResumeService) {
   }

  ngOnInit() {
    this.resumeService.getAllExperience().subscribe(result => {
      this.experienceInfo = result
      console.log(this.experienceInfo)
    });
    this.resumeService.getAllEducation().subscribe(result => {
      this.educationInfo = result
    });
    this.resumeService.getAllSkills().subscribe(result => {
      this.skillsInfo = result 
    });
  }

}
