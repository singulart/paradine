import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ParadineSharedModule } from 'app/shared/shared.module';
import { AchievementComponent } from './achievement.component';
import { AchievementDetailComponent } from './achievement-detail.component';
import { AchievementUpdateComponent } from './achievement-update.component';
import { AchievementDeleteDialogComponent } from './achievement-delete-dialog.component';
import { achievementRoute } from './achievement.route';

@NgModule({
  imports: [ParadineSharedModule, RouterModule.forChild(achievementRoute)],
  declarations: [AchievementComponent, AchievementDetailComponent, AchievementUpdateComponent, AchievementDeleteDialogComponent],
  entryComponents: [AchievementDeleteDialogComponent],
})
export class ParadineAchievementModule {}
