import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserAddress } from './user-address.model';
import { UserAddressPopupService } from './user-address-popup.service';
import { UserAddressService } from './user-address.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-user-address-dialog',
    templateUrl: './user-address-dialog.component.html'
})
export class UserAddressDialogComponent implements OnInit {

    userAddress: UserAddress;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private userAddressService: UserAddressService,
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
        if (this.userAddress.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userAddressService.update(this.userAddress));
        } else {
            this.subscribeToSaveResponse(
                this.userAddressService.create(this.userAddress));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<UserAddress>>) {
        result.subscribe((res: HttpResponse<UserAddress>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: UserAddress) {
        this.eventManager.broadcast({ name: 'userAddressListModification', content: 'OK'});
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
    selector: 'jhi-user-address-popup',
    template: ''
})
export class UserAddressPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userAddressPopupService: UserAddressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userAddressPopupService
                    .open(UserAddressDialogComponent as Component, params['id']);
            } else {
                this.userAddressPopupService
                    .open(UserAddressDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
