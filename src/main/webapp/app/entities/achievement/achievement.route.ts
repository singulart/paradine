import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAchievement, Achievement } from 'app/shared/model/achievement.model';
import { AchievementService } from './achievement.service';
import { AchievementComponent } from './achievement.component';
import { AchievementDetailComponent } from './achievement-detail.component';
import { AchievementUpdateComponent } from './achievement-update.component';

@Injectable({ providedIn: 'root' })
export class AchievementResolve implements Resolve<IAchievement> {
  constructor(private service: AchievementService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAchievement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((achievement: HttpResponse<Achievement>) => {
          if (achievement.body) {
            return of(achievement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Achievement());
  }
}

export const achievementRoute: Routes = [
  {
    path: '',
    component: AchievementComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paradineApp.achievement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AchievementDetailComponent,
    resolve: {
      achievement: AchievementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paradineApp.achievement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AchievementUpdateComponent,
    resolve: {
      achievement: AchievementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paradineApp.achievement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AchievementUpdateComponent,
    resolve: {
      achievement: AchievementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paradineApp.achievement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
