export interface IAchievement {
  id?: number;
  slug?: string;
  nameEn?: string;
  nameRu?: string;
  nameUa?: string;
  descriptionEn?: string;
  descriptionRu?: string;
  descriptionUa?: string;
}

export class Achievement implements IAchievement {
  constructor(
    public id?: number,
    public slug?: string,
    public nameEn?: string,
    public nameRu?: string,
    public nameUa?: string,
    public descriptionEn?: string,
    public descriptionRu?: string,
    public descriptionUa?: string
  ) {}
}
