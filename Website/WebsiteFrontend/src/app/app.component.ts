import { Component } from '@angular/core';

import { AuthenticationService } from './service';
import { User } from './model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css', './app.component.less' ]
})
export class AppComponent {
  currentUser: User;

  constructor(
      private authenticationService: AuthenticationService
  ) {
      this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
  }
  
}
