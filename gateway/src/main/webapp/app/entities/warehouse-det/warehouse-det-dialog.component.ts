import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WarehouseDet } from './warehouse-det.model';
import { WarehouseDetPopupService } from './warehouse-det-popup.service';
import { WarehouseDetService } from './warehouse-det.service';

@Component({
    selector: 'jhi-warehouse-det-dialog',
    templateUrl: './warehouse-det-dialog.component.html'
})
export class WarehouseDetDialogComponent implements OnInit {

    warehouseDet: WarehouseDet;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private warehouseDetService: WarehouseDetService,
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
        if (this.warehouseDet.id !== undefined) {
            this.subscribeToSaveResponse(
                this.warehouseDetService.update(this.warehouseDet));
        } else {
            this.subscribeToSaveResponse(
                this.warehouseDetService.create(this.warehouseDet));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<WarehouseDet>>) {
        result.subscribe((res: HttpResponse<WarehouseDet>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: WarehouseDet) {
        this.eventManager.broadcast({ name: 'warehouseDetListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-warehouse-det-popup',
    template: ''
})
export class WarehouseDetPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private warehouseDetPopupService: WarehouseDetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.warehouseDetPopupService
                    .open(WarehouseDetDialogComponent as Component, params['id']);
            } else {
                this.warehouseDetPopupService
                    .open(WarehouseDetDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
