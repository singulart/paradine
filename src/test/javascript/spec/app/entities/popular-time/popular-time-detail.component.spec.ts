import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ParadineTestModule } from '../../../test.module';
import { PopularTimeDetailComponent } from 'app/entities/popular-time/popular-time-detail.component';
import { PopularTime } from 'app/shared/model/popular-time.model';

describe('Component Tests', () => {
  describe('PopularTime Management Detail Component', () => {
    let comp: PopularTimeDetailComponent;
    let fixture: ComponentFixture<PopularTimeDetailComponent>;
    const route = ({ data: of({ popularTime: new PopularTime(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ParadineTestModule],
        declarations: [PopularTimeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PopularTimeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PopularTimeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load popularTime on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.popularTime).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
