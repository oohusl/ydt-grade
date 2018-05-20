import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { OrdrCoin } from './ordr-coin.model';
import { OrdrCoinService } from './ordr-coin.service';

@Component({
    selector: 'jhi-ordr-coin-detail',
    templateUrl: './ordr-coin-detail.component.html'
})
export class OrdrCoinDetailComponent implements OnInit, OnDestroy {

    ordrCoin: OrdrCoin;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ordrCoinService: OrdrCoinService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrdrCoins();
    }

    load(id) {
        this.ordrCoinService.find(id)
            .subscribe((ordrCoinResponse: HttpResponse<OrdrCoin>) => {
                this.ordrCoin = ordrCoinResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrdrCoins() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ordrCoinListModification',
            (response) => this.load(this.ordrCoin.id)
        );
    }
}
