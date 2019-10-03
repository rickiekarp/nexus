import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Http } from '@angular/http';
import { ContactDto } from '../model/contact.model';

@Injectable()
export class ContactService {
  private contactApiUrl = 'api/contact';

  constructor(private http: Http) { }

  getContactInformation() {
    return this.http.get(this.contactApiUrl);
  }

}
