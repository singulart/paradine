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
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ParadineEntityModule {}
