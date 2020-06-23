import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPopularTime } from 'app/shared/model/popular-time.model';
import { PopularTimeService } from './popular-time.service';
import { PopularTimeDeleteDialogComponent } from './popular-time-delete-dialog.component';

@Component({
  selector: 'jhi-popular-time',
  templateUrl: './popular-time.component.html',
})
export class PopularTimeComponent implements OnInit, OnDestroy {
  popularTimes?: IPopularTime[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected popularTimeService: PopularTimeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.popularTimeService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IPopularTime[]>) => (this.popularTimes = res.body || []));
      return;
    }

    this.popularTimeService.query().subscribe((res: HttpResponse<IPopularTime[]>) => (this.popularTimes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPopularTimes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPopularTime): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPopularTimes(): void {
    this.eventSubscriber = this.eventManager.subscribe('popularTimeListModification', () => this.loadAll());
  }

  delete(popularTime: IPopularTime): void {
    const modalRef = this.modalService.open(PopularTimeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.popularTime = popularTime;
  }
}
