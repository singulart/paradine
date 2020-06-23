import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPopularTime } from 'app/shared/model/popular-time.model';
import { PopularTimeService } from './popular-time.service';

@Component({
  templateUrl: './popular-time-delete-dialog.component.html',
})
export class PopularTimeDeleteDialogComponent {
  popularTime?: IPopularTime;

  constructor(
    protected popularTimeService: PopularTimeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.popularTimeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('popularTimeListModification');
      this.activeModal.close();
    });
  }
}
