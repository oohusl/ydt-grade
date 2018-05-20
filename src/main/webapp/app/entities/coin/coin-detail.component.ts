import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Coin } from './coin.model';
import { CoinService } from './coin.service';

@Component({
    selector: 'jhi-coin-detail',
    templateUrl: './coin-detail.component.html'
})
export class CoinDetailComponent implements OnInit, OnDestroy {

    coin: Coin;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private coinService: CoinService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCoins();
    }

    load(id) {
        this.coinService.find(id)
            .subscribe((coinResponse: HttpResponse<Coin>) => {
                this.coin = coinResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCoins() {
        this.eventSubscriber = this.eventManager.subscribe(
            'coinListModification',
            (response) => this.load(this.coin.id)
        );
    }
}
