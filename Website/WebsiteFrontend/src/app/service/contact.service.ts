import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import { ContactDto } from '../model/contact.model';

@Injectable()
export class ContactService {
  private greetingUrl = 'api/contact';

  constructor(private http: Http) { }

  getHeroes(): Promise<ContactDto[]> {
    return this.http.get(this.greetingUrl).toPromise()
      .then(response => response.json() as ContactDto[]).catch(this.handleError);
  }

  getHero(id: number): Promise<ContactDto> {
    return this.getHeroes()
               .then(heroes => heroes.find(hero => hero.id === id));
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }
}
