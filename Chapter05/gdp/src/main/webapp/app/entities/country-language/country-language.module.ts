import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GdpSharedModule } from 'app/shared';
import {
    CountryLanguageComponent,
    CountryLanguageDetailComponent,
    CountryLanguageUpdateComponent,
    CountryLanguageDeletePopupComponent,
    CountryLanguageDeleteDialogComponent,
    countryLanguageRoute,
    countryLanguagePopupRoute
} from './';

const ENTITY_STATES = [...countryLanguageRoute, ...countryLanguagePopupRoute];

@NgModule({
    imports: [GdpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CountryLanguageComponent,
        CountryLanguageDetailComponent,
        CountryLanguageUpdateComponent,
        CountryLanguageDeleteDialogComponent,
        CountryLanguageDeletePopupComponent
    ],
    entryComponents: [
        CountryLanguageComponent,
        CountryLanguageUpdateComponent,
        CountryLanguageDeleteDialogComponent,
        CountryLanguageDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GdpCountryLanguageModule {}
