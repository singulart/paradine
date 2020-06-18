import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'restaurant',
        loadChildren: () => import('./restaurant/restaurant.module').then(m => m.ThehipstaRestaurantModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ThehipstaEntityModule {}
