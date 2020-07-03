import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ParadineTestModule } from '../../../test.module';
import { WorkingHoursDetailComponent } from 'app/entities/working-hours/working-hours-detail.component';
import { WorkingHours } from 'app/shared/model/working-hours.model';

describe('Component Tests', () => {
  describe('WorkingHours Management Detail Component', () => {
    let comp: WorkingHoursDetailComponent;
    let fixture: ComponentFixture<WorkingHoursDetailComponent>;
    const route = ({ data: of({ workingHours: new WorkingHours(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ParadineTestModule],
        declarations: [WorkingHoursDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WorkingHoursDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkingHoursDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load workingHours on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.workingHours).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
