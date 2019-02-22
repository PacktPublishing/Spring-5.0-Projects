import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICountry } from 'app/shared/model/country.model';

type EntityResponseType = HttpResponse<ICountry>;
type EntityArrayResponseType = HttpResponse<ICountry[]>;

@Injectable({ providedIn: 'root'})
export class CountryGDPService {

    public searchCountryUrl = SERVER_API_URL + 'api/open/search-countries';
    public showGDPUrl = SERVER_API_URL + 'api/open/show-gdp';

    constructor(private http: HttpClient) { }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICountry>(`${this.showGDPUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICountry[]>(this.searchCountryUrl, { params: options, observe: 'response' });
    }
}
