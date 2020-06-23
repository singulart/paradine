import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPopularTime } from 'app/shared/model/popular-time.model';

@Component({
  selector: 'jhi-popular-time-detail',
  templateUrl: './popular-time-detail.component.html',
})
export class PopularTimeDetailComponent implements OnInit {
  popularTime: IPopularTime | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ popularTime }) => (this.popularTime = popularTime));
  }

  previousState(): void {
    window.history.back();
  }
}
