import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { WarehouseDet } from './warehouse-det.model';
import { WarehouseDetService } from './warehouse-det.service';

@Injectable()
export class WarehouseDetPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private warehouseDetService: WarehouseDetService

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
                this.warehouseDetService.find(id)
                    .subscribe((warehouseDetResponse: HttpResponse<WarehouseDet>) => {
                        const warehouseDet: WarehouseDet = warehouseDetResponse.body;
                        this.ngbModalRef = this.warehouseDetModalRef(component, warehouseDet);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.warehouseDetModalRef(component, new WarehouseDet());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    warehouseDetModalRef(component: Component, warehouseDet: WarehouseDet): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.warehouseDet = warehouseDet;
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
