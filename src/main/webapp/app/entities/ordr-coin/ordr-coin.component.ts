import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OrdrCoin } from './ordr-coin.model';
import { OrdrCoinService } from './ordr-coin.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-ordr-coin',
    templateUrl: './ordr-coin.component.html'
})
export class OrdrCoinComponent implements OnInit, OnDestroy {
ordrCoins: OrdrCoin[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ordrCoinService: OrdrCoinService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.ordrCoinService.query().subscribe(
            (res: HttpResponse<OrdrCoin[]>) => {
                this.ordrCoins = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInOrdrCoins();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: OrdrCoin) {
        return item.id;
    }
    registerChangeInOrdrCoins() {
        this.eventSubscriber = this.eventManager.subscribe('ordrCoinListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
