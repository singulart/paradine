import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPopularTime, PopularTime } from 'app/shared/model/popular-time.model';
import { PopularTimeService } from './popular-time.service';
import { PopularTimeComponent } from './popular-time.component';
import { PopularTimeDetailComponent } from './popular-time-detail.component';
import { PopularTimeUpdateComponent } from './popular-time-update.component';

@Injectable({ providedIn: 'root' })
export class PopularTimeResolve implements Resolve<IPopularTime> {
  constructor(private service: PopularTimeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPopularTime> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((popularTime: HttpResponse<PopularTime>) => {
          if (popularTime.body) {
            return of(popularTime.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PopularTime());
  }
}

export const popularTimeRoute: Routes = [
  {
    path: '',
    component: PopularTimeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'thehipstaApp.popularTime.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PopularTimeDetailComponent,
    resolve: {
      popularTime: PopularTimeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'thehipstaApp.popularTime.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PopularTimeUpdateComponent,
    resolve: {
      popularTime: PopularTimeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'thehipstaApp.popularTime.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PopularTimeUpdateComponent,
    resolve: {
      popularTime: PopularTimeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'thehipstaApp.popularTime.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
