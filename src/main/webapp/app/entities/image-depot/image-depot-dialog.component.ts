import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { ImageDepot } from './image-depot.model';
import { ImageDepotPopupService } from './image-depot-popup.service';
import { ImageDepotService } from './image-depot.service';

@Component({
    selector: 'jhi-image-depot-dialog',
    templateUrl: './image-depot-dialog.component.html'
})
export class ImageDepotDialogComponent implements OnInit {

    imageDepot: ImageDepot;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private imageDepotService: ImageDepotService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.imageDepot, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.imageDepot.id !== undefined) {
            this.subscribeToSaveResponse(
                this.imageDepotService.update(this.imageDepot));
        } else {
            this.subscribeToSaveResponse(
                this.imageDepotService.create(this.imageDepot));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ImageDepot>>) {
        result.subscribe((res: HttpResponse<ImageDepot>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ImageDepot) {
        this.eventManager.broadcast({ name: 'imageDepotListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-image-depot-popup',
    template: ''
})
export class ImageDepotPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private imageDepotPopupService: ImageDepotPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.imageDepotPopupService
                    .open(ImageDepotDialogComponent as Component, params['id']);
            } else {
                this.imageDepotPopupService
                    .open(ImageDepotDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
