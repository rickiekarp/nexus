import { ResumeDescription } from '../model/resumedescription.model';

export interface ResumeDto {
    id: number;
    startDate: Date;
    endDate: Date;
    jobTitle: string;
    name: string;
    description: ResumeDescription;
}
