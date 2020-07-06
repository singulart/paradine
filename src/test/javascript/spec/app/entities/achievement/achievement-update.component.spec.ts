import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ParadineTestModule } from '../../../test.module';
import { AchievementUpdateComponent } from 'app/entities/achievement/achievement-update.component';
import { AchievementService } from 'app/entities/achievement/achievement.service';
import { Achievement } from 'app/shared/model/achievement.model';

describe('Component Tests', () => {
  describe('Achievement Management Update Component', () => {
    let comp: AchievementUpdateComponent;
    let fixture: ComponentFixture<AchievementUpdateComponent>;
    let service: AchievementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ParadineTestModule],
        declarations: [AchievementUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AchievementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AchievementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AchievementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Achievement(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Achievement();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
