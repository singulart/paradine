import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ParadineTestModule } from '../../../test.module';
import { WorkingHoursUpdateComponent } from 'app/entities/working-hours/working-hours-update.component';
import { WorkingHoursService } from 'app/entities/working-hours/working-hours.service';
import { WorkingHours } from 'app/shared/model/working-hours.model';

describe('Component Tests', () => {
  describe('WorkingHours Management Update Component', () => {
    let comp: WorkingHoursUpdateComponent;
    let fixture: ComponentFixture<WorkingHoursUpdateComponent>;
    let service: WorkingHoursService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ParadineTestModule],
        declarations: [WorkingHoursUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WorkingHoursUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkingHoursUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkingHoursService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorkingHours(123);
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
        const entity = new WorkingHours();
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
