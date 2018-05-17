import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { MarketingDet } from './marketing-det.model';
import { MarketingDetService } from './marketing-det.service';

@Injectable()
export class MarketingDetPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private marketingDetService: MarketingDetService

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
                this.marketingDetService.find(id)
                    .subscribe((marketingDetResponse: HttpResponse<MarketingDet>) => {
                        const marketingDet: MarketingDet = marketingDetResponse.body;
                        this.ngbModalRef = this.marketingDetModalRef(component, marketingDet);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.marketingDetModalRef(component, new MarketingDet());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    marketingDetModalRef(component: Component, marketingDet: MarketingDet): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.marketingDet = marketingDet;
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
