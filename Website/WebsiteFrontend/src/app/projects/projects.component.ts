import { Component, OnInit } from '@angular/core';

import { Hero } from '../hero';
import { HeroService } from '../hero.service';

@Component({
  selector: 'app-my-project',
  templateUrl: './projects.component.html',
  styleUrls: [ './projects.component.css' ],
  providers: [HeroService]
})

export class ProjectsComponent implements OnInit {
  heroes: Hero[] = [];
  constructor(private heroService: HeroService) { }
  ngOnInit(): void {
    this.heroService.getHeroes()
      .then(heroes => this.heroes = heroes.slice(1, 5));
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
