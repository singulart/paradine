import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IIntendedVisit, IntendedVisit } from 'app/shared/model/intended-visit.model';
import { IntendedVisitService } from './intended-visit.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant/restaurant.service';

type SelectableEntity = IUser | IRestaurant;

@Component({
  selector: 'jhi-intended-visit-update',
  templateUrl: './intended-visit-update.component.html',
})
export class IntendedVisitUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  restaurants: IRestaurant[] = [];

  editForm = this.fb.group({
    id: [],
    visitStartDate: [null, [Validators.required]],
    visitEndDate: [null, [Validators.required]],
    cancelled: [null, [Validators.required]],
    visitingUserId: [null, Validators.required],
    restaurantId: [null, Validators.required],
  });

  constructor(
    protected intendedVisitService: IntendedVisitService,
    protected userService: UserService,
    protected restaurantService: RestaurantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ intendedVisit }) => {
      if (!intendedVisit.id) {
        const today = moment().startOf('day');
        intendedVisit.visitStartDate = today;
        intendedVisit.visitEndDate = today;
      }

      this.updateForm(intendedVisit);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.restaurantService
        .query({ 'intendedVisitId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IRestaurant[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IRestaurant[]) => {
          if (!intendedVisit.restaurantId) {
            this.restaurants = resBody;
          } else {
            this.restaurantService
              .find(intendedVisit.restaurantId)
              .pipe(
                map((subRes: HttpResponse<IRestaurant>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IRestaurant[]) => (this.restaurants = concatRes));
          }
        });
    });
  }

  updateForm(intendedVisit: IIntendedVisit): void {
    this.editForm.patchValue({
      id: intendedVisit.id,
      visitStartDate: intendedVisit.visitStartDate ? intendedVisit.visitStartDate.format(DATE_TIME_FORMAT) : null,
      visitEndDate: intendedVisit.visitEndDate ? intendedVisit.visitEndDate.format(DATE_TIME_FORMAT) : null,
      cancelled: intendedVisit.cancelled,
      visitingUserId: intendedVisit.visitingUserId,
      restaurantId: intendedVisit.restaurantId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const intendedVisit = this.createFromForm();
    if (intendedVisit.id !== undefined) {
      this.subscribeToSaveResponse(this.intendedVisitService.update(intendedVisit));
    } else {
      this.subscribeToSaveResponse(this.intendedVisitService.create(intendedVisit));
    }
  }

  private createFromForm(): IIntendedVisit {
    return {
      ...new IntendedVisit(),
      id: this.editForm.get(['id'])!.value,
      visitStartDate: this.editForm.get(['visitStartDate'])!.value
        ? moment(this.editForm.get(['visitStartDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      visitEndDate: this.editForm.get(['visitEndDate'])!.value
        ? moment(this.editForm.get(['visitEndDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      cancelled: this.editForm.get(['cancelled'])!.value,
      visitingUserId: this.editForm.get(['visitingUserId'])!.value,
      restaurantId: this.editForm.get(['restaurantId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIntendedVisit>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
