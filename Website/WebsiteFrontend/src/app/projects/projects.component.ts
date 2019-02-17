import { Component, OnInit } from '@angular/core';

import { Hero } from '../hero';
import { HeroService } from '../hero.service';
import { ProjectDto } from '../model/project.model'
import { Router,ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-my-project',
  templateUrl: './projects.component.html',
  styleUrls: [ './projects.component.css' ],
  providers: [HeroService]
})

export class ProjectsComponent implements OnInit {
  heroes: Hero[] = [];
  id: string;
  project: ProjectDto;

  constructor(
    private activatedroute:ActivatedRoute,
    private router: Router, 
    private heroService: HeroService) { }
    
  ngOnInit(): void {
    this.heroService.getHeroes().then(heroes => this.heroes = heroes.slice(1, 5));
    this.id=this.activatedroute.snapshot.params['id'];

    // temporary
    this.project = new ProjectDto();
    switch(this.id) {
      case 'lordsandknights': 
        this.project.name = 'Lords & Knights'
        this.project.description = 'Lords & Knights is a medieval strategy MMO game available for Browser, iOS and Android. Play with thousands of other lords, create massive armies and conquer hundreds of new castles. There is no limit in this game!'
        this.project.projectUrl = 'https://lordsandknights.com'
        this.project.projectImage = 'images/LordsandKnights-Logo.jpg'
        this.project.company = 'XYRALITY GmbH'
        this.project.projectDuration = 'February 2016 - June 2018'
        this.project.text = "During my time at Xyrality I mainly worked on the Lords & Knights game. This is a strategy game where players build up castles, recruit units and battle/conquer other players castles. My responsibility as a software engineering trainee was to support the development/testing process by implementing a good test automation strategy. This includes writing UI tests for Android/Browser/iOS and improving the continuous integration process during development. Additionally to the test automation I was also working on the design and implementation of a new web application."
        this.project.trailerUrl = 'https://www.youtube.com/embed/Le2zUxS1cH8'
        break;
      case 'bigfarm':
        this.project.name = 'Big Farm'
        this.project.description = 'Live an exciting life in the country with your own farm in Goodgame Big Farm! You’re in charge of planting and harvesting your fields and, of course, raising your own pigs and cows! You’ll also have to prove your business skills by selling your goods on the market. With your profits, you can keep upgrading and expanding your farm until you become the greatest farmer far and wide!'
        this.project.projectUrl = 'https://bigfarm.goodgamestudios.com'
        this.project.projectImage = 'images/BigFarm-Logo.jpg'
        this.project.projectDuration = 'October 2013 - February 2015'
        this.project.company = 'Goodgame Studios'
        this.project.text = "When I worked at Goodgame Studios I mainly worked on the BigFarm browser game. This is a farming simulator where players build up farms and produce/sell products. I was responsible for assuring the quality by testing the game, reporting bugs and helping my colleagues with questions in regards to software quality. Additionally to that I was also doing the following:"
        this.project.tasks = [ 
          'Maintaining the bug database',
          'Creating and improving test plans',
          'Ensuring optimal communication between QA and Development',
          'Giving feedback in regards to game design',
          'Writing test reports',
          'Creating and analysing test statistics'
        ]
        this.project.trailerUrl = 'https://www.youtube.com/embed/nUok2W5FWiQ'
      break;
      
    }
  }

  ngAfterViewInit() {
    // Hack: Scrolls to top of Page after page view initialized
    let top = document.getElementById('projects');
    if (top !== null) {
      top.scrollIntoView();
      top = null;
    }
  }

}
