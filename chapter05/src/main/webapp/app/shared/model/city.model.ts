export interface ICity {
    id?: number;
    name?: string;
    distrct?: string;
    population?: number;
    countryName?: string;
    countryId?: number;
}

export class City implements ICity {
    constructor(
        public id?: number,
        public name?: string,
        public distrct?: string,
        public population?: number,
        public countryName?: string,
        public countryId?: number
    ) {}
}
