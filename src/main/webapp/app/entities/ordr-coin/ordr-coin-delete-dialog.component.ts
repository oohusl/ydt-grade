import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OrdrCoin } from './ordr-coin.model';
import { OrdrCoinPopupService } from './ordr-coin-popup.service';
import { OrdrCoinService } from './ordr-coin.service';

@Component({
    selector: 'jhi-ordr-coin-delete-dialog',
    templateUrl: './ordr-coin-delete-dialog.component.html'
})
export class OrdrCoinDeleteDialogComponent {

    ordrCoin: OrdrCoin;

    constructor(
        private ordrCoinService: OrdrCoinService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ordrCoinService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ordrCoinListModification',
                content: 'Deleted an ordrCoin'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ordr-coin-delete-popup',
    template: ''
})
export class OrdrCoinDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ordrCoinPopupService: OrdrCoinPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ordrCoinPopupService
                .open(OrdrCoinDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
