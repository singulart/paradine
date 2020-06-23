import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ThehipstaSharedModule } from 'app/shared/shared.module';
import { PopularTimeComponent } from './popular-time.component';
import { PopularTimeDetailComponent } from './popular-time-detail.component';
import { PopularTimeUpdateComponent } from './popular-time-update.component';
import { PopularTimeDeleteDialogComponent } from './popular-time-delete-dialog.component';
import { popularTimeRoute } from './popular-time.route';

@NgModule({
  imports: [ThehipstaSharedModule, RouterModule.forChild(popularTimeRoute)],
  declarations: [PopularTimeComponent, PopularTimeDetailComponent, PopularTimeUpdateComponent, PopularTimeDeleteDialogComponent],
  entryComponents: [PopularTimeDeleteDialogComponent],
})
export class ThehipstaPopularTimeModule {}
