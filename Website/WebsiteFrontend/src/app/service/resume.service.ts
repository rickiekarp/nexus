import { Injectable } from '@angular/core';

import { map, catchError } from 'rxjs/operators';
import { Http } from '@angular/http';
import { SkillDto } from '../model/skill.model';
import { Observable, of } from 'rxjs';

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

@Injectable()
export class ResumeService {
  private skillsApiUrl = 'api/resume/skills';
  private experienceApiUrl = 'api/resume/experience';
  private educationApiUrl = 'api/resume/education';

  constructor(private http: Http) { }

  getAllExperience() {
    return this.http.get(this.experienceApiUrl);
  }

  getAllEducation() {
    return this.http.get(this.educationApiUrl);
  }

  getAllSkills() {
    return this.http.get(this.skillsApiUrl);
  }

  getHeroes(): Observable<SkillDto[]> {
    return this.getAllSkills().pipe(
      map(data => {
          let body = data.text()
          let dat = JSON.parse(body)
          let result = <SkillDto[]> dat;
          return result;
      })
    );
  }

  getLocalHeroes(): Observable<SkillDto[]> {
    console.log(HEROES)
    return of(HEROES);
  }

}
