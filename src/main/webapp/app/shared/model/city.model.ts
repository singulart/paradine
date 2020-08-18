export interface ICity {
  id?: number;
  slug?: string;
  name?: string;
  image?: string;
}

export class City implements ICity {
  constructor(public id?: number, public slug?: string, public name?: string, public image?: string) {}
}
