import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWorkingHours, WorkingHours } from 'app/shared/model/working-hours.model';
import { WorkingHoursService } from './working-hours.service';
import { WorkingHoursComponent } from './working-hours.component';
import { WorkingHoursDetailComponent } from './working-hours-detail.component';
import { WorkingHoursUpdateComponent } from './working-hours-update.component';

@Injectable({ providedIn: 'root' })
export class WorkingHoursResolve implements Resolve<IWorkingHours> {
  constructor(private service: WorkingHoursService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkingHours> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((workingHours: HttpResponse<WorkingHours>) => {
          if (workingHours.body) {
            return of(workingHours.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WorkingHours());
  }
}

export const workingHoursRoute: Routes = [
  {
    path: '',
    component: WorkingHoursComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paradineApp.workingHours.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkingHoursDetailComponent,
    resolve: {
      workingHours: WorkingHoursResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paradineApp.workingHours.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkingHoursUpdateComponent,
    resolve: {
      workingHours: WorkingHoursResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paradineApp.workingHours.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkingHoursUpdateComponent,
    resolve: {
      workingHours: WorkingHoursResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paradineApp.workingHours.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
