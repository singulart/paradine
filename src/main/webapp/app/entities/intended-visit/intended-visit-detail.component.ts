import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIntendedVisit } from 'app/shared/model/intended-visit.model';

@Component({
  selector: 'jhi-intended-visit-detail',
  templateUrl: './intended-visit-detail.component.html',
})
export class IntendedVisitDetailComponent implements OnInit {
  intendedVisit: IIntendedVisit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ intendedVisit }) => (this.intendedVisit = intendedVisit));
  }

  previousState(): void {
    window.history.back();
  }
}
