import { Moment } from 'moment';

export interface IRestaurant {
  id?: number;
  uuid?: string;
  name?: string;
  capacity?: number;
  geolat?: number;
  geolng?: number;
  photoUrl?: string;
  altName1?: string;
  altName2?: string;
  altName3?: string;
  googlePlacesId?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
}

export class Restaurant implements IRestaurant {
  constructor(
    public id?: number,
    public uuid?: string,
    public name?: string,
    public capacity?: number,
    public geolat?: number,
    public geolng?: number,
    public photoUrl?: string,
    public altName1?: string,
    public altName2?: string,
    public altName3?: string,
    public googlePlacesId?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment
  ) {}
}
