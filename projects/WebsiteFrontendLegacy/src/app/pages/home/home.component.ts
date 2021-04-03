import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-my-home',
  templateUrl: './home.component.html',
  styleUrls: [ './home.component.less' ]
})

export class HomeComponent implements OnInit {

  slides = [
    {'image': 'assets/images/projects/professional/soulworker/logo-wide.jpg'}, 
    {'image': 'assets/images/projects/professional/kuf2/logo-kuf2-wide.jpg'},
    {'image': 'assets/images/projects/professional/logo-lk.png'},
    {'image': 'assets/images/projects/professional/logo-bf.png'},
    {'image': 'assets/images/projects/professional/logo-e4k.png'},
    ];

  constructor(private router: Router, private titleService: Title) { }
  
  ngOnInit(): void {
    this.titleService.setTitle( "Home - Rickie Karp" );
  }
}
