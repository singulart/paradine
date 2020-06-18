import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ThehipstaSharedModule } from 'app/shared/shared.module';
import { RestaurantComponent } from './restaurant.component';
import { RestaurantDetailComponent } from './restaurant-detail.component';
import { RestaurantUpdateComponent } from './restaurant-update.component';
import { RestaurantDeleteDialogComponent } from './restaurant-delete-dialog.component';
import { restaurantRoute } from './restaurant.route';

@NgModule({
  imports: [ThehipstaSharedModule, RouterModule.forChild(restaurantRoute)],
  declarations: [RestaurantComponent, RestaurantDetailComponent, RestaurantUpdateComponent, RestaurantDeleteDialogComponent],
  entryComponents: [RestaurantDeleteDialogComponent],
})
export class ThehipstaRestaurantModule {}
