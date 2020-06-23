import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'restaurant',
        loadChildren: () => import('./restaurant/restaurant.module').then(m => m.ThehipstaRestaurantModule),
      },
      {
        path: 'popular-time',
        loadChildren: () => import('./popular-time/popular-time.module').then(m => m.ThehipstaPopularTimeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ThehipstaEntityModule {}
