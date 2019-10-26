import { Component, OnInit, Input } from '@angular/core';
import { ContactDto } from '../../../model/contact.model';

@Component({
  selector: 'profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  @Input("avatarImage") avatarImage: string;
  @Input("role") role: string;
  @Input("location") location: string;
  @Input("contactInfo") contactInfo: ContactDto;

  constructor() { }

  ngOnInit() {
  }

}
