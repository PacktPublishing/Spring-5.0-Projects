import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Country } from 'app/shared/model/country.model';
import { CountryService } from './country.service';
import { CountryComponent } from './country.component';
import { CountryDetailComponent } from './country-detail.component';
import { CountryUpdateComponent } from './country-update.component';
import { CountryDeletePopupComponent } from './country-delete-dialog.component';
import { ICountry } from 'app/shared/model/country.model';

@Injectable({ providedIn: 'root' })
export class CountryResolve implements Resolve<ICountry> {
    constructor(private service: CountryService) {}

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

export const countryRoute: Routes = [
    {
        path: 'country',
        component: CountryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'gdpApp.country.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'country/:id/view',
        component: CountryDetailComponent,
        resolve: {
            country: CountryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gdpApp.country.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'country/new',
        component: CountryUpdateComponent,
        resolve: {
            country: CountryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gdpApp.country.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'country/:id/edit',
        component: CountryUpdateComponent,
        resolve: {
            country: CountryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gdpApp.country.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const countryPopupRoute: Routes = [
    {
        path: 'country/:id/delete',
        component: CountryDeletePopupComponent,
        resolve: {
            country: CountryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gdpApp.country.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
