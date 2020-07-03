export interface IWorkingHours {
  id?: number;
  dayOfWeek?: string;
  closed?: boolean;
  openingHour?: number;
  closingHour?: number;
  restaurantName?: string;
  restaurantId?: number;
}

export class WorkingHours implements IWorkingHours {
  constructor(
    public id?: number,
    public dayOfWeek?: string,
    public closed?: boolean,
    public openingHour?: number,
    public closingHour?: number,
    public restaurantName?: string,
    public restaurantId?: number
  ) {
    this.closed = this.closed || false;
  }
}
