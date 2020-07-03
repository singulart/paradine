import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkingHours } from 'app/shared/model/working-hours.model';
import { WorkingHoursService } from './working-hours.service';

@Component({
  templateUrl: './working-hours-delete-dialog.component.html',
})
export class WorkingHoursDeleteDialogComponent {
  workingHours?: IWorkingHours;

  constructor(
    protected workingHoursService: WorkingHoursService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workingHoursService.delete(id).subscribe(() => {
      this.eventManager.broadcast('workingHoursListModification');
      this.activeModal.close();
    });
  }
}
