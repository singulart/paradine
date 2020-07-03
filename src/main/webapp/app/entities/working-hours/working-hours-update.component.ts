import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWorkingHours, WorkingHours } from 'app/shared/model/working-hours.model';
import { WorkingHoursService } from './working-hours.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant/restaurant.service';

@Component({
  selector: 'jhi-working-hours-update',
  templateUrl: './working-hours-update.component.html',
})
export class WorkingHoursUpdateComponent implements OnInit {
  isSaving = false;
  restaurants: IRestaurant[] = [];

  editForm = this.fb.group({
    id: [],
    dayOfWeek: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(2)]],
    closed: [null, [Validators.required]],
    openingHour: [null, [Validators.min(0), Validators.max(24)]],
    closingHour: [null, [Validators.min(0), Validators.max(24)]],
    restaurantId: [],
  });

  constructor(
    protected workingHoursService: WorkingHoursService,
    protected restaurantService: RestaurantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workingHours }) => {
      this.updateForm(workingHours);

      this.restaurantService.query().subscribe((res: HttpResponse<IRestaurant[]>) => (this.restaurants = res.body || []));
    });
  }

  updateForm(workingHours: IWorkingHours): void {
    this.editForm.patchValue({
      id: workingHours.id,
      dayOfWeek: workingHours.dayOfWeek,
      closed: workingHours.closed,
      openingHour: workingHours.openingHour,
      closingHour: workingHours.closingHour,
      restaurantId: workingHours.restaurantId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workingHours = this.createFromForm();
    if (workingHours.id !== undefined) {
      this.subscribeToSaveResponse(this.workingHoursService.update(workingHours));
    } else {
      this.subscribeToSaveResponse(this.workingHoursService.create(workingHours));
    }
  }

  private createFromForm(): IWorkingHours {
    return {
      ...new WorkingHours(),
      id: this.editForm.get(['id'])!.value,
      dayOfWeek: this.editForm.get(['dayOfWeek'])!.value,
      closed: this.editForm.get(['closed'])!.value,
      openingHour: this.editForm.get(['openingHour'])!.value,
      closingHour: this.editForm.get(['closingHour'])!.value,
      restaurantId: this.editForm.get(['restaurantId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkingHours>>): void {
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
