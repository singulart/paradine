export interface IPopularTime {
  id?: number;
  dayOfWeek?: string;
  occ06?: number;
  occ07?: number;
  occ08?: number;
  occ09?: number;
  occ10?: number;
  occ11?: number;
  occ12?: number;
  occ13?: number;
  occ14?: number;
  occ15?: number;
  occ16?: number;
  occ17?: number;
  occ18?: number;
  occ19?: number;
  occ20?: number;
  occ21?: number;
  occ22?: number;
  occ23?: number;
  restaurantName?: string;
  restaurantId?: number;
}

export class PopularTime implements IPopularTime {
  constructor(
    public id?: number,
    public dayOfWeek?: string,
    public occ06?: number,
    public occ07?: number,
    public occ08?: number,
    public occ09?: number,
    public occ10?: number,
    public occ11?: number,
    public occ12?: number,
    public occ13?: number,
    public occ14?: number,
    public occ15?: number,
    public occ16?: number,
    public occ17?: number,
    public occ18?: number,
    public occ19?: number,
    public occ20?: number,
    public occ21?: number,
    public occ22?: number,
    public occ23?: number,
    public restaurantName?: string,
    public restaurantId?: number
  ) {}
}
