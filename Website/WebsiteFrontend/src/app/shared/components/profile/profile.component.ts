import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  avatarImgSrc: string = 'assets/images/0.jpg';
  userName: string = 'Rickie Karp';
  userPost: string = 'DevOps Engineer';
  location: string = 'Karlsruhe';
  email: string = 'contact@rickiekarp.net'

  constructor() { }

  ngOnInit() {
  }

}
