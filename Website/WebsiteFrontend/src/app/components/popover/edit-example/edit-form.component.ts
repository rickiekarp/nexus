import { EventEmitter, Component, Input, Output, Optional, Host } from '@angular/core';
import { SatPopover } from '@ncstate/sat-popover';

@Component({
  selector: 'edit-form',
  styleUrls: ['edit-form.component.scss'],
  template: `
    <form (ngSubmit)="onSubmit()">
      <input type="text" name="value" [(ngModel)]="value">
    </form>
  `
})
export class EditForm {

  @Input() value = '';

  @Output() update = new EventEmitter<string>();

  constructor(@Optional() @Host() public popover: SatPopover) { }

  onSubmit() {
    this.update.next(this.value);
    if (this.popover) {
      this.popover.close();
    }
  }
}