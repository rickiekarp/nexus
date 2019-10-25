import { Component, OnInit } from '@angular/core';

import { ProjectDto } from '../../model/project.model'
import { Router,ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-my-project',
  templateUrl: './projects.component.html',
  styleUrls: [ './projects.component.css' ],
  providers: []
})

export class ProjectsComponent implements OnInit {
  id: string;
  project: ProjectDto;

  constructor(
    private activatedroute:ActivatedRoute,
    private router: Router) { }
    
  ngOnInit(): void {
    this.id=this.activatedroute.snapshot.params['id'];

    // temporary
    this.project = new ProjectDto();
    switch(this.id) {

// professional projects begin here

      case 'kingdomunderfire2': 
        this.project.name = 'Kingdom Under Fire 2'
        this.project.description = 'Kingdom Under Fire II is a video game set in a high fantasy setting'
        this.project.projectUrl = 'https://join.kingdomunderfire2.gameforge.com/'
        this.project.projectImage = 'images/logo-kuf2.png'
        this.project.company = 'Gameforge'
        this.project.function = 'DevOps Engineer'
        this.project.projectDuration = 'December 2018 - present'
        this.project.text = "Responsibilities in the project:"
        this.project.tasks = [
          'Support game integration into portfolio',
          'Prepared and deployed shop for pre-order and release bundles'
        ]
        this.project.trailerUrl = 'https://www.youtube.com/embed/QXKqnE7VZtg'
        break;
      case 'guardiansofember': 
        this.project.name = 'Guardians of Ember'
        this.project.description = 'Slash your way through hordes of monsters in a world complete with crafting system, dungeons, mounts, pets, guilds and trading.'
        this.project.projectUrl = 'https://gameforge.com/play/guardians_of_ember'
        this.project.projectImage = 'images/logo-goe.png'
        this.project.company = 'Gameforge'
        this.project.function = 'DevOps Engineer'
        this.project.projectDuration = 'December 2018 - present'
        this.project.text = "Responsibilities in the project:"
        this.project.tasks = [
          'Support game integration into portfolio',
          'Implemented and deployed item shop'
          
        ]
        this.project.trailerUrl = 'https://www.youtube.com/embed/VoniLk20CQw'
        break;
      case 'battlehordes': 
        this.project.name = 'Battle Hordes'
        this.project.description = 'Battle Hordes is a free-to-play fantasy MMO for mobile devices. Customize a castle and command an army to conquer the world for your glorious empire!'
        this.project.projectUrl = 'https://www.facebook.com/pg/battlehordes'
        this.project.projectImage = 'images/logo-bh.png'
        this.project.company = 'XYRALITY GmbH'
        this.project.function = 'Software engineering trainee'
        this.project.projectDuration = 'February 2016 - June 2018'
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
        this.project.projectImage = 'images/logo-lk.png'
        this.project.company = 'XYRALITY GmbH'
        this.project.function = 'Software engineering trainee'
        this.project.projectDuration = 'February 2016 - June 2018'
        this.project.text = "Responsibilities in the project:"
        this.project.tasks = [ 
          'Supported the development/testing process by implementing a test automation strategy',
          'Developed UI tests for Android/Browser/iOS platforms using Selenium',
          'Assigned tasks to individual developers in test',
          'Created and improved Jenkins build scripts for continuous integration',
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
        this.project.projectImage = 'images/logo-bf.png'
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
        this.project.projectUrl = 'https://lordsandknights.com'
        this.project.projectImage = 'images/logo-e4k.png'
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
        this.project.projectImage = 'images/logo-ep.png'
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
        this.project.downloadUrl = 'https://drive.google.com/open?id=1qRbS7OkmPPoYzkdM2dQtYzoP0b4N_S9r'
        this.project.projectImage = 'images/logo-snakefx.png'
        this.project.company = 'Private project'
        this.project.function = 'Creator'
        this.project.projectDuration = 'October 2019'
        this.project.text = "Simple Snake implementation using JavaFX"
        break;

      case 'homebackend': 
        this.project.name = 'Home Automation'
        this.project.description = 'Backend applications running on a Raspberry Pi'
        this.project.projectUrl = 'https://git.rickiekarp.net/rickie/home/src/master/projects/homebackend'
        this.project.projectImage = 'images/logo-homebackend.png'
        this.project.company = 'Private project'
        this.project.function = 'Creator'
        this.project.projectDuration = 'July 2018 - present'
        this.project.text = "Content of this project:"
        this.project.tasks = [ 
          'Login servlet written in Kotlin',
          'Application servlet written in Kotlin',
          'REST service written in Go',
          'Services automated using Bash'
        ]
        break;

      case 'sha1pass': 
        this.project.name = 'SHA1Pass'
        this.project.description = 'A sentence based password generation program'
        this.project.projectUrl = 'https://git.rickiekarp.net/rickie/home/src/master/projects/JavaFXApps/SHA1Pass'
        this.project.projectImage = 'images/logo-sha1pass.png'
        this.project.company = 'Private project'
        this.project.function = 'Creator'
        this.project.projectDuration = 'June 2014 - present'
        this.project.text = "Content of this project:"
        this.project.tasks = [ 
          'Hex encryption',
          'Base64 encryption',
          'Bcrypt encryption',
          'Color code encryption',
          'HMAC and Complex Mode'
        ]
        break;
     
      case 'filelistcreator': 
        this.project.name = 'FilelistCreator'
        this.project.description = 'Application to create file lists'
        this.project.projectUrl = 'https://git.rickiekarp.net/rickie/home/src/master/projects/JavaFXApps/FilelistCreator'
        this.project.projectImage = 'images/logo-filelistcreator.png'
        this.project.company = 'Private project'
        this.project.function = 'Creator'
        this.project.projectDuration = 'October 2013 - February 2015'
        this.project.text = "Content of this project:"
        this.project.tasks = [ 
          'Create file lists',
          'Group files by folder',
          'Save file list to html / text file'
        ]
        break;

      case 'botmanager': 
        this.project.name = 'Web Bot'
        this.project.description = 'Application to automate tasks on browsers and mobile'
        this.project.projectUrl = 'https://git.rickiekarp.net/rickie/home/src/master/projects/JavaFXApps/BotManager'
        this.project.projectImage = 'images/logo-botmanager.png'
        this.project.company = 'Private project'
        this.project.function = 'Creator'
        this.project.projectDuration = 'October 2013 - February 2015'
        this.project.text = "Content of this project:"
        this.project.tasks = [ 
          'Login system',
          'Plugin system',
          'Periodic execution'
        ]
        break;

      case 'assistant': 
        this.project.name = 'Assistant'
        this.project.description = 'Personal assistant app'
        this.project.projectUrl = 'https://git.rickiekarp.net/rickie/home/src/master/projects/MobileApps/assistantApp'
        this.project.projectImage = 'images/logo-assistant.png'
        this.project.company = 'Private project'
        this.project.function = 'Creator'
        this.project.projectDuration = 'October 2013 - February 2015'
        this.project.text = "Content of this project:"
        this.project.tasks = [ 
          'Shopping list',
          'Shopping history view'
        ]
        break;

      case 'reddit': 
        this.project.name = 'reddit'
        this.project.description = 'Android app to read reddit.com'
        this.project.projectUrl = 'https://git.rickiekarp.net/rickie/home/src/master/projects/MobileApps/redditApp'
        this.project.projectImage = 'images/logo-reddit.png'
        this.project.company = 'Private project'
        this.project.projectDuration = 'December 2015'
        this.project.text = "Forked from https://github.com/talklittle/reddit-is-fun"
        break;

      case 'university': 
        this.project.name = 'university'
        this.project.description = 'Collection of my university assignments'
        this.project.projectUrl = 'https://git.rickiekarp.net/rickie/home/src/master/edu/university'
        this.project.projectImage = 'images/logo-unihh.png'
        this.project.company = 'Private project'
        this.project.projectDuration = 'August 2015 - February 2016'
        this.project.text = "Courses:"
        this.project.tasks = [ 
          'Software development I',
          'Computer architecture'
        ]
        break;
    }
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
