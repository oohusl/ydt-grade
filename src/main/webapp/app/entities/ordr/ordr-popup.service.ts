import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Ordr } from './ordr.model';
import { OrdrService } from './ordr.service';

@Injectable()
export class OrdrPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private ordrService: OrdrService

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
                this.ordrService.find(id)
                    .subscribe((ordrResponse: HttpResponse<Ordr>) => {
                        const ordr: Ordr = ordrResponse.body;
                        ordr.createDate = this.datePipe
                            .transform(ordr.createDate, 'yyyy-MM-ddTHH:mm:ss');
                        ordr.receiveDate = this.datePipe
                            .transform(ordr.receiveDate, 'yyyy-MM-ddTHH:mm:ss');
                        ordr.backDate = this.datePipe
                            .transform(ordr.backDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.ordrModalRef(component, ordr);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.ordrModalRef(component, new Ordr());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    ordrModalRef(component: Component, ordr: Ordr): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ordr = ordr;
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
