import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIntendedVisit } from 'app/shared/model/intended-visit.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IntendedVisitService } from './intended-visit.service';
import { IntendedVisitDeleteDialogComponent } from './intended-visit-delete-dialog.component';

@Component({
  selector: 'jhi-intended-visit',
  templateUrl: './intended-visit.component.html',
})
export class IntendedVisitComponent implements OnInit, OnDestroy {
  intendedVisits: IIntendedVisit[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  currentSearch: string;

  constructor(
    protected intendedVisitService: IntendedVisitService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute
  ) {
    this.intendedVisits = [];
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
      this.intendedVisitService
        .search({
          query: this.currentSearch,
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe((res: HttpResponse<IIntendedVisit[]>) => this.paginateIntendedVisits(res.body, res.headers));
      return;
    }

    this.intendedVisitService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IIntendedVisit[]>) => this.paginateIntendedVisits(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.intendedVisits = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  search(query: string): void {
    this.intendedVisits = [];
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
    this.registerChangeInIntendedVisits();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIntendedVisit): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIntendedVisits(): void {
    this.eventSubscriber = this.eventManager.subscribe('intendedVisitListModification', () => this.reset());
  }

  delete(intendedVisit: IIntendedVisit): void {
    const modalRef = this.modalService.open(IntendedVisitDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.intendedVisit = intendedVisit;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateIntendedVisits(data: IIntendedVisit[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.intendedVisits.push(data[i]);
      }
    }
  }
}
