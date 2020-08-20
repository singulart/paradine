import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPopularTime } from 'app/shared/model/popular-time.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PopularTimeService } from './popular-time.service';
import { PopularTimeDeleteDialogComponent } from './popular-time-delete-dialog.component';

@Component({
  selector: 'jhi-popular-time',
  templateUrl: './popular-time.component.html',
})
export class PopularTimeComponent implements OnInit, OnDestroy {
  popularTimes: IPopularTime[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  currentSearch: string;

  constructor(
    protected popularTimeService: PopularTimeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute
  ) {
    this.popularTimes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
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
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe((res: HttpResponse<IPopularTime[]>) => this.paginatePopularTimes(res.body, res.headers));
      return;
    }

    this.popularTimeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IPopularTime[]>) => this.paginatePopularTimes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.popularTimes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  search(query: string): void {
    this.popularTimes = [];
    this.links = {
      last: 0,
    };
    this.page = 0;
    if (query) {
      this.predicate = '_score';
      this.ascending = false;
    } else {
      this.predicate = 'id';
      this.ascending = true;
    }
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
    this.eventSubscriber = this.eventManager.subscribe('popularTimeListModification', () => this.reset());
  }

  delete(popularTime: IPopularTime): void {
    const modalRef = this.modalService.open(PopularTimeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.popularTime = popularTime;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePopularTimes(data: IPopularTime[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.popularTimes.push(data[i]);
      }
    }
  }
}
