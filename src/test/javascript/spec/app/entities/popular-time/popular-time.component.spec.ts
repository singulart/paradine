import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ThehipstaTestModule } from '../../../test.module';
import { PopularTimeComponent } from 'app/entities/popular-time/popular-time.component';
import { PopularTimeService } from 'app/entities/popular-time/popular-time.service';
import { PopularTime } from 'app/shared/model/popular-time.model';

describe('Component Tests', () => {
  describe('PopularTime Management Component', () => {
    let comp: PopularTimeComponent;
    let fixture: ComponentFixture<PopularTimeComponent>;
    let service: PopularTimeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ThehipstaTestModule],
        declarations: [PopularTimeComponent],
      })
        .overrideTemplate(PopularTimeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PopularTimeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PopularTimeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PopularTime(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.popularTimes && comp.popularTimes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
