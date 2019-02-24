import { Component, OnInit } from '@angular/core';

import { ContactDto } from '../../model/contact.model';
import { ContactService } from '../../service/contact.service';

@Component({
  selector: 'app-my-contact',
  templateUrl: './contact.component.html',
  styleUrls: [ './contact.component.less' ],
  providers: [ContactService]
})

export class ContactComponent implements OnInit {
  heroes: ContactDto[] = [];
  constructor(private contactService: ContactService) { }
  ngOnInit(): void {
    this.contactService.getHeroes().then(heroes => this.heroes = heroes.slice(1, 5));
  }
}
