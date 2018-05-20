/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { YdtTestModule } from '../../../test.module';
import { OrdrCoinDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/ordr-coin/ordr-coin-delete-dialog.component';
import { OrdrCoinService } from '../../../../../../main/webapp/app/entities/ordr-coin/ordr-coin.service';

describe('Component Tests', () => {

    describe('OrdrCoin Management Delete Component', () => {
        let comp: OrdrCoinDeleteDialogComponent;
        let fixture: ComponentFixture<OrdrCoinDeleteDialogComponent>;
        let service: OrdrCoinService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [YdtTestModule],
                declarations: [OrdrCoinDeleteDialogComponent],
                providers: [
                    OrdrCoinService
                ]
            })
            .overrideTemplate(OrdrCoinDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrdrCoinDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdrCoinService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
