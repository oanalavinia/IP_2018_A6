import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Sales } from './sales.model';
import { SalesPopupService } from './sales-popup.service';
import { SalesService } from './sales.service';

@Component({
    selector: 'jhi-sales-dialog',
    templateUrl: './sales-dialog.component.html'
})
export class SalesDialogComponent implements OnInit {

    sales: Sales;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private salesService: SalesService,
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
        if (this.sales.id !== undefined) {
            this.subscribeToSaveResponse(
                this.salesService.update(this.sales));
        } else {
            this.subscribeToSaveResponse(
                this.salesService.create(this.sales));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Sales>>) {
        result.subscribe((res: HttpResponse<Sales>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Sales) {
        this.eventManager.broadcast({ name: 'salesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-sales-popup',
    template: ''
})
export class SalesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private salesPopupService: SalesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.salesPopupService
                    .open(SalesDialogComponent as Component, params['id']);
            } else {
                this.salesPopupService
                    .open(SalesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
