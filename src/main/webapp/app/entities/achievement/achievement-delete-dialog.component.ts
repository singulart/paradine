import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAchievement } from 'app/shared/model/achievement.model';
import { AchievementService } from './achievement.service';

@Component({
  templateUrl: './achievement-delete-dialog.component.html',
})
export class AchievementDeleteDialogComponent {
  achievement?: IAchievement;

  constructor(
    protected achievementService: AchievementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.achievementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('achievementListModification');
      this.activeModal.close();
    });
  }
}
