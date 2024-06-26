import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRestaurant } from 'app/shared/model/restaurant.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RestaurantService } from './restaurant.service';
import { RestaurantDeleteDialogComponent } from './restaurant-delete-dialog.component';

@Component({
  selector: 'jhi-restaurant',
  templateUrl: './restaurant.component.html',
})
export class RestaurantComponent implements OnInit, OnDestroy {
  restaurants: IRestaurant[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  currentSearch: string;

  constructor(
    protected restaurantService: RestaurantService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute
  ) {
    this.restaurants = [];
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
      this.restaurantService
        .search({
          query: this.currentSearch,
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe((res: HttpResponse<IRestaurant[]>) => this.paginateRestaurants(res.body, res.headers));
      return;
    }

    this.restaurantService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IRestaurant[]>) => this.paginateRestaurants(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.restaurants = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  search(query: string): void {
    this.restaurants = [];
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
    this.registerChangeInRestaurants();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRestaurant): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRestaurants(): void {
    this.eventSubscriber = this.eventManager.subscribe('restaurantListModification', () => this.reset());
  }

  delete(restaurant: IRestaurant): void {
    const modalRef = this.modalService.open(RestaurantDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.restaurant = restaurant;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRestaurants(data: IRestaurant[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.restaurants.push(data[i]);
      }
    }
  }
}
