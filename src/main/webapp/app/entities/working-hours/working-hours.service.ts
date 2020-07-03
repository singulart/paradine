import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IWorkingHours } from 'app/shared/model/working-hours.model';

type EntityResponseType = HttpResponse<IWorkingHours>;
type EntityArrayResponseType = HttpResponse<IWorkingHours[]>;

@Injectable({ providedIn: 'root' })
export class WorkingHoursService {
  public resourceUrl = SERVER_API_URL + 'api/working-hours';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/working-hours';

  constructor(protected http: HttpClient) {}

  create(workingHours: IWorkingHours): Observable<EntityResponseType> {
    return this.http.post<IWorkingHours>(this.resourceUrl, workingHours, { observe: 'response' });
  }

  update(workingHours: IWorkingHours): Observable<EntityResponseType> {
    return this.http.put<IWorkingHours>(this.resourceUrl, workingHours, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWorkingHours>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWorkingHours[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWorkingHours[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
