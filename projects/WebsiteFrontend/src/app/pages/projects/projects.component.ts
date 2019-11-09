import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-my-projects',
  templateUrl: './projects.component.html',
  styleUrls: [ './projects.component.less' ]
})

export class ProjectsComponent implements OnInit {
  constructor(private titleService: Title) { }
  ngOnInit(): void {
    this.titleService.setTitle("Projects - Rickie Karp");
  }
}