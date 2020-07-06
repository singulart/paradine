import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AchievementService } from 'app/entities/achievement/achievement.service';
import { IAchievement, Achievement } from 'app/shared/model/achievement.model';

describe('Service Tests', () => {
  describe('Achievement Service', () => {
    let injector: TestBed;
    let service: AchievementService;
    let httpMock: HttpTestingController;
    let elemDefault: IAchievement;
    let expectedResult: IAchievement | IAchievement[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AchievementService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Achievement(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Achievement', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Achievement()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Achievement', () => {
        const returnedFromService = Object.assign(
          {
            slug: 'BBBBBB',
            nameEn: 'BBBBBB',
            nameRu: 'BBBBBB',
            nameUa: 'BBBBBB',
            descriptionEn: 'BBBBBB',
            descriptionRu: 'BBBBBB',
            descriptionUa: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Achievement', () => {
        const returnedFromService = Object.assign(
          {
            slug: 'BBBBBB',
            nameEn: 'BBBBBB',
            nameRu: 'BBBBBB',
            nameUa: 'BBBBBB',
            descriptionEn: 'BBBBBB',
            descriptionRu: 'BBBBBB',
            descriptionUa: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Achievement', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
