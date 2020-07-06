import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAchievement, Achievement } from 'app/shared/model/achievement.model';
import { AchievementService } from './achievement.service';

@Component({
  selector: 'jhi-achievement-update',
  templateUrl: './achievement-update.component.html',
})
export class AchievementUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    slug: [null, [Validators.required]],
    nameEn: [null, [Validators.required]],
    nameRu: [],
    nameUa: [],
    descriptionEn: [],
    descriptionRu: [],
    descriptionUa: [],
  });

  constructor(protected achievementService: AchievementService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ achievement }) => {
      this.updateForm(achievement);
    });
  }

  updateForm(achievement: IAchievement): void {
    this.editForm.patchValue({
      id: achievement.id,
      slug: achievement.slug,
      nameEn: achievement.nameEn,
      nameRu: achievement.nameRu,
      nameUa: achievement.nameUa,
      descriptionEn: achievement.descriptionEn,
      descriptionRu: achievement.descriptionRu,
      descriptionUa: achievement.descriptionUa,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const achievement = this.createFromForm();
    if (achievement.id !== undefined) {
      this.subscribeToSaveResponse(this.achievementService.update(achievement));
    } else {
      this.subscribeToSaveResponse(this.achievementService.create(achievement));
    }
  }

  private createFromForm(): IAchievement {
    return {
      ...new Achievement(),
      id: this.editForm.get(['id'])!.value,
      slug: this.editForm.get(['slug'])!.value,
      nameEn: this.editForm.get(['nameEn'])!.value,
      nameRu: this.editForm.get(['nameRu'])!.value,
      nameUa: this.editForm.get(['nameUa'])!.value,
      descriptionEn: this.editForm.get(['descriptionEn'])!.value,
      descriptionRu: this.editForm.get(['descriptionRu'])!.value,
      descriptionUa: this.editForm.get(['descriptionUa'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAchievement>>): void {
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
