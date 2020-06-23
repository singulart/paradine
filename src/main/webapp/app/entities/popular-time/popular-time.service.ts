import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IPopularTime } from 'app/shared/model/popular-time.model';

type EntityResponseType = HttpResponse<IPopularTime>;
type EntityArrayResponseType = HttpResponse<IPopularTime[]>;

@Injectable({ providedIn: 'root' })
export class PopularTimeService {
  public resourceUrl = SERVER_API_URL + 'api/popular-times';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/popular-times';

  constructor(protected http: HttpClient) {}

  create(popularTime: IPopularTime): Observable<EntityResponseType> {
    return this.http.post<IPopularTime>(this.resourceUrl, popularTime, { observe: 'response' });
  }

  update(popularTime: IPopularTime): Observable<EntityResponseType> {
    return this.http.put<IPopularTime>(this.resourceUrl, popularTime, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPopularTime>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPopularTime[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPopularTime[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
