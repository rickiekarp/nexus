import { Http } from '@angular/http';
import { Injectable } from '@angular/core';
import { SkillDto } from '../model/skill.model';
import {BehaviorSubject, Observable, Subject, timer} from 'rxjs';
import { ResumeDto } from '../model/resume.model';
import { BlacklistData } from '../model/blacklistdata.model';
import { HttpClient } from  "@angular/common/http";
import { ResumeData } from '../model/resumedata.model';

@Injectable({
  providedIn: 'root'
})
@Injectable()
export class ResumeService {
  private skillsApiUrl = 'api/resume/skills';
  private experienceApiUrl = 'api/resume/experience';
  private educationApiUrl = 'api/resume/education';

  constructor(private http: Http, private  httpClient:HttpClient) { 
  }

  getExistingArsByLab(): Observable<BlacklistData> {
    return this.httpClient.get<BlacklistData>("assets/test.json")
  }

  getAllExperience(): Observable<ResumeData> {
    return this.httpClient.get<ResumeData>(this.experienceApiUrl);
  }

  getAllEducation(): Observable<ResumeDto[]> {
    return this.httpClient.get<ResumeDto[]>(this.educationApiUrl);
  }

  getAllSkills(): Observable<SkillDto[]> {
    return this.httpClient.get<SkillDto[]>(this.skillsApiUrl)
  }
}
