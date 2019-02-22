import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICountryLanguage } from 'app/shared/model/country-language.model';
import { CountryLanguageService } from './country-language.service';
import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from 'app/entities/country';

@Component({
    selector: 'jhi-country-language-update',
    templateUrl: './country-language-update.component.html'
})
export class CountryLanguageUpdateComponent implements OnInit {
    countryLanguage: ICountryLanguage;
    isSaving: boolean;

    countries: ICountry[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private countryLanguageService: CountryLanguageService,
        private countryService: CountryService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ countryLanguage }) => {
            this.countryLanguage = countryLanguage;
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
        if (this.countryLanguage.id !== undefined) {
            this.subscribeToSaveResponse(this.countryLanguageService.update(this.countryLanguage));
        } else {
            this.subscribeToSaveResponse(this.countryLanguageService.create(this.countryLanguage));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICountryLanguage>>) {
        result.subscribe((res: HttpResponse<ICountryLanguage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
