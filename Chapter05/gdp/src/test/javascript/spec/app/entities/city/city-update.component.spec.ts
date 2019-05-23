/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GdpTestModule } from '../../../test.module';
import { CityUpdateComponent } from 'app/entities/city/city-update.component';
import { CityService } from 'app/entities/city/city.service';
import { City } from 'app/shared/model/city.model';

describe('Component Tests', () => {
    describe('City Management Update Component', () => {
        let comp: CityUpdateComponent;
        let fixture: ComponentFixture<CityUpdateComponent>;
        let service: CityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdpTestModule],
                declarations: [CityUpdateComponent]
            })
                .overrideTemplate(CityUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CityUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CityService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new City(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.city = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new City();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.city = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
