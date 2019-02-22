import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GdpSharedModule } from 'app/shared';
import { SearchCountryComponent} from './search-country.component';
import { CountryGDPComponent} from './show-gdp.component';
import { countryGDPRoute} from './gdp.route';

const ENTITY_STATES = [...countryGDPRoute];

@NgModule({
    imports: [GdpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SearchCountryComponent,
        CountryGDPComponent,
    ],
    entryComponents: [SearchCountryComponent , CountryGDPComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CountryGDPModule {}
