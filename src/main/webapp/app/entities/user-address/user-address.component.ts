import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserAddress } from './user-address.model';
import { UserAddressService } from './user-address.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-user-address',
    templateUrl: './user-address.component.html'
})
export class UserAddressComponent implements OnInit, OnDestroy {
userAddresses: UserAddress[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private userAddressService: UserAddressService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.userAddressService.query().subscribe(
            (res: HttpResponse<UserAddress[]>) => {
                this.userAddresses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUserAddresses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UserAddress) {
        return item.id;
    }
    registerChangeInUserAddresses() {
        this.eventSubscriber = this.eventManager.subscribe('userAddressListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
