import { Component, QueryList, ViewChildren } from '@angular/core';
import { SatPopover } from '@ncstate/sat-popover';

interface MenuItem {
  val: string;
  children?: MenuItem[];
}

@Component({
  selector: 'app-menu-example',
  styleUrls: ['menu-example.component.scss'],
  template: `
    <button class="button-default" [satPopoverAnchorFor]="menu" (click)="menu.toggle()">
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

  /** For a simplified (ahem, lazy) demo, the menu has max depth of 2. */
  menuItems: MenuItem[] = [
    { val: 'Settings' },
    { val: 'Logout' },
  ];

  select(values: string) {
    this.selection = values;

    console.log(values)

    // close all popovers
    this.allPopovers.forEach(p => p.close());
  }



}