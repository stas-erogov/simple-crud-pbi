import { Moment } from 'moment';

export interface IPoints {
  id?: number;
  region?: string;
  city?: string;
  type?: string;
  date?: Moment;
  fact?: number;
  bandwidth?: number;
}

export class Points implements IPoints {
  constructor(
    public id?: number,
    public region?: string,
    public city?: string,
    public type?: string,
    public date?: Moment,
    public fact?: number,
    public bandwidth?: number
  ) {}
}
