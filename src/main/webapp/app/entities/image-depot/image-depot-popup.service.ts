import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { ImageDepot } from './image-depot.model';
import { ImageDepotService } from './image-depot.service';

@Injectable()
export class ImageDepotPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private imageDepotService: ImageDepotService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.imageDepotService.find(id)
                    .subscribe((imageDepotResponse: HttpResponse<ImageDepot>) => {
                        const imageDepot: ImageDepot = imageDepotResponse.body;
                        imageDepot.createDate = this.datePipe
                            .transform(imageDepot.createDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.imageDepotModalRef(component, imageDepot);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.imageDepotModalRef(component, new ImageDepot());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    imageDepotModalRef(component: Component, imageDepot: ImageDepot): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.imageDepot = imageDepot;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
