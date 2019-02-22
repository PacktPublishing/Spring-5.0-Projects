import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Country } from 'app/shared/model/country.model';
import { CountryGDPService } from './gdp.service';
import { SearchCountryComponent } from './search-country.component';
import { CountryGDPComponent } from './show-gdp.component';
import { ICountry } from 'app/shared/model/country.model';

@Injectable({ providedIn: 'root' })
export class CountryGDPResolve implements Resolve<ICountry> {
    constructor(private service: CountryGDPService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Country> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Country>) => response.ok),
                map((country: HttpResponse<Country>) => country.body)
            );
        }
        return of(new Country());
    }
}

export const countryGDPRoute: Routes = [
    {
        path: 'countries',
        component: SearchCountryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            defaultSort: 'name,asc',
            pageTitle: 'gdpApp.country.home.title'
        },
    },
    {
        path: 'showGDP/:id',
        component: CountryGDPComponent,
        resolve: {
            country: CountryGDPResolve
        }
    },
];
