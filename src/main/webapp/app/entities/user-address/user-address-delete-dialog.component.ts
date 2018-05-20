import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserAddress } from './user-address.model';
import { UserAddressPopupService } from './user-address-popup.service';
import { UserAddressService } from './user-address.service';

@Component({
    selector: 'jhi-user-address-delete-dialog',
    templateUrl: './user-address-delete-dialog.component.html'
})
export class UserAddressDeleteDialogComponent {

    userAddress: UserAddress;

    constructor(
        private userAddressService: UserAddressService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userAddressService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userAddressListModification',
                content: 'Deleted an userAddress'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-address-delete-popup',
    template: ''
})
export class UserAddressDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userAddressPopupService: UserAddressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userAddressPopupService
                .open(UserAddressDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
