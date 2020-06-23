import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IRestaurant } from 'app/shared/model/restaurant.model';

type EntityResponseType = HttpResponse<IRestaurant>;
type EntityArrayResponseType = HttpResponse<IRestaurant[]>;

@Injectable({ providedIn: 'root' })
export class RestaurantService {
  public resourceUrl = SERVER_API_URL + 'api/restaurants';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/restaurants';

  constructor(protected http: HttpClient) {}

  create(restaurant: IRestaurant): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(restaurant);
    return this.http
      .post<IRestaurant>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(restaurant: IRestaurant): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(restaurant);
    return this.http
      .put<IRestaurant>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRestaurant>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRestaurant[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRestaurant[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(restaurant: IRestaurant): IRestaurant {
    const copy: IRestaurant = Object.assign({}, restaurant, {
      createdAt: restaurant.createdAt && restaurant.createdAt.isValid() ? restaurant.createdAt.toJSON() : undefined,
      updatedAt: restaurant.updatedAt && restaurant.updatedAt.isValid() ? restaurant.updatedAt.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? moment(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((restaurant: IRestaurant) => {
        restaurant.createdAt = restaurant.createdAt ? moment(restaurant.createdAt) : undefined;
        restaurant.updatedAt = restaurant.updatedAt ? moment(restaurant.updatedAt) : undefined;
      });
    }
    return res;
  }
}
