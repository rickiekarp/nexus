import { Component, OnInit } from '@angular/core';
import { SkillDto } from '../../model/skill.model';
import { ResumeDto } from '../../model/resume.model';
import { ResumeService } from '../../service/resume.service';
import { ResumeData } from '../../model/resumedata.model';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent implements OnInit {
  skillsInfo: SkillDto[] = [];
  experienceInfo: ResumeData = {} as ResumeData;
  educationInfo: ResumeDto[] = [];

  constructor(private resumeService: ResumeService) {
   }

  ngOnInit() {
    this.resumeService.getExistingArsByLab().subscribe(result => {
      console.log(result)
    });
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
