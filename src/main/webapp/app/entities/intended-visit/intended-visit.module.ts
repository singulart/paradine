import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ParadineSharedModule } from 'app/shared/shared.module';
import { IntendedVisitComponent } from './intended-visit.component';
import { IntendedVisitDetailComponent } from './intended-visit-detail.component';
import { IntendedVisitUpdateComponent } from './intended-visit-update.component';
import { IntendedVisitDeleteDialogComponent } from './intended-visit-delete-dialog.component';
import { intendedVisitRoute } from './intended-visit.route';

@NgModule({
  imports: [ParadineSharedModule, RouterModule.forChild(intendedVisitRoute)],
  declarations: [IntendedVisitComponent, IntendedVisitDetailComponent, IntendedVisitUpdateComponent, IntendedVisitDeleteDialogComponent],
  entryComponents: [IntendedVisitDeleteDialogComponent],
})
export class ParadineIntendedVisitModule {}
