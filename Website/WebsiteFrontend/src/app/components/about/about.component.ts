import { Component, OnInit } from '@angular/core';
import { SkillDto } from '../../model/skill.model';
import { ResumeService } from '../../service/resume.service';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent implements OnInit {
  skillsInfo: SkillDto[] = [];

  constructor(private resumeService: ResumeService) { }

  ngOnInit() {
    this.getHeroes();
  }

  getHeroes(): void {
    var temp = this.resumeService.getAll();
    this.resumeService.getAll().subscribe(
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
