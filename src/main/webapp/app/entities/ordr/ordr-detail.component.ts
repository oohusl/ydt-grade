import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Ordr } from './ordr.model';
import { OrdrService } from './ordr.service';

@Component({
    selector: 'jhi-ordr-detail',
    templateUrl: './ordr-detail.component.html'
})
export class OrdrDetailComponent implements OnInit, OnDestroy {

    ordr: Ordr;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ordrService: OrdrService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrdrs();
    }

    load(id) {
        this.ordrService.find(id)
            .subscribe((ordrResponse: HttpResponse<Ordr>) => {
                this.ordr = ordrResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrdrs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ordrListModification',
            (response) => this.load(this.ordr.id)
        );
    }
}
