/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GdpTestModule } from '../../../test.module';
import { CountryLanguageDeleteDialogComponent } from 'app/entities/country-language/country-language-delete-dialog.component';
import { CountryLanguageService } from 'app/entities/country-language/country-language.service';

describe('Component Tests', () => {
    describe('CountryLanguage Management Delete Component', () => {
        let comp: CountryLanguageDeleteDialogComponent;
        let fixture: ComponentFixture<CountryLanguageDeleteDialogComponent>;
        let service: CountryLanguageService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdpTestModule],
                declarations: [CountryLanguageDeleteDialogComponent]
            })
                .overrideTemplate(CountryLanguageDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CountryLanguageDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CountryLanguageService);
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
