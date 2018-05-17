import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ShippingDet } from './shipping-det.model';
import { ShippingDetPopupService } from './shipping-det-popup.service';
import { ShippingDetService } from './shipping-det.service';

@Component({
    selector: 'jhi-shipping-det-delete-dialog',
    templateUrl: './shipping-det-delete-dialog.component.html'
})
export class ShippingDetDeleteDialogComponent {

    shippingDet: ShippingDet;

    constructor(
        private shippingDetService: ShippingDetService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shippingDetService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'shippingDetListModification',
                content: 'Deleted an shippingDet'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-shipping-det-delete-popup',
    template: ''
})
export class ShippingDetDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shippingDetPopupService: ShippingDetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.shippingDetPopupService
                .open(ShippingDetDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
