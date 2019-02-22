/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GdpTestModule } from '../../../test.module';
import { CityDeleteDialogComponent } from 'app/entities/city/city-delete-dialog.component';
import { CityService } from 'app/entities/city/city.service';

describe('Component Tests', () => {
    describe('City Management Delete Component', () => {
        let comp: CityDeleteDialogComponent;
        let fixture: ComponentFixture<CityDeleteDialogComponent>;
        let service: CityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdpTestModule],
                declarations: [CityDeleteDialogComponent]
            })
                .overrideTemplate(CityDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
