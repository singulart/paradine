import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRestaurant, Restaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from './restaurant.service';
import { ICity } from 'app/shared/model/city.model';
import { CityService } from 'app/entities/city/city.service';

@Component({
  selector: 'jhi-restaurant-update',
  templateUrl: './restaurant-update.component.html',
})
export class RestaurantUpdateComponent implements OnInit {
  isSaving = false;
  cities: ICity[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(128)]],
    altName1: [null, [Validators.maxLength(128)]],
    addressEn: [null, [Validators.required, Validators.maxLength(200)]],
    addressRu: [null, [Validators.maxLength(200)]],
    addressUa: [null, [Validators.maxLength(200)]],
    googlePlacesId: [null, [Validators.maxLength(255)]],
    geolat: [null, [Validators.required]],
    geolng: [null, [Validators.required]],
    photoUrl: [
      null,
      [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(256),
        Validators.pattern('[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&amp;//=]*)'),
      ],
    ],
    altName2: [null, [Validators.maxLength(128)]],
    altName3: [null, [Validators.maxLength(128)]],
    capacity: [null, [Validators.required, Validators.min(3)]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    uuid: [
      null,
      [
        Validators.required,
        Validators.minLength(36),
        Validators.maxLength(36),
        Validators.pattern('[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}'),
      ],
    ],
    cityId: [],
  });

  constructor(
    protected restaurantService: RestaurantService,
    protected cityService: CityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ restaurant }) => {
      if (!restaurant.id) {
        const today = moment().startOf('day');
        restaurant.createdAt = today;
        restaurant.updatedAt = today;
      }

      this.updateForm(restaurant);

      this.cityService
        .query({ filter: 'restaurant-is-null' })
        .pipe(
          map((res: HttpResponse<ICity[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICity[]) => {
          if (!restaurant.cityId) {
            this.cities = resBody;
          } else {
            this.cityService
              .find(restaurant.cityId)
              .pipe(
                map((subRes: HttpResponse<ICity>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICity[]) => (this.cities = concatRes));
          }
        });
    });
  }

  updateForm(restaurant: IRestaurant): void {
    this.editForm.patchValue({
      id: restaurant.id,
      name: restaurant.name,
      altName1: restaurant.altName1,
      addressEn: restaurant.addressEn,
      addressRu: restaurant.addressRu,
      addressUa: restaurant.addressUa,
      googlePlacesId: restaurant.googlePlacesId,
      geolat: restaurant.geolat,
      geolng: restaurant.geolng,
      photoUrl: restaurant.photoUrl,
      altName2: restaurant.altName2,
      altName3: restaurant.altName3,
      capacity: restaurant.capacity,
      createdAt: restaurant.createdAt ? restaurant.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: restaurant.updatedAt ? restaurant.updatedAt.format(DATE_TIME_FORMAT) : null,
      uuid: restaurant.uuid,
      cityId: restaurant.cityId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const restaurant = this.createFromForm();
    if (restaurant.id !== undefined) {
      this.subscribeToSaveResponse(this.restaurantService.update(restaurant));
    } else {
      this.subscribeToSaveResponse(this.restaurantService.create(restaurant));
    }
  }

  private createFromForm(): IRestaurant {
    return {
      ...new Restaurant(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      altName1: this.editForm.get(['altName1'])!.value,
      addressEn: this.editForm.get(['addressEn'])!.value,
      addressRu: this.editForm.get(['addressRu'])!.value,
      addressUa: this.editForm.get(['addressUa'])!.value,
      googlePlacesId: this.editForm.get(['googlePlacesId'])!.value,
      geolat: this.editForm.get(['geolat'])!.value,
      geolng: this.editForm.get(['geolng'])!.value,
      photoUrl: this.editForm.get(['photoUrl'])!.value,
      altName2: this.editForm.get(['altName2'])!.value,
      altName3: this.editForm.get(['altName3'])!.value,
      capacity: this.editForm.get(['capacity'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      uuid: this.editForm.get(['uuid'])!.value,
      cityId: this.editForm.get(['cityId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRestaurant>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICity): any {
    return item.id;
  }
}
