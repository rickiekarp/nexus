import { Component, QueryList, ViewChildren } from '@angular/core';
import { SatPopover } from '@ncstate/sat-popover';
import { Router } from '@angular/router';

import { AuthenticationService } from '../../../../service';
import { User } from '../../../../model';

interface MenuItem {
  val: string;
  children?: MenuItem[];
}

@Component({
  selector: 'app-profile-popover',
  styleUrls: ['profile-popover.component.scss'],
  template: `
    <button class="button-default" [satPopoverAnchor]="menu" (click)="menu.toggle()">
     <i class="fa fa-user fa-2x" aria-hidden="true"></i>
    </button>

    <sat-popover #menu verticalAlign="below" hasBackdrop>
    <div>
      <div class="menu-wrapper" tabIndex="0">
      <button *ngFor="let item of menuItems"
          class="menu-item" (click)="item.children ? child.toggle() : select(item.val)">
        {{ item.val }}
        </button>
      </div>
	  </div>
    </sat-popover>
  `
})
export class MenuExample {
  @ViewChildren(SatPopover) allPopovers: QueryList<SatPopover>;
  selection: string;
  currentUser: User;  
  menuItems: MenuItem[] = [
    { val: 'Settings' },
    { val: 'Logout' },
  ];

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService
) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
}

  select(values: string) {
    this.selection = values;

    switch(this.selection) { 
      case "Settings": { 
        this.router.navigate(['userarea']);
         break; 
      } 
      case "Logout": { 
        this.logout()
        break; 
      } 
      default: { 
         break; 
      } 
   } 

    // close all popovers
    this.allPopovers.forEach(p => p.close());
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }
}