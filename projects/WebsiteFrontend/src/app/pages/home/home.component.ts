import { Component, OnInit } from '@angular/core';
import { IImage } from 'ng-simple-slideshow';

@Component({
  selector: 'app-my-home',
  templateUrl: './home.component.html',
  styleUrls: [ './home.component.less' ]
})

export class HomeComponent implements OnInit {

  imageUrls: (string | IImage)[] = [
    { url: 'https://cdn.vox-cdn.com/uploads/chorus_image/image/56748793/dbohn_170625_1801_0018.0.0.jpg', caption: 'The first slide', href: '#config' },
    { url: 'https://cdn.vox-cdn.com/uploads/chorus_asset/file/9278671/jbareham_170917_2000_0124.jpg', clickAction: () => alert('custom click function') },
    { url: 'https://lh3.googleusercontent.com/JTz1xQWbQJCScoa0VEVFzp4PlTqr1vGIZc1Rtlo5vNGtgRx6_yjk-0j6Nca09YcXKWB35u1u1cvR1bj2Tb9F3WPGJsDmGTDaMuUFX3lVAwRmZh28F9p5zFl-E-pWe4reaV5ONR1macQHPGmVSHfDnR1-D4UR6eEBew6t8D7vRl4La-REBm1A6Hbj2y3adZpZMnzkGHmlmr_DYgtB09XY9T_HFkAiuGz2Eu7b8Ai6PCA10FGP0gWCbxR_OInHjNuJR6ZBiBRCsqhcMkAesTW98wHIENUBT3DdXPkTqrGRcUDJweupdONcIytPgSR05YPoTyKS3gJQS6wHtXICS9ASzkej4O68KDNZe1u5gRLVNeBY4nClLXfRtp3UjgNXNSZ-LL50FvPlODlYNQIlbvTAmpSp7YGhikJO26SyN1UeQ32BVo7pHUud3bPGKnTGjRVy_TKwLtwPpo8jiM6LomUSNF13tk2l-kujvfF-hcTvZ9YOl0j_3hIQpQWT7NRrl7c4ZFQKXpMyr2XYQBdTe73_5Nb22rLsXvOYHxKA41Ev', caption: 'Apple TV', href: 'https://www.apple.com/' },
    { url: 'assets/images/logo-bh.png', backgroundSize: 'contain', backgroundPosition: 'center' }
  ];
  height: string = '300px';
  minHeight: string;
  arrowSize: string = '30px';
  showArrows: boolean = true;
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
  captionBackground: string = 'rgba(0, 0, 0, .35)';
  lazyLoad: boolean = false;
  hideOnNoSlides: boolean = false;
  width: string = '100%';
  fullscreen: boolean = false;

  constructor() { }
  
  ngOnInit(): void {
  }
}
