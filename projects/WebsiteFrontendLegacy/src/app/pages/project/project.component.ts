import { Component, OnInit } from '@angular/core';

import { ProjectDto } from '../../model/project.model'
import { Router,ActivatedRoute } from '@angular/router';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-my-project',
  templateUrl: './project.component.html',
  styleUrls: [ './project.component.less' ],
  providers: []
})

export class ProjectComponent implements OnInit {
  id: string;
  project: ProjectDto;

  height: string = '600px';
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
  captionBackground: string = 'rgba(0, 0, 0, .7)';
  lazyLoad: boolean = false;
  hideOnNoSlides: boolean = false;
  width: string = '100%';
  imageUrls = [];

  constructor(
    private activatedroute:ActivatedRoute,
    private router: Router,
    private titleService: Title) { }
    
  ngOnInit(): void {
    this.id=this.activatedroute.snapshot.params['id'];

    // temporary
    this.project = new ProjectDto();
    switch(this.id) {

      // professional projects begin here
      case 'gujian':
      this.project.name = 'Swords of Legends Online'
      this.project.description = 'Swords of Legends Online is an action MMORPG set in a breathtaking fantasy world with sophisticated combat mechanics and a unique storyline based on Chinese mythology.'
      this.project.projectUrl = 'https://gameforge.com/play/swords_of_legends_online'
      this.project.projectImage = "images/projects/professional/" + this.id + "/logo-wide.jpg"
      this.project.company = 'Gameforge'
      this.project.function = 'DevOps Engineer'
      this.project.projectDuration = '2020'
      this.project.text = "Responsibilities in the project:"
      this.project.tasks = [
        'Support integration into Gameforge platform',
        'Prepared and deployed shop for pre-order and release bundles'
      ]
      this.project.trailerUrl = 'https://www.youtube.com/embed/ak4Fx1UoWeQ'
    break;
      case 'soulworkeranimelegends': 
        this.project.name = 'Soulworker Anime Legends'
        this.project.description = '💎Enjoy one of the best anime style multiplayer action RPG’s of 2020💎'
        this.project.projectUrl = 'https://gameforge.com/play/soulworker_anime_legends'
        this.project.projectImage = 'images/projects/professional/soulworker/logo-wide.jpg'
        this.project.company = 'Gameforge'
        this.project.function = 'DevOps Engineer'
        this.project.projectDuration = '2020'
        this.project.text = "Responsibilities in the project:"
        this.project.tasks = [
          'Support integration into Gameforge platform',
          'Prepared and deployed shop for pre-order and release bundles'
        ]
        this.project.trailerUrl = 'https://www.youtube.com/embed/4_q64ahk-aQ'
      break;
      case 'kingdomunderfire2': 
        this.project.name = 'Kingdom Under Fire 2'
        this.project.description = 'Kingdom Under Fire 2 is an action-packed MMORPG/RTS hybrid. Choose one of five heroes, explore a fantasy world and hone your skills until you and your troops are ready for the epic-scale battles of this colossal war!'
        this.project.projectUrl = 'https://gameforge.com/play/kingdom_under_fire_2'
        this.project.projectImage = 'images/projects/professional/kuf2/logo-kuf2-wide.jpg'
        this.project.company = 'Gameforge'
        this.project.function = 'DevOps Engineer'
        this.project.projectDuration = '2019'
        this.project.text = "Responsibilities in the project:"
        this.project.tasks = [
          'Support integration into Gameforge platform',
          'Prepared and deployed shop for pre-order and release bundles'
        ]
        this.project.trailerUrl = 'https://www.youtube.com/embed/QXKqnE7VZtg'
        break;
      case 'guardiansofember': 
        this.project.name = 'Guardians of Ember'
        this.project.description = 'Slash your way through hordes of monsters in a world complete with crafting system, dungeons, mounts, pets, guilds and trading.'
        this.project.projectUrl = 'https://gameforge.com/play/guardians_of_ember'
        this.project.projectImage = 'images/projects/professional/guardiansofember/logo-goe-wide.jpg'
        this.project.company = 'Gameforge'
        this.project.function = 'DevOps Engineer'
        this.project.projectDuration = '2019'
        this.project.text = "Responsibilities in the project:"
        this.project.tasks = [
          'Support integration into Gameforge platform',
          'Prepared and deployed shop for release bundles'
        ]
        this.project.trailerUrl = 'https://www.youtube.com/embed/VoniLk20CQw'
        break;
      case 'battlehordes': 
        this.project.name = 'Battle Hordes'
        this.project.description = 'Battle Hordes is a free-to-play fantasy MMO for mobile devices. Customize a castle and command an army to conquer the world for your glorious empire!'
        this.project.projectUrl = 'https://www.facebook.com/pg/battlehordes'
        this.project.projectImage = 'images/projects/professional/logo-bh.png'
        this.project.company = 'XYRALITY'
        this.project.function = 'Software engineering trainee'
        this.project.projectDuration = 'November 2016 - July 2017'
        this.project.text = "Responsibilities in the project:"
        this.project.tasks = [
          'Supported the development/testing process by implementing a test automation strategy',
          'Assigned tasks to individual developers in test',
          'Created and improved Jenkins build scripts for continuous integration',
          'Maintained the Jenkins slave machines used for test automation',
          'Developed iOS UI tests',
          'Implemented a test coverage system to generate reports for project management',
          'Provided feedback in regards to game design and gameplay',
          'Tested release candidate versions'
        ]
        this.project.trailerUrl = 'https://www.youtube.com/embed/lmme2t_ljIQ'
        break;
      case 'lordsandknights': 
        this.project.name = 'Lords & Knights'
        this.project.description = 'Lords & Knights is a medieval strategy MMO game available for Browser, iOS and Android. Play with thousands of other lords, create massive armies and conquer hundreds of new castles. There is no limit in this game!'
        this.project.projectUrl = 'https://lordsandknights.com'
        this.project.projectImage = 'images/projects/professional/logo-lk.png'
        this.project.company = 'XYRALITY'
        this.project.function = 'Software engineering trainee'
        this.project.projectDuration = 'February 2016 - June 2018'
        this.project.text = "Responsibilities in the project:"
        this.project.tasks = [ 
          'Supported the development/testing process by implementing a test automation strategy',
          'Developed UI tests for Android/Browser/iOS platforms using Selenium',
          'Assigned tasks to individual developers in test',
          'Created and improved Jenkins build scripts for continuous integration',
          'Extended the Docker setup of the game backend',
          'Maintained the Jenkins slave machines used for test automation',
          'Implemented a test coverage system to generate reports for project management',
          'Provided feedback in regards to game design and gameplay',
          'Designed and implemented a new Spring/Vaadin based web application (Admin Tool) using Java and Kotlin'
        ]
        this.project.trailerUrl = 'https://www.youtube.com/embed/Le2zUxS1cH8'
        break;
      case 'bigfarm':
        this.project.name = 'Big Farm'
        this.project.description = 'Live an exciting life in the country with your own farm in Goodgame Big Farm! You are in charge of planting and harvesting your fields and, of course, raising your own pigs and cows! You will also have to prove your business skills by selling your goods on the market.'
        this.project.projectUrl = 'https://bigfarm.goodgamestudios.com'
        this.project.projectImage = 'images/projects/professional/bigfarm/logo-wide.jpg'
        this.project.projectDuration = 'October 2013 - February 2015'
        this.project.company = 'Goodgame Studios'
        this.project.function = 'Quality Assurance Technician'
        this.project.text = "Responsibilities in the project:"
        this.project.tasks = [ 
          'Created and improved test plans',
          'Assigned tasks to individual testers',
          'Supported testers in case of questions',
          'Ensured optimal communication between QA and Development',
          'Provided feedback in regards to game design and gameplay',
          'Prepared test reports for project management',
          'Created and analyzed test statistics'
        ]
        this.project.trailerUrl = 'https://www.youtube.com/embed/nUok2W5FWiQ'
      break;
      case 'empirefourkingdoms': 
        this.project.name = 'Empire: Four Kingdoms'
        this.project.description = 'Make your dream a reality and become a king and castle lord with the Empire: Four Kingdoms app! Produce new resources and build your small castle into a mighty fortress!'
        this.project.projectUrl = 'https://www.goodgamestudios.com/games/empire-four-kingdoms-app'
        this.project.projectImage = 'images/projects/professional/logo-e4k.png'
        this.project.company = 'Goodgame Studios'
        this.project.function = 'Quality Assurance Technician'
        this.project.projectDuration = 'October 2013 - February 2015'
        this.project.text = "Responsibilities in the project:"
        this.project.tasks = [ 
          'Tested alpha, beta and release candidate versions',
          'Reported game bugs',
          'Verified payment functionality through payment sandbox and real payment',
          'Provided feedback in regards to game design and gameplay',
          'Improved test plans'
        ]
        this.project.trailerUrl = 'https://www.youtube.com/embed/fVuhXzW-JPY'
        break;
      case 'empire': 
        this.project.name = 'Goodgame Empire'
        this.project.description = 'In Goodgame Empire you create your own medieval kingdom! Fight PvP battles against other players or form alliances to become the most powerful ruler.'
        this.project.projectUrl = 'https://www.goodgamestudios.com/games/goodgame-empire/'
        this.project.projectImage = 'images/projects/professional/empire/logo-wide.jpg'
        this.project.company = 'Goodgame Studios'
        this.project.function = 'Quality Assurance Technician'
        this.project.projectDuration = 'October 2013 - February 2015'
        this.project.text = "Responsibilities in the project:"
        this.project.tasks = [ 
          'Tested alpha, beta and release candidate versions',
          'Reported game bugs',
          'Verified payment functionality through payment sandbox and real payment',
          'Provided feedback in regards to game design and gameplay',
          'Improved test plans'
        ]
        this.project.trailerUrl = 'https://www.youtube.com/embed/az62_lZf-dY'
        break;
      
// private projects begin here

      case 'snakefx': 
        this.project.name = 'SnakeFX'
        this.project.description = 'Snake game implementation'
        this.project.projectUrl = 'https://git.rickiekarp.net/rickie/home/src/master/projects/JavaFXApps/SnakeFX'
        this.project.downloadUrl = 'https://drive.google.com/open?id=1rut3LQAT78lcs23L-3swXuL04jwKa09m'
        this.project.projectImage = 'images/projects/private/logo-snakefx.png'
        this.project.company = 'Private project'
        this.project.function = 'Creator'
        this.project.projectDuration = 'October 2019 - present'
        this.project.text = "Simple Snake implementation using JavaFX"
        this.imageUrls.push(
          {'image': 'assets/images/projects/private/snakefx/preview_1.png'},
          {'image': 'assets/images/projects/private/snakefx/preview_2.png'},
        );
        break;

      case 'homebackend': 
        this.project.name = 'Home Automation'
        this.project.description = 'Backend applications running on a Raspberry Pi'
        this.project.projectUrl = 'https://git.rickiekarp.net/rickie/home/src/master/projects/homebackend'
        this.project.projectImage = 'images/projects/private/logo-homebackend.png'
        this.project.company = 'Private project'
        this.project.function = 'Creator'
        this.project.projectDuration = 'July 2018 - present'
        this.project.text = "Content of this project:"
        this.project.tasks = [ 
          'Login server written in Kotlin',
          'Application server written in Kotlin',
          'REST service written in Go',
          'Service automation using Bash/Python/Go'
        ]
        break;

      case 'sha1pass': 
        this.project.name = 'SHA1Pass'
        this.project.description = 'A sentence based password generation program'
        this.project.projectUrl = 'https://git.rickiekarp.net/rickie/home/src/master/projects/JavaFXApps/SHA1Pass'
        this.project.downloadUrl = 'https://drive.google.com/open?id=13pCI7Q0miUfk7fXJsmQxtWgXRamgvxgi'
        this.project.projectImage = 'images/projects/private/logo-sha1pass.png'
        this.project.company = 'Private project'
        this.project.function = 'Creator'
        this.project.projectDuration = 'June 2014 - present'
        this.project.text = "Content of this project:"
        this.project.tasks = [ 
          'Hex encryption',
          'Base64 encryption',
          'Bcrypt encryption',
          'Color code encryption',
          'HMAC and Complex Mode',
          'Application ported to Android'
        ]
        this.imageUrls.push(
          {'image': 'assets/images/projects/private/sha1pass/preview_1.png'},
          {'image': 'assets/images/projects/private/sha1pass/preview_2.png'},
          {'image': 'assets/images/projects/private/sha1pass/preview_3.png'},
        );
        break;
     
      case 'filelistcreator': 
        this.project.name = 'FilelistCreator'
        this.project.description = 'Application to create file lists'
        this.project.projectUrl = 'https://git.rickiekarp.net/rickie/home/src/master/projects/JavaFXApps/FilelistCreator'
        this.project.downloadUrl = 'https://drive.google.com/open?id=12C138Ek6ncQmQBa-Y6MpcS0b3i8KfjJA'
        this.project.projectImage = 'images/projects/private/logo-filelistcreator.png'
        this.project.company = 'Private project'
        this.project.function = 'Creator'
        this.project.projectDuration = 'June 2014 - present'
        this.project.text = "Content of this project:"
        this.project.tasks = [ 
          'Create file lists',
          'Group files by folder',
          'Save file list to html / text file'
        ]
        this.imageUrls.push(
          {'image': 'assets/images/projects/private/filelistcreator/preview_1.png'},
          {'image': 'assets/images/projects/private/filelistcreator/preview_2.png'},
          {'image': 'assets/images/projects/private/filelistcreator/preview_3.png'},
        );
        break;

      case 'botmanager': 
        this.project.name = 'Web Bot'
        this.project.description = 'Application to automate tasks on browsers and mobile'
        this.project.projectUrl = 'https://git.rickiekarp.net/rickie/home/src/master/projects/JavaFXApps/BotManager'
        this.project.projectImage = 'images/projects/private/logo-botmanager.png'
        this.project.company = 'Private project'
        this.project.function = 'Creator'
        this.project.projectDuration = 'June 2015 - February 2016'
        this.project.text = "Content of this project:"
        this.project.tasks = [ 
          'Login system',
          'Plugin system',
          'Periodic task execution'
        ]
        break;

      case 'assistant': 
        this.project.name = 'Assistant'
        this.project.description = 'Personal assistant app'
        this.project.projectUrl = 'https://git.rickiekarp.net/rickie/home/src/master/projects/MobileApps/assistantApp'
        this.project.projectImage = 'images/projects/private/logo-assistant.png'
        this.project.company = 'Private project'
        this.project.function = 'Creator'
        this.project.projectDuration = 'October 2017 - June 2018'
        this.project.text = "Content of this project:"
        this.project.tasks = [ 
          'Shopping list',
          'Shopping history view'
        ]
        this.imageUrls.push(
          {'image': 'assets/images/projects/private/assistant/preview_1.png'},
          {'image': 'assets/images/projects/private/assistant/preview_2.png'},
          {'image': 'assets/images/projects/private/assistant/preview_3.png'},
        );
        break;

      case 'reddit': 
        this.project.name = 'RedditIsFun'
        this.project.description = 'Android app to read reddit.com'
        this.project.projectUrl = 'https://git.rickiekarp.net/rickie/home/src/master/projects/MobileApps/redditApp'
        this.project.projectImage = 'images/projects/private/logo-reddit.png'
        this.project.company = 'Private project'
        this.project.projectDuration = 'December 2015'
        this.project.text = "Forked from https://github.com/talklittle/reddit-is-fun"
        break;

      case 'university': 
        this.project.name = 'University Hamburg'
        this.project.description = 'Collection of my university assignments'
        this.project.projectUrl = 'https://git.rickiekarp.net/rickie/university'
        this.project.projectImage = 'images/projects/private/logo-unihh.png'
        this.project.company = 'Private project'
        this.project.projectDuration = 'August 2015 - March 2016'
        this.project.text = "Courses:"
        this.project.tasks = [ 
          'Software development I',
          'Computer architecture'
        ]
        break;
    }
    this.titleService.setTitle(this.project.name);
  }

  ngAfterViewInit() {
    // Hack: Scrolls to top of Page after page view initialized
    let top = document.getElementById('projects');
    if (top !== null) {
      top.scrollIntoView();
      top = null;
    }
  }

}
