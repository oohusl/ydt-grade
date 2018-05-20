import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { ImageDepot } from './image-depot.model';
import { ImageDepotService } from './image-depot.service';

@Component({
    selector: 'jhi-image-depot-detail',
    templateUrl: './image-depot-detail.component.html'
})
export class ImageDepotDetailComponent implements OnInit, OnDestroy {

    imageDepot: ImageDepot;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private imageDepotService: ImageDepotService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInImageDepots();
    }

    load(id) {
        this.imageDepotService.find(id)
            .subscribe((imageDepotResponse: HttpResponse<ImageDepot>) => {
                this.imageDepot = imageDepotResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInImageDepots() {
        this.eventSubscriber = this.eventManager.subscribe(
            'imageDepotListModification',
            (response) => this.load(this.imageDepot.id)
        );
    }
}
