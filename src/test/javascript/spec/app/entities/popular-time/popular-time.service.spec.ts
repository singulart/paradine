import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PopularTimeService } from 'app/entities/popular-time/popular-time.service';
import { IPopularTime, PopularTime } from 'app/shared/model/popular-time.model';

describe('Service Tests', () => {
  describe('PopularTime Service', () => {
    let injector: TestBed;
    let service: PopularTimeService;
    let httpMock: HttpTestingController;
    let elemDefault: IPopularTime;
    let expectedResult: IPopularTime | IPopularTime[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PopularTimeService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PopularTime(0, 'AAAAAAA', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PopularTime', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PopularTime()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PopularTime', () => {
        const returnedFromService = Object.assign(
          {
            dayOfWeek: 'BBBBBB',
            occ06: 1,
            occ07: 1,
            occ08: 1,
            occ09: 1,
            occ10: 1,
            occ11: 1,
            occ12: 1,
            occ13: 1,
            occ14: 1,
            occ15: 1,
            occ16: 1,
            occ17: 1,
            occ18: 1,
            occ19: 1,
            occ20: 1,
            occ21: 1,
            occ22: 1,
            occ23: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PopularTime', () => {
        const returnedFromService = Object.assign(
          {
            dayOfWeek: 'BBBBBB',
            occ06: 1,
            occ07: 1,
            occ08: 1,
            occ09: 1,
            occ10: 1,
            occ11: 1,
            occ12: 1,
            occ13: 1,
            occ14: 1,
            occ15: 1,
            occ16: 1,
            occ17: 1,
            occ18: 1,
            occ19: 1,
            occ20: 1,
            occ21: 1,
            occ22: 1,
            occ23: 1,
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

      it('should delete a PopularTime', () => {
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
