import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICountry } from 'app/shared/model/country.model';

@Component({
    selector: 'jhi-country-detail',
    templateUrl: './country-detail.component.html'
})
export class CountryDetailComponent implements OnInit {
    country: ICountry;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ country }) => {
            this.country = country;
        });
    }

    previousState() {
        window.history.back();
    }
}
