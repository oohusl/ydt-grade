import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ordr } from './ordr.model';
import { OrdrPopupService } from './ordr-popup.service';
import { OrdrService } from './ordr.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-ordr-dialog',
    templateUrl: './ordr-dialog.component.html'
})
export class OrdrDialogComponent implements OnInit {

    ordr: Ordr;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ordrService: OrdrService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ordr.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ordrService.update(this.ordr));
        } else {
            this.subscribeToSaveResponse(
                this.ordrService.create(this.ordr));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Ordr>>) {
        result.subscribe((res: HttpResponse<Ordr>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Ordr) {
        this.eventManager.broadcast({ name: 'ordrListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ordr-popup',
    template: ''
})
export class OrdrPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ordrPopupService: OrdrPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ordrPopupService
                    .open(OrdrDialogComponent as Component, params['id']);
            } else {
                this.ordrPopupService
                    .open(OrdrDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
