import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICity } from 'app/shared/model/city.model';
import { CityService } from './city.service';
import { CityDeleteDialogComponent } from './city-delete-dialog.component';

@Component({
  selector: 'jhi-city',
  templateUrl: './city.component.html',
})
export class CityComponent implements OnInit, OnDestroy {
  cities?: ICity[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected cityService: CityService,
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
      this.cityService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ICity[]>) => (this.cities = res.body || []));
      return;
    }

    this.cityService.query().subscribe((res: HttpResponse<ICity[]>) => (this.cities = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCities(): void {
    this.eventSubscriber = this.eventManager.subscribe('cityListModification', () => this.loadAll());
  }

  delete(city: ICity): void {
    const modalRef = this.modalService.open(CityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.city = city;
  }
}
