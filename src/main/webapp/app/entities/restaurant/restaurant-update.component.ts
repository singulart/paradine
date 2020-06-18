import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRestaurant, Restaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from './restaurant.service';

@Component({
  selector: 'jhi-restaurant-update',
  templateUrl: './restaurant-update.component.html',
})
export class RestaurantUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    uuid: [
      null,
      [
        Validators.required,
        Validators.minLength(36),
        Validators.maxLength(36),
        Validators.pattern('[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}'),
      ],
    ],
    capacity: [null, [Validators.required, Validators.min(3)]],
    geolat: [null, [Validators.required]],
    geolng: [null, [Validators.required]],
    name: [
      null,
      [Validators.required, Validators.minLength(3), Validators.maxLength(128), Validators.pattern('([a-zA-Z0-9]| |,|&amp;|\\.)+')],
    ],
    photoUrl: [
      null,
      [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(256),
        Validators.pattern('[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&amp;//=]*)'),
      ],
    ],
    altName1: [null, [Validators.maxLength(128)]],
    altName2: [null, [Validators.maxLength(128)]],
    altName3: [null, [Validators.maxLength(128)]],
  });

  constructor(protected restaurantService: RestaurantService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ restaurant }) => {
      this.updateForm(restaurant);
    });
  }

  updateForm(restaurant: IRestaurant): void {
    this.editForm.patchValue({
      id: restaurant.id,
      uuid: restaurant.uuid,
      capacity: restaurant.capacity,
      geolat: restaurant.geolat,
      geolng: restaurant.geolng,
      name: restaurant.name,
      photoUrl: restaurant.photoUrl,
      altName1: restaurant.altName1,
      altName2: restaurant.altName2,
      altName3: restaurant.altName3,
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
      uuid: this.editForm.get(['uuid'])!.value,
      capacity: this.editForm.get(['capacity'])!.value,
      geolat: this.editForm.get(['geolat'])!.value,
      geolng: this.editForm.get(['geolng'])!.value,
      name: this.editForm.get(['name'])!.value,
      photoUrl: this.editForm.get(['photoUrl'])!.value,
      altName1: this.editForm.get(['altName1'])!.value,
      altName2: this.editForm.get(['altName2'])!.value,
      altName3: this.editForm.get(['altName3'])!.value,
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
}
