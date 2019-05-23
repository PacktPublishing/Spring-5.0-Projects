import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';

import { ICountry } from 'app/shared/model/country.model';

import { ITEMS_PER_PAGE } from 'app/shared';
import { CountryGDPService } from './gdp.service';

@Component({
  selector: 'jhi-search-country',
  templateUrl: './search-country.component.html',
})
export class SearchCountryComponent implements OnInit {

    countries: ICountry[];
    routeData: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    nameFilter: String;
    continentFilter: String;

    constructor(
        private countryGDPService: CountryGDPService,
        private activatedRoute: ActivatedRoute,
        private router: Router,
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    loadAll() {
        this.countryGDPService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort(),
                'name.contains': this.nameFilter,
                'continent.equals' : this.continentFilter
            })
            .subscribe(
                (res: HttpResponse<ICountry[]>) => this.paginateCountries(res.body, res.headers),
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/countries'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/countries',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.continentFilter = '';
        this.nameFilter = '';
        this.loadAll();
    }
    searchCountries() {
       this.clear();
    }

    trackId(index: number, item: ICountry) {
        return item.id;
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        return result;
    }

    private paginateCountries(data: ICountry[], headers: HttpHeaders) {
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.countries = data;
    }
}
