import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAchievement } from 'app/shared/model/achievement.model';
import { AchievementService } from './achievement.service';
import { AchievementDeleteDialogComponent } from './achievement-delete-dialog.component';

@Component({
  selector: 'jhi-achievement',
  templateUrl: './achievement.component.html',
})
export class AchievementComponent implements OnInit, OnDestroy {
  achievements?: IAchievement[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected achievementService: AchievementService,
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
      this.achievementService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IAchievement[]>) => (this.achievements = res.body || []));
      return;
    }

    this.achievementService.query().subscribe((res: HttpResponse<IAchievement[]>) => (this.achievements = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAchievements();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAchievement): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAchievements(): void {
    this.eventSubscriber = this.eventManager.subscribe('achievementListModification', () => this.loadAll());
  }

  delete(achievement: IAchievement): void {
    const modalRef = this.modalService.open(AchievementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.achievement = achievement;
  }
}
