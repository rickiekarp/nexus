import { Component, OnInit } from '@angular/core';

import { ContactDto } from '../../model/contact.model';
import { ContactService } from '../../service/contact.service';

@Component({
  selector: 'app-my-contact',
  templateUrl: './contact.component.html',
  styleUrls: [ './contact.component.scss' ],
  providers: [ContactService]
})
export class ContactComponent implements OnInit {
  contactInfo: ContactDto = new ContactDto();
  constructor(private contactService: ContactService) { }

  location: string = "Karlsruhe"
  avatarImage: string = 'assets/images/0.jpg';
  role: string = 'DevOps Engineer';

  ngOnInit(): void {

    this.contactService.getContactInformation().subscribe(
      data => {
        let body = data.text()
        let dat = JSON.parse(body)
        this.contactInfo = <ContactDto> dat;
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
