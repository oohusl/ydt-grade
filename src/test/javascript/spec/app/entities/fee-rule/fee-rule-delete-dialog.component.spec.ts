/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { YdtTestModule } from '../../../test.module';
import { FeeRuleDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/fee-rule/fee-rule-delete-dialog.component';
import { FeeRuleService } from '../../../../../../main/webapp/app/entities/fee-rule/fee-rule.service';

describe('Component Tests', () => {

    describe('FeeRule Management Delete Component', () => {
        let comp: FeeRuleDeleteDialogComponent;
        let fixture: ComponentFixture<FeeRuleDeleteDialogComponent>;
        let service: FeeRuleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [YdtTestModule],
                declarations: [FeeRuleDeleteDialogComponent],
                providers: [
                    FeeRuleService
                ]
            })
            .overrideTemplate(FeeRuleDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FeeRuleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FeeRuleService);
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
