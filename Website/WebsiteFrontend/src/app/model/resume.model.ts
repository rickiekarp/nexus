import { ResumeDescription } from '../model/resumedescription.model';

export class ResumeDto {
    id: number;
    startDate: Date;
    endDate: Date;
    jobTitle: string;
    name: string;
    description: ResumeDescription;
}
