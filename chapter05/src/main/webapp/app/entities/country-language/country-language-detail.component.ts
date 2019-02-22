import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICountryLanguage } from 'app/shared/model/country-language.model';

@Component({
    selector: 'jhi-country-language-detail',
    templateUrl: './country-language-detail.component.html'
})
export class CountryLanguageDetailComponent implements OnInit {
    countryLanguage: ICountryLanguage;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ countryLanguage }) => {
            this.countryLanguage = countryLanguage;
        });
    }

    previousState() {
        window.history.back();
    }
}
