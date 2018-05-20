import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ordr } from './ordr.model';
import { OrdrPopupService } from './ordr-popup.service';
import { OrdrService } from './ordr.service';

@Component({
    selector: 'jhi-ordr-delete-dialog',
    templateUrl: './ordr-delete-dialog.component.html'
})
export class OrdrDeleteDialogComponent {

    ordr: Ordr;

    constructor(
        private ordrService: OrdrService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ordrService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ordrListModification',
                content: 'Deleted an ordr'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ordr-delete-popup',
    template: ''
})
export class OrdrDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ordrPopupService: OrdrPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ordrPopupService
                .open(OrdrDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
