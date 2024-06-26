import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'restaurant',
        loadChildren: () => import('./restaurant/restaurant.module').then(m => m.ParadineRestaurantModule),
      },
      {
        path: 'popular-time',
        loadChildren: () => import('./popular-time/popular-time.module').then(m => m.ParadinePopularTimeModule),
      },
      {
        path: 'working-hours',
        loadChildren: () => import('./working-hours/working-hours.module').then(m => m.ParadineWorkingHoursModule),
      },
      {
        path: 'achievement',
        loadChildren: () => import('./achievement/achievement.module').then(m => m.ParadineAchievementModule),
      },
      {
        path: 'intended-visit',
        loadChildren: () => import('./intended-visit/intended-visit.module').then(m => m.ParadineIntendedVisitModule),
      },
      {
        path: 'city',
        loadChildren: () => import('./city/city.module').then(m => m.ParadineCityModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ParadineEntityModule {}
