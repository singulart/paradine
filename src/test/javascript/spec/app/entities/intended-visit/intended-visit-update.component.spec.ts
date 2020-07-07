import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ParadineTestModule } from '../../../test.module';
import { IntendedVisitUpdateComponent } from 'app/entities/intended-visit/intended-visit-update.component';
import { IntendedVisitService } from 'app/entities/intended-visit/intended-visit.service';
import { IntendedVisit } from 'app/shared/model/intended-visit.model';

describe('Component Tests', () => {
  describe('IntendedVisit Management Update Component', () => {
    let comp: IntendedVisitUpdateComponent;
    let fixture: ComponentFixture<IntendedVisitUpdateComponent>;
    let service: IntendedVisitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ParadineTestModule],
        declarations: [IntendedVisitUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IntendedVisitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IntendedVisitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IntendedVisitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IntendedVisit(123);
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
        const entity = new IntendedVisit();
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
