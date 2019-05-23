export const enum TrueFalse {
    T = 'T',
    F = 'F'
}

export interface ICountryLanguage {
    id?: number;
    language?: string;
    isOfficial?: TrueFalse;
    percentage?: number;
    countryName?: string;
    countryId?: number;
}

export class CountryLanguage implements ICountryLanguage {
    constructor(
        public id?: number,
        public language?: string,
        public isOfficial?: TrueFalse,
        public percentage?: number,
        public countryName?: string,
        public countryId?: number
    ) {}
}
