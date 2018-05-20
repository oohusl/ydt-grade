import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FeeRule } from './fee-rule.model';
import { FeeRuleService } from './fee-rule.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-fee-rule',
    templateUrl: './fee-rule.component.html'
})
export class FeeRuleComponent implements OnInit, OnDestroy {
feeRules: FeeRule[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private feeRuleService: FeeRuleService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.feeRuleService.query().subscribe(
            (res: HttpResponse<FeeRule[]>) => {
                this.feeRules = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFeeRules();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FeeRule) {
        return item.id;
    }
    registerChangeInFeeRules() {
        this.eventSubscriber = this.eventManager.subscribe('feeRuleListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
