export interface IRestaurant {
  id?: number;
  uuid?: string;
  capacity?: number;
  geolat?: number;
  geolng?: number;
  name?: string;
  photoUrl?: string;
  altName1?: string;
  altName2?: string;
  altName3?: string;
}

export class Restaurant implements IRestaurant {
  constructor(
    public id?: number,
    public uuid?: string,
    public capacity?: number,
    public geolat?: number,
    public geolng?: number,
    public name?: string,
    public photoUrl?: string,
    public altName1?: string,
    public altName2?: string,
    public altName3?: string
  ) {}
}
