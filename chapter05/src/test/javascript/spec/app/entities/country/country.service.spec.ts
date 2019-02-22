/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { CountryService } from 'app/entities/country/country.service';
import { ICountry, Country, Continent } from 'app/shared/model/country.model';

describe('Service Tests', () => {
    describe('Country Service', () => {
        let injector: TestBed;
        let service: CountryService;
        let httpMock: HttpTestingController;
        let elemDefault: ICountry;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CountryService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Country(0, 'AAAAAAA', 'AAAAAAA', Continent.ASIA, 'AAAAAAA', 0, 0, 0, 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Country', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Country(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Country', async () => {
                const returnedFromService = Object.assign(
                    {
                        code: 'BBBBBB',
                        name: 'BBBBBB',
                        continent: 'BBBBBB',
                        region: 'BBBBBB',
                        surfaceArea: 1,
                        population: 1,
                        lifeExpectancy: 1,
                        localName: 'BBBBBB',
                        governmentForm: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Country', async () => {
                const returnedFromService = Object.assign(
                    {
                        code: 'BBBBBB',
                        name: 'BBBBBB',
                        continent: 'BBBBBB',
                        region: 'BBBBBB',
                        surfaceArea: 1,
                        population: 1,
                        lifeExpectancy: 1,
                        localName: 'BBBBBB',
                        governmentForm: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Country', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
