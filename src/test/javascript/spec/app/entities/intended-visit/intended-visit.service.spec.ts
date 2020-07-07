import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IntendedVisitService } from 'app/entities/intended-visit/intended-visit.service';
import { IIntendedVisit, IntendedVisit } from 'app/shared/model/intended-visit.model';

describe('Service Tests', () => {
  describe('IntendedVisit Service', () => {
    let injector: TestBed;
    let service: IntendedVisitService;
    let httpMock: HttpTestingController;
    let elemDefault: IIntendedVisit;
    let expectedResult: IIntendedVisit | IIntendedVisit[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(IntendedVisitService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new IntendedVisit(0, 'AAAAAAA', currentDate, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            visitStartDate: currentDate.format(DATE_TIME_FORMAT),
            visitEndDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a IntendedVisit', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            visitStartDate: currentDate.format(DATE_TIME_FORMAT),
            visitEndDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            visitStartDate: currentDate,
            visitEndDate: currentDate,
          },
          returnedFromService
        );

        service.create(new IntendedVisit()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a IntendedVisit', () => {
        const returnedFromService = Object.assign(
          {
            uuid: 'BBBBBB',
            visitStartDate: currentDate.format(DATE_TIME_FORMAT),
            visitEndDate: currentDate.format(DATE_TIME_FORMAT),
            cancelled: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            visitStartDate: currentDate,
            visitEndDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of IntendedVisit', () => {
        const returnedFromService = Object.assign(
          {
            uuid: 'BBBBBB',
            visitStartDate: currentDate.format(DATE_TIME_FORMAT),
            visitEndDate: currentDate.format(DATE_TIME_FORMAT),
            cancelled: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            visitStartDate: currentDate,
            visitEndDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a IntendedVisit', () => {
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
