import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from './country.service';

@Component({
    selector: 'jhi-country-update',
    templateUrl: './country-update.component.html'
})
export class CountryUpdateComponent implements OnInit {
    country: ICountry;
    isSaving: boolean;

    constructor(private countryService: CountryService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ country }) => {
            this.country = country;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.country.id !== undefined) {
            this.subscribeToSaveResponse(this.countryService.update(this.country));
        } else {
            this.subscribeToSaveResponse(this.countryService.create(this.country));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICountry>>) {
        result.subscribe((res: HttpResponse<ICountry>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
