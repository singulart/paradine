import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ParadineTestModule } from '../../../test.module';
import { AchievementComponent } from 'app/entities/achievement/achievement.component';
import { AchievementService } from 'app/entities/achievement/achievement.service';
import { Achievement } from 'app/shared/model/achievement.model';

describe('Component Tests', () => {
  describe('Achievement Management Component', () => {
    let comp: AchievementComponent;
    let fixture: ComponentFixture<AchievementComponent>;
    let service: AchievementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ParadineTestModule],
        declarations: [AchievementComponent],
      })
        .overrideTemplate(AchievementComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AchievementComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AchievementService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Achievement(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.achievements && comp.achievements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
