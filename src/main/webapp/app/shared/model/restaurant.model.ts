import { Moment } from 'moment';
import { IPopularTime } from 'app/shared/model/popular-time.model';

export interface IRestaurant {
  id?: number;
  name?: string;
  altName1?: string;
  googlePlacesId?: string;
  geolat?: number;
  geolng?: number;
  photoUrl?: string;
  altName2?: string;
  altName3?: string;
  capacity?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  uuid?: string;
  popularTimes?: IPopularTime[];
}

export class Restaurant implements IRestaurant {
  constructor(
    public id?: number,
    public name?: string,
    public altName1?: string,
    public googlePlacesId?: string,
    public geolat?: number,
    public geolng?: number,
    public photoUrl?: string,
    public altName2?: string,
    public altName3?: string,
    public capacity?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public uuid?: string,
    public popularTimes?: IPopularTime[]
  ) {}
}
