import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIntendedVisit } from 'app/shared/model/intended-visit.model';
import { IntendedVisitService } from './intended-visit.service';

@Component({
  templateUrl: './intended-visit-delete-dialog.component.html',
})
export class IntendedVisitDeleteDialogComponent {
  intendedVisit?: IIntendedVisit;

  constructor(
    protected intendedVisitService: IntendedVisitService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.intendedVisitService.delete(id).subscribe(() => {
      this.eventManager.broadcast('intendedVisitListModification');
      this.activeModal.close();
    });
  }
}
