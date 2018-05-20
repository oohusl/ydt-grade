/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { YdtTestModule } from '../../../test.module';
import { OrdrCoinDialogComponent } from '../../../../../../main/webapp/app/entities/ordr-coin/ordr-coin-dialog.component';
import { OrdrCoinService } from '../../../../../../main/webapp/app/entities/ordr-coin/ordr-coin.service';
import { OrdrCoin } from '../../../../../../main/webapp/app/entities/ordr-coin/ordr-coin.model';
import { OrdrService } from '../../../../../../main/webapp/app/entities/ordr';

describe('Component Tests', () => {

    describe('OrdrCoin Management Dialog Component', () => {
        let comp: OrdrCoinDialogComponent;
        let fixture: ComponentFixture<OrdrCoinDialogComponent>;
        let service: OrdrCoinService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [YdtTestModule],
                declarations: [OrdrCoinDialogComponent],
                providers: [
                    OrdrService,
                    OrdrCoinService
                ]
            })
            .overrideTemplate(OrdrCoinDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrdrCoinDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdrCoinService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new OrdrCoin(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.ordrCoin = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'ordrCoinListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new OrdrCoin();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.ordrCoin = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'ordrCoinListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
