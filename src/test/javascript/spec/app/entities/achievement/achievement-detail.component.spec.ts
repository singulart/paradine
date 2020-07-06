import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ParadineTestModule } from '../../../test.module';
import { AchievementDetailComponent } from 'app/entities/achievement/achievement-detail.component';
import { Achievement } from 'app/shared/model/achievement.model';

describe('Component Tests', () => {
  describe('Achievement Management Detail Component', () => {
    let comp: AchievementDetailComponent;
    let fixture: ComponentFixture<AchievementDetailComponent>;
    const route = ({ data: of({ achievement: new Achievement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ParadineTestModule],
        declarations: [AchievementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AchievementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AchievementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load achievement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.achievement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
