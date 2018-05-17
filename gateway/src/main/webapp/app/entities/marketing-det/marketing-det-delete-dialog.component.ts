import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MarketingDet } from './marketing-det.model';
import { MarketingDetPopupService } from './marketing-det-popup.service';
import { MarketingDetService } from './marketing-det.service';

@Component({
    selector: 'jhi-marketing-det-delete-dialog',
    templateUrl: './marketing-det-delete-dialog.component.html'
})
export class MarketingDetDeleteDialogComponent {

    marketingDet: MarketingDet;

    constructor(
        private marketingDetService: MarketingDetService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marketingDetService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'marketingDetListModification',
                content: 'Deleted an marketingDet'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-marketing-det-delete-popup',
    template: ''
})
export class MarketingDetDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketingDetPopupService: MarketingDetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.marketingDetPopupService
                .open(MarketingDetDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
