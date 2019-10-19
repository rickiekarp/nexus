import { Http } from '@angular/http';
import { Injectable } from '@angular/core';
import { SkillDto } from '../model/skill.model';
import { Observable } from 'rxjs';
import { ResumeDto } from '../model/resume.model';
import { HttpClient } from  "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
@Injectable()
export class ResumeService {
  private skillsApiUrl = '/HomeServer/resume/skills';
  private experienceApiUrl = '/HomeServer/resume/experience';
  private educationApiUrl = '/HomeServer/resume/education';

  constructor(private http: Http, private  httpClient:HttpClient) { 
  }

  getAllExperience(): Observable<ResumeDto[]> {
    return this.httpClient.get<ResumeDto[]>(this.experienceApiUrl);
  }

  getAllEducation(): Observable<ResumeDto[]> {
    return this.httpClient.get<ResumeDto[]>(this.educationApiUrl);
  }

  getAllSkills(): Observable<SkillDto[]> {
    return this.httpClient.get<SkillDto[]>(this.skillsApiUrl)
  }
}
