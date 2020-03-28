import { Component, OnInit } from '@angular/core';
import { IImage } from 'ng-simple-slideshow';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-my-home',
  templateUrl: './home.component.html',
  styleUrls: [ './home.component.less' ]
})

export class HomeComponent implements OnInit {

  imageUrls: (string | IImage)[] = [
    { url: 'assets/images/logo-kuf2.png', backgroundSize: 'contain', backgroundPosition: 'center', clickAction: () => this.router.navigate(['/projects/kingdomunderfire2']) },
    { url: 'assets/images/logo-lk.png', backgroundSize: 'contain', backgroundPosition: 'center', clickAction: () => this.router.navigate(['/projects/lordsandknights']) },
    { url: 'assets/images/logo-bf.png', backgroundSize: 'contain', backgroundPosition: 'center', clickAction: () => this.router.navigate(['/projects/bigfarm']) },
    { url: 'assets/images/logo-e4k.png', backgroundSize: 'contain', backgroundPosition: 'center', clickAction: () => this.router.navigate(['/projects/empirefourkingdoms']) }
  ];
  height: string = '300px';
  minHeight: string;
  arrowSize: string = '30px';
  showArrows: boolean = false;
  disableSwiping: boolean = false;
  autoPlay: boolean = true;
  autoPlayInterval: number = 3333;
  stopAutoPlayOnSlide: boolean = true;
  debug: boolean = false;
  backgroundSize: string = 'cover';
  backgroundPosition: string = 'center center';
  backgroundRepeat: string = 'no-repeat';
  showDots: boolean = false;
  dotColor: string = '#FFF';
  showCaptions: boolean = true;
  captionColor: string = '#FFF';
  captionBackground: string = 'rgba(0, 0, 0, .7)';
  lazyLoad: boolean = false;
  hideOnNoSlides: boolean = false;
  width: string = '100%';
  fullscreen: boolean = false;

  constructor(private router: Router, private titleService: Title) { }
  
  ngOnInit(): void {
    this.titleService.setTitle( "Home - Rickie Karp" );
  }
}
