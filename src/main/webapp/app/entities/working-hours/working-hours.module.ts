import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ParadineSharedModule } from 'app/shared/shared.module';
import { WorkingHoursComponent } from './working-hours.component';
import { WorkingHoursDetailComponent } from './working-hours-detail.component';
import { WorkingHoursUpdateComponent } from './working-hours-update.component';
import { WorkingHoursDeleteDialogComponent } from './working-hours-delete-dialog.component';
import { workingHoursRoute } from './working-hours.route';

@NgModule({
  imports: [ParadineSharedModule, RouterModule.forChild(workingHoursRoute)],
  declarations: [WorkingHoursComponent, WorkingHoursDetailComponent, WorkingHoursUpdateComponent, WorkingHoursDeleteDialogComponent],
  entryComponents: [WorkingHoursDeleteDialogComponent],
})
export class ParadineWorkingHoursModule {}
