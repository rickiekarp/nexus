import { Http } from '@angular/http';
import { Injectable } from '@angular/core';
import { SkillDto } from '../model/skill.model';
import {BehaviorSubject, Observable, Subject, timer} from 'rxjs';
import {delayWhen, filter, map, retryWhen, shareReplay, tap, withLatestFrom} from 'rxjs/operators';
import {createHttpObservable} from './util';

export const HEROES: SkillDto[] = [
  { id: 11, text: 'Mr. Nice' },
  { id: 12, text: 'Narco' },
  { id: 13, text: 'Bombasto' },
  { id: 14, text: 'Celeritas' },
  { id: 15, text: 'Magneta' },
  { id: 16, text: 'RubberMan' },
  { id: 17, text: 'Dynama' },
  { id: 18, text: 'Dr IQ' },
  { id: 19, text: 'Magma' },
  { id: 20, text: 'Tornado' }
];

@Injectable({
  providedIn: 'root'
})
@Injectable()
export class ResumeService {
  private skillsApiUrl = 'api/resume/skills';
  private experienceApiUrl = 'api/resume/experience';
  private educationApiUrl = 'api/resume/education';

  constructor(private http: Http) { }

  skillsInfo: Array<SkillDto>;

  private subject = new BehaviorSubject<SkillDto[]>([]);

  courses$: Observable<SkillDto[]> = this.subject.asObservable();

  getAll() {
    return this.http.get('assets/data/people.json');
  }

  init() {
    console.log("init");
    const http$ = createHttpObservable('/api/resume/skills');

    http$
        .pipe(
            tap(() => console.log('HTTP request executed')),
            map(res => Object.values(res['payload']))
        )
        .subscribe(
            courses => this.subject.next(courses)
        );
}

  selectCourseById(courseId:number) {
    return this.courses$
        .pipe(
            map(courses => courses.find(course => course.id == courseId)),
            filter(course => !!course)

        );
}

  getAllExperience() {
    return this.http.get(this.experienceApiUrl);
  }

  getAllEducation() {
    return this.http.get(this.educationApiUrl);
  }

  getAllSkills() {
    return this.http.get(this.skillsApiUrl);
  }
}
