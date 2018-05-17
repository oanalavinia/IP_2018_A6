import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SalesDet } from './sales-det.model';
import { SalesDetPopupService } from './sales-det-popup.service';
import { SalesDetService } from './sales-det.service';

@Component({
    selector: 'jhi-sales-det-dialog',
    templateUrl: './sales-det-dialog.component.html'
})
export class SalesDetDialogComponent implements OnInit {

    salesDet: SalesDet;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private salesDetService: SalesDetService,
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
        if (this.salesDet.id !== undefined) {
            this.subscribeToSaveResponse(
                this.salesDetService.update(this.salesDet));
        } else {
            this.subscribeToSaveResponse(
                this.salesDetService.create(this.salesDet));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SalesDet>>) {
        result.subscribe((res: HttpResponse<SalesDet>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SalesDet) {
        this.eventManager.broadcast({ name: 'salesDetListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-sales-det-popup',
    template: ''
})
export class SalesDetPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private salesDetPopupService: SalesDetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.salesDetPopupService
                    .open(SalesDetDialogComponent as Component, params['id']);
            } else {
                this.salesDetPopupService
                    .open(SalesDetDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
