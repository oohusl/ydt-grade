import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { OrdrCoin } from './ordr-coin.model';
import { OrdrCoinService } from './ordr-coin.service';

@Injectable()
export class OrdrCoinPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private ordrCoinService: OrdrCoinService

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
                this.ordrCoinService.find(id)
                    .subscribe((ordrCoinResponse: HttpResponse<OrdrCoin>) => {
                        const ordrCoin: OrdrCoin = ordrCoinResponse.body;
                        this.ngbModalRef = this.ordrCoinModalRef(component, ordrCoin);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.ordrCoinModalRef(component, new OrdrCoin());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    ordrCoinModalRef(component: Component, ordrCoin: OrdrCoin): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ordrCoin = ordrCoin;
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
