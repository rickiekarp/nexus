import { Component } from '@angular/core';


@Component({
  selector: 'app-edit-example',
  styleUrls: ['edit-example.component.scss'],
  template: `
    Your character is: 
    <u class="editable" [satPopoverAnchor]="p" (click)="p.open()">
      {{ name }}
    </u>

    <sat-popover #p hasBackdrop forceAlignment>
      <edit-form (update)="name = $event"></edit-form>
    </sat-popover>
  `
})
export class EditExample {

  name = 'Bowser';

}

