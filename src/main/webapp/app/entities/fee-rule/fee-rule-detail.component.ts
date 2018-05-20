import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { FeeRule } from './fee-rule.model';
import { FeeRuleService } from './fee-rule.service';

@Component({
    selector: 'jhi-fee-rule-detail',
    templateUrl: './fee-rule-detail.component.html'
})
export class FeeRuleDetailComponent implements OnInit, OnDestroy {

    feeRule: FeeRule;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private feeRuleService: FeeRuleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFeeRules();
    }

    load(id) {
        this.feeRuleService.find(id)
            .subscribe((feeRuleResponse: HttpResponse<FeeRule>) => {
                this.feeRule = feeRuleResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFeeRules() {
        this.eventSubscriber = this.eventManager.subscribe(
            'feeRuleListModification',
            (response) => this.load(this.feeRule.id)
        );
    }
}
