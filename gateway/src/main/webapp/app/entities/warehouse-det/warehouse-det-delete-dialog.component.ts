import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WarehouseDet } from './warehouse-det.model';
import { WarehouseDetPopupService } from './warehouse-det-popup.service';
import { WarehouseDetService } from './warehouse-det.service';

@Component({
    selector: 'jhi-warehouse-det-delete-dialog',
    templateUrl: './warehouse-det-delete-dialog.component.html'
})
export class WarehouseDetDeleteDialogComponent {

    warehouseDet: WarehouseDet;

    constructor(
        private warehouseDetService: WarehouseDetService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.warehouseDetService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'warehouseDetListModification',
                content: 'Deleted an warehouseDet'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-warehouse-det-delete-popup',
    template: ''
})
export class WarehouseDetDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private warehouseDetPopupService: WarehouseDetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.warehouseDetPopupService
                .open(WarehouseDetDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
