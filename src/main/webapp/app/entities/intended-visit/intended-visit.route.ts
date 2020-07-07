import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIntendedVisit, IntendedVisit } from 'app/shared/model/intended-visit.model';
import { IntendedVisitService } from './intended-visit.service';
import { IntendedVisitComponent } from './intended-visit.component';
import { IntendedVisitDetailComponent } from './intended-visit-detail.component';
import { IntendedVisitUpdateComponent } from './intended-visit-update.component';

@Injectable({ providedIn: 'root' })
export class IntendedVisitResolve implements Resolve<IIntendedVisit> {
  constructor(private service: IntendedVisitService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIntendedVisit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((intendedVisit: HttpResponse<IntendedVisit>) => {
          if (intendedVisit.body) {
            return of(intendedVisit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IntendedVisit());
  }
}

export const intendedVisitRoute: Routes = [
  {
    path: '',
    component: IntendedVisitComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paradineApp.intendedVisit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IntendedVisitDetailComponent,
    resolve: {
      intendedVisit: IntendedVisitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paradineApp.intendedVisit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IntendedVisitUpdateComponent,
    resolve: {
      intendedVisit: IntendedVisitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paradineApp.intendedVisit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IntendedVisitUpdateComponent,
    resolve: {
      intendedVisit: IntendedVisitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paradineApp.intendedVisit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
