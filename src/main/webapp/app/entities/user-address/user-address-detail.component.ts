import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { UserAddress } from './user-address.model';
import { UserAddressService } from './user-address.service';

@Component({
    selector: 'jhi-user-address-detail',
    templateUrl: './user-address-detail.component.html'
})
export class UserAddressDetailComponent implements OnInit, OnDestroy {

    userAddress: UserAddress;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userAddressService: UserAddressService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserAddresses();
    }

    load(id) {
        this.userAddressService.find(id)
            .subscribe((userAddressResponse: HttpResponse<UserAddress>) => {
                this.userAddress = userAddressResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserAddresses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userAddressListModification',
            (response) => this.load(this.userAddress.id)
        );
    }
}
