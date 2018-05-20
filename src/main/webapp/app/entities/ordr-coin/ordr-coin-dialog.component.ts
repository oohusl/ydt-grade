import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OrdrCoin } from './ordr-coin.model';
import { OrdrCoinPopupService } from './ordr-coin-popup.service';
import { OrdrCoinService } from './ordr-coin.service';
import { Ordr, OrdrService } from '../ordr';

@Component({
    selector: 'jhi-ordr-coin-dialog',
    templateUrl: './ordr-coin-dialog.component.html'
})
export class OrdrCoinDialogComponent implements OnInit {

    ordrCoin: OrdrCoin;
    isSaving: boolean;

    ordrs: Ordr[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ordrCoinService: OrdrCoinService,
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
        if (this.ordrCoin.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ordrCoinService.update(this.ordrCoin));
        } else {
            this.subscribeToSaveResponse(
                this.ordrCoinService.create(this.ordrCoin));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<OrdrCoin>>) {
        result.subscribe((res: HttpResponse<OrdrCoin>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: OrdrCoin) {
        this.eventManager.broadcast({ name: 'ordrCoinListModification', content: 'OK'});
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
    selector: 'jhi-ordr-coin-popup',
    template: ''
})
export class OrdrCoinPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ordrCoinPopupService: OrdrCoinPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ordrCoinPopupService
                    .open(OrdrCoinDialogComponent as Component, params['id']);
            } else {
                this.ordrCoinPopupService
                    .open(OrdrCoinDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
