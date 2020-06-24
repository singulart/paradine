import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ParadineTestModule } from '../../../test.module';
import { PopularTimeUpdateComponent } from 'app/entities/popular-time/popular-time-update.component';
import { PopularTimeService } from 'app/entities/popular-time/popular-time.service';
import { PopularTime } from 'app/shared/model/popular-time.model';

describe('Component Tests', () => {
  describe('PopularTime Management Update Component', () => {
    let comp: PopularTimeUpdateComponent;
    let fixture: ComponentFixture<PopularTimeUpdateComponent>;
    let service: PopularTimeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ParadineTestModule],
        declarations: [PopularTimeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PopularTimeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PopularTimeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PopularTimeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PopularTime(123);
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
        const entity = new PopularTime();
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
