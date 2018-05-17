import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SalesDet } from './sales-det.model';
import { SalesDetPopupService } from './sales-det-popup.service';
import { SalesDetService } from './sales-det.service';

@Component({
    selector: 'jhi-sales-det-delete-dialog',
    templateUrl: './sales-det-delete-dialog.component.html'
})
export class SalesDetDeleteDialogComponent {

    salesDet: SalesDet;

    constructor(
        private salesDetService: SalesDetService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.salesDetService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'salesDetListModification',
                content: 'Deleted an salesDet'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sales-det-delete-popup',
    template: ''
})
export class SalesDetDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private salesDetPopupService: SalesDetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.salesDetPopupService
                .open(SalesDetDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
