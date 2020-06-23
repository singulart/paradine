import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPopularTime, PopularTime } from 'app/shared/model/popular-time.model';
import { PopularTimeService } from './popular-time.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant/restaurant.service';

@Component({
  selector: 'jhi-popular-time-update',
  templateUrl: './popular-time-update.component.html',
})
export class PopularTimeUpdateComponent implements OnInit {
  isSaving = false;
  restaurants: IRestaurant[] = [];

  editForm = this.fb.group({
    id: [],
    dayOfWeek: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(2)]],
    occ06: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    occ07: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    occ08: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    occ09: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    occ10: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    occ11: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    occ12: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    occ13: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    occ14: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    occ15: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    occ16: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    occ17: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    occ18: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    occ19: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    occ20: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    occ21: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    occ22: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    occ23: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    restaurantId: [],
  });

  constructor(
    protected popularTimeService: PopularTimeService,
    protected restaurantService: RestaurantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ popularTime }) => {
      this.updateForm(popularTime);

      this.restaurantService.query().subscribe((res: HttpResponse<IRestaurant[]>) => (this.restaurants = res.body || []));
    });
  }

  updateForm(popularTime: IPopularTime): void {
    this.editForm.patchValue({
      id: popularTime.id,
      dayOfWeek: popularTime.dayOfWeek,
      occ06: popularTime.occ06,
      occ07: popularTime.occ07,
      occ08: popularTime.occ08,
      occ09: popularTime.occ09,
      occ10: popularTime.occ10,
      occ11: popularTime.occ11,
      occ12: popularTime.occ12,
      occ13: popularTime.occ13,
      occ14: popularTime.occ14,
      occ15: popularTime.occ15,
      occ16: popularTime.occ16,
      occ17: popularTime.occ17,
      occ18: popularTime.occ18,
      occ19: popularTime.occ19,
      occ20: popularTime.occ20,
      occ21: popularTime.occ21,
      occ22: popularTime.occ22,
      occ23: popularTime.occ23,
      restaurantId: popularTime.restaurantId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const popularTime = this.createFromForm();
    if (popularTime.id !== undefined) {
      this.subscribeToSaveResponse(this.popularTimeService.update(popularTime));
    } else {
      this.subscribeToSaveResponse(this.popularTimeService.create(popularTime));
    }
  }

  private createFromForm(): IPopularTime {
    return {
      ...new PopularTime(),
      id: this.editForm.get(['id'])!.value,
      dayOfWeek: this.editForm.get(['dayOfWeek'])!.value,
      occ06: this.editForm.get(['occ06'])!.value,
      occ07: this.editForm.get(['occ07'])!.value,
      occ08: this.editForm.get(['occ08'])!.value,
      occ09: this.editForm.get(['occ09'])!.value,
      occ10: this.editForm.get(['occ10'])!.value,
      occ11: this.editForm.get(['occ11'])!.value,
      occ12: this.editForm.get(['occ12'])!.value,
      occ13: this.editForm.get(['occ13'])!.value,
      occ14: this.editForm.get(['occ14'])!.value,
      occ15: this.editForm.get(['occ15'])!.value,
      occ16: this.editForm.get(['occ16'])!.value,
      occ17: this.editForm.get(['occ17'])!.value,
      occ18: this.editForm.get(['occ18'])!.value,
      occ19: this.editForm.get(['occ19'])!.value,
      occ20: this.editForm.get(['occ20'])!.value,
      occ21: this.editForm.get(['occ21'])!.value,
      occ22: this.editForm.get(['occ22'])!.value,
      occ23: this.editForm.get(['occ23'])!.value,
      restaurantId: this.editForm.get(['restaurantId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPopularTime>>): void {
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

  trackById(index: number, item: IRestaurant): any {
    return item.id;
  }
}
