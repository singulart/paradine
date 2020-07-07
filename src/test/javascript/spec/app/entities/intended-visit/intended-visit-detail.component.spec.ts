import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ParadineTestModule } from '../../../test.module';
import { IntendedVisitDetailComponent } from 'app/entities/intended-visit/intended-visit-detail.component';
import { IntendedVisit } from 'app/shared/model/intended-visit.model';

describe('Component Tests', () => {
  describe('IntendedVisit Management Detail Component', () => {
    let comp: IntendedVisitDetailComponent;
    let fixture: ComponentFixture<IntendedVisitDetailComponent>;
    const route = ({ data: of({ intendedVisit: new IntendedVisit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ParadineTestModule],
        declarations: [IntendedVisitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IntendedVisitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IntendedVisitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load intendedVisit on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.intendedVisit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
