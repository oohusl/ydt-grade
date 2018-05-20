import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Coin } from './coin.model';
import { CoinPopupService } from './coin-popup.service';
import { CoinService } from './coin.service';

@Component({
    selector: 'jhi-coin-delete-dialog',
    templateUrl: './coin-delete-dialog.component.html'
})
export class CoinDeleteDialogComponent {

    coin: Coin;

    constructor(
        private coinService: CoinService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.coinService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'coinListModification',
                content: 'Deleted an coin'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-coin-delete-popup',
    template: ''
})
export class CoinDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private coinPopupService: CoinPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.coinPopupService
                .open(CoinDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
