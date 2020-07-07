import { Moment } from 'moment';

export interface IIntendedVisit {
  id?: number;
  uuid?: string;
  visitStartDate?: Moment;
  visitEndDate?: Moment;
  cancelled?: boolean;
  visitingUserLogin?: string;
  visitingUserId?: number;
  restaurantName?: string;
  restaurantId?: number;
}

export class IntendedVisit implements IIntendedVisit {
  constructor(
    public id?: number,
    public uuid?: string,
    public visitStartDate?: Moment,
    public visitEndDate?: Moment,
    public cancelled?: boolean,
    public visitingUserLogin?: string,
    public visitingUserId?: number,
    public restaurantName?: string,
    public restaurantId?: number
  ) {
    this.cancelled = this.cancelled || false;
  }
}
