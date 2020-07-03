import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkingHours } from 'app/shared/model/working-hours.model';

@Component({
  selector: 'jhi-working-hours-detail',
  templateUrl: './working-hours-detail.component.html',
})
export class WorkingHoursDetailComponent implements OnInit {
  workingHours: IWorkingHours | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workingHours }) => (this.workingHours = workingHours));
  }

  previousState(): void {
    window.history.back();
  }
}
