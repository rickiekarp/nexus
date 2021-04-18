import { Component, OnInit } from '@angular/core';

import { ContactDto } from '../../model/contact.model';
import { ContactService } from '../../service/contact.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-my-contact',
  templateUrl: './contact.component.html',
  styleUrls: [ './contact.component.less' ],
  providers: [ContactService]
})
export class ContactComponent implements OnInit {
  contactInfo: ContactDto = new ContactDto();
  constructor(private contactService: ContactService, private titleService: Title) { }

  location: string = "Karlsruhe"
  avatarImage: string = 'assets/images/contact/0.jpg';
  role: string = 'DevOps Engineer';

  ngOnInit(): void {
    this.titleService.setTitle("Contact - Rickie Karp");

    this.contactService.getContactInformation().subscribe(
      data => {
        let bodyJson = JSON.parse(JSON.stringify(data))
        this.contactInfo = <ContactDto> bodyJson;
      },
      error=> { 
        console.log("Error in recieving data"); 
      },
      ()   => {
        //console.log( "Test" );
      }
    );
  }
}
