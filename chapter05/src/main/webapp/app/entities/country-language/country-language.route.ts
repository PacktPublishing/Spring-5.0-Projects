import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CountryLanguage } from 'app/shared/model/country-language.model';
import { CountryLanguageService } from './country-language.service';
import { CountryLanguageComponent } from './country-language.component';
import { CountryLanguageDetailComponent } from './country-language-detail.component';
import { CountryLanguageUpdateComponent } from './country-language-update.component';
import { CountryLanguageDeletePopupComponent } from './country-language-delete-dialog.component';
import { ICountryLanguage } from 'app/shared/model/country-language.model';

@Injectable({ providedIn: 'root' })
export class CountryLanguageResolve implements Resolve<ICountryLanguage> {
    constructor(private service: CountryLanguageService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CountryLanguage> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CountryLanguage>) => response.ok),
                map((countryLanguage: HttpResponse<CountryLanguage>) => countryLanguage.body)
            );
        }
        return of(new CountryLanguage());
    }
}

export const countryLanguageRoute: Routes = [
    {
        path: 'country-language',
        component: CountryLanguageComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'gdpApp.countryLanguage.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'country-language/:id/view',
        component: CountryLanguageDetailComponent,
        resolve: {
            countryLanguage: CountryLanguageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gdpApp.countryLanguage.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'country-language/new',
        component: CountryLanguageUpdateComponent,
        resolve: {
            countryLanguage: CountryLanguageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gdpApp.countryLanguage.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'country-language/:id/edit',
        component: CountryLanguageUpdateComponent,
        resolve: {
            countryLanguage: CountryLanguageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gdpApp.countryLanguage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const countryLanguagePopupRoute: Routes = [
    {
        path: 'country-language/:id/delete',
        component: CountryLanguageDeletePopupComponent,
        resolve: {
            countryLanguage: CountryLanguageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gdpApp.countryLanguage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
