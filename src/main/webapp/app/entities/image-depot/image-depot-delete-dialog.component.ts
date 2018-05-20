import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ImageDepot } from './image-depot.model';
import { ImageDepotPopupService } from './image-depot-popup.service';
import { ImageDepotService } from './image-depot.service';

@Component({
    selector: 'jhi-image-depot-delete-dialog',
    templateUrl: './image-depot-delete-dialog.component.html'
})
export class ImageDepotDeleteDialogComponent {

    imageDepot: ImageDepot;

    constructor(
        private imageDepotService: ImageDepotService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.imageDepotService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'imageDepotListModification',
                content: 'Deleted an imageDepot'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-image-depot-delete-popup',
    template: ''
})
export class ImageDepotDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private imageDepotPopupService: ImageDepotPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.imageDepotPopupService
                .open(ImageDepotDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
