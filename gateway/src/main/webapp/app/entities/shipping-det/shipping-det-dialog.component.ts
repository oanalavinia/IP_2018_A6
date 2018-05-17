import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ShippingDet } from './shipping-det.model';
import { ShippingDetPopupService } from './shipping-det-popup.service';
import { ShippingDetService } from './shipping-det.service';

@Component({
    selector: 'jhi-shipping-det-dialog',
    templateUrl: './shipping-det-dialog.component.html'
})
export class ShippingDetDialogComponent implements OnInit {

    shippingDet: ShippingDet;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private shippingDetService: ShippingDetService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.shippingDet.id !== undefined) {
            this.subscribeToSaveResponse(
                this.shippingDetService.update(this.shippingDet));
        } else {
            this.subscribeToSaveResponse(
                this.shippingDetService.create(this.shippingDet));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ShippingDet>>) {
        result.subscribe((res: HttpResponse<ShippingDet>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ShippingDet) {
        this.eventManager.broadcast({ name: 'shippingDetListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-shipping-det-popup',
    template: ''
})
export class ShippingDetPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shippingDetPopupService: ShippingDetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.shippingDetPopupService
                    .open(ShippingDetDialogComponent as Component, params['id']);
            } else {
                this.shippingDetPopupService
                    .open(ShippingDetDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
