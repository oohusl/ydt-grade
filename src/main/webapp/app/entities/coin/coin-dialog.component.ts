import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Coin } from './coin.model';
import { CoinPopupService } from './coin-popup.service';
import { CoinService } from './coin.service';
import { Ordr, OrdrService } from '../ordr';

@Component({
    selector: 'jhi-coin-dialog',
    templateUrl: './coin-dialog.component.html'
})
export class CoinDialogComponent implements OnInit {

    coin: Coin;
    isSaving: boolean;

    ordrs: Ordr[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private coinService: CoinService,
        private ordrService: OrdrService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.ordrService.query()
            .subscribe((res: HttpResponse<Ordr[]>) => { this.ordrs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.coin.id !== undefined) {
            this.subscribeToSaveResponse(
                this.coinService.update(this.coin));
        } else {
            this.subscribeToSaveResponse(
                this.coinService.create(this.coin));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Coin>>) {
        result.subscribe((res: HttpResponse<Coin>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Coin) {
        this.eventManager.broadcast({ name: 'coinListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackOrdrById(index: number, item: Ordr) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-coin-popup',
    template: ''
})
export class CoinPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private coinPopupService: CoinPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.coinPopupService
                    .open(CoinDialogComponent as Component, params['id']);
            } else {
                this.coinPopupService
                    .open(CoinDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
