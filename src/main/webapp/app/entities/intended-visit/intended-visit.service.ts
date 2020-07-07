import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IIntendedVisit } from 'app/shared/model/intended-visit.model';

type EntityResponseType = HttpResponse<IIntendedVisit>;
type EntityArrayResponseType = HttpResponse<IIntendedVisit[]>;

@Injectable({ providedIn: 'root' })
export class IntendedVisitService {
  public resourceUrl = SERVER_API_URL + 'api/intended-visits';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/intended-visits';

  constructor(protected http: HttpClient) {}

  create(intendedVisit: IIntendedVisit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(intendedVisit);
    return this.http
      .post<IIntendedVisit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(intendedVisit: IIntendedVisit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(intendedVisit);
    return this.http
      .put<IIntendedVisit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIntendedVisit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIntendedVisit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIntendedVisit[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(intendedVisit: IIntendedVisit): IIntendedVisit {
    const copy: IIntendedVisit = Object.assign({}, intendedVisit, {
      visitStartDate:
        intendedVisit.visitStartDate && intendedVisit.visitStartDate.isValid() ? intendedVisit.visitStartDate.toJSON() : undefined,
      visitEndDate: intendedVisit.visitEndDate && intendedVisit.visitEndDate.isValid() ? intendedVisit.visitEndDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.visitStartDate = res.body.visitStartDate ? moment(res.body.visitStartDate) : undefined;
      res.body.visitEndDate = res.body.visitEndDate ? moment(res.body.visitEndDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((intendedVisit: IIntendedVisit) => {
        intendedVisit.visitStartDate = intendedVisit.visitStartDate ? moment(intendedVisit.visitStartDate) : undefined;
        intendedVisit.visitEndDate = intendedVisit.visitEndDate ? moment(intendedVisit.visitEndDate) : undefined;
      });
    }
    return res;
  }
}
