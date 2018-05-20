import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FeeRule } from './fee-rule.model';
import { FeeRulePopupService } from './fee-rule-popup.service';
import { FeeRuleService } from './fee-rule.service';

@Component({
    selector: 'jhi-fee-rule-dialog',
    templateUrl: './fee-rule-dialog.component.html'
})
export class FeeRuleDialogComponent implements OnInit {

    feeRule: FeeRule;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private feeRuleService: FeeRuleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.feeRule.id !== undefined) {
            this.subscribeToSaveResponse(
                this.feeRuleService.update(this.feeRule));
        } else {
            this.subscribeToSaveResponse(
                this.feeRuleService.create(this.feeRule));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<FeeRule>>) {
        result.subscribe((res: HttpResponse<FeeRule>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: FeeRule) {
        this.eventManager.broadcast({ name: 'feeRuleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-fee-rule-popup',
    template: ''
})
export class FeeRulePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private feeRulePopupService: FeeRulePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.feeRulePopupService
                    .open(FeeRuleDialogComponent as Component, params['id']);
            } else {
                this.feeRulePopupService
                    .open(FeeRuleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
