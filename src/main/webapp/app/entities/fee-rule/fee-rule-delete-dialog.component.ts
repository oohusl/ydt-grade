import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FeeRule } from './fee-rule.model';
import { FeeRulePopupService } from './fee-rule-popup.service';
import { FeeRuleService } from './fee-rule.service';

@Component({
    selector: 'jhi-fee-rule-delete-dialog',
    templateUrl: './fee-rule-delete-dialog.component.html'
})
export class FeeRuleDeleteDialogComponent {

    feeRule: FeeRule;

    constructor(
        private feeRuleService: FeeRuleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.feeRuleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'feeRuleListModification',
                content: 'Deleted an feeRule'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fee-rule-delete-popup',
    template: ''
})
export class FeeRuleDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private feeRulePopupService: FeeRulePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.feeRulePopupService
                .open(FeeRuleDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
