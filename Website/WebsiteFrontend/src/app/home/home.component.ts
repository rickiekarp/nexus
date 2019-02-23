import { Component, OnInit } from '@angular/core';

import { ContactDto } from '../model/contact.model';
import { HeroService } from '../service/contact.service';

@Component({
  selector: 'app-my-home',
  templateUrl: './home.component.html',
  styleUrls: [ './home.component.less' ],
  providers: [HeroService]
})

export class HomeComponent implements OnInit {
  heroes: ContactDto[] = [];
  constructor(private heroService: HeroService) { }
  ngOnInit(): void {
    this.heroService.getHeroes().then(heroes => this.heroes = heroes.slice(1, 5));
  }
}
