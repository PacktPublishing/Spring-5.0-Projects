import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GdpCountryModule } from './country/country.module';
import { GdpCityModule } from './city/city.module';
import { GdpCountryLanguageModule } from './country-language/country-language.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        GdpCountryModule,
        GdpCityModule,
        GdpCountryLanguageModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GdpEntityModule {}
