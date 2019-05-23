import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICity } from 'app/shared/model/city.model';
import { CityService } from './city.service';
import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from 'app/entities/country';

@Component({
    selector: 'jhi-city-update',
    templateUrl: './city-update.component.html'
})
export class CityUpdateComponent implements OnInit {
    city: ICity;
    isSaving: boolean;

    countries: ICountry[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private cityService: CityService,
        private countryService: CountryService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ city }) => {
            this.city = city;
        });
        this.countryService.query().subscribe(
            (res: HttpResponse<ICountry[]>) => {
                this.countries = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.city.id !== undefined) {
            this.subscribeToSaveResponse(this.cityService.update(this.city));
        } else {
            this.subscribeToSaveResponse(this.cityService.create(this.city));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICity>>) {
        result.subscribe((res: HttpResponse<ICity>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCountryById(index: number, item: ICountry) {
        return item.id;
    }
}
