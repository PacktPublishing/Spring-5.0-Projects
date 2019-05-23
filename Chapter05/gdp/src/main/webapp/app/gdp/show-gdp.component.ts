import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { IGdpData } from 'app/shared/model/chart.gdp.model';
import { ICountry } from 'app/shared/model/country.model';
import { Chart } from 'chart.js';

@Component({
  selector: 'jhi-show-gdp',
  templateUrl: './show-gdp.component.html',
  })
export class CountryGDPComponent implements OnInit {

    currentCountry: ICountry;
    data: IGdpData[];
    preGDPUrl = 'http://api.worldbank.org/v2/countries/';
    postGDPUrl = '/indicators/NY.GDP.MKTP.CD?format=json&per_page=' + 10;
    year = [];
    gdp = [];
    chart = [];
    noDataAvailale: any;
    constructor(
        private activatedRoute: ActivatedRoute,
        private httpClient: HttpClient
    ) {
        this.activatedRoute.data.subscribe(data => {
            this.currentCountry = data.country;
        });
    }

    ngOnInit() {
        const  gdpUrl = this.preGDPUrl + this.currentCountry.code + this.postGDPUrl;
        this.httpClient.get(gdpUrl).subscribe(res => {
            this.noDataAvailale = true;
            const gdpDataArr = res[1];
            if ( gdpDataArr ) {
                this.noDataAvailale = false;
                gdpDataArr.forEach(y => {
                    this.year.push(y.date);
                    this.gdp.push(y.value);
                });
                this.year = this.year.reverse();
                this.gdp = this.gdp.reverse();

                this.chart = new Chart('canvas', {
                    type: 'line',
                    data: {
                        labels: this.year,
                        datasets: [
                            {
                            data: this.gdp,
                            borderColor: '#3cba9f',
                            fill: true
                            }
                        ]
                    },
                    options: {
                    legend: {
                        display: false
                    },
                    scales: {
                        xAxes: [{
                            display: true
                        }],
                        yAxes: [{
                            display: true
                        }],
                    }
                    }
                });
            }
        });
    }
}
