import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MarketingDet } from './marketing-det.model';
import { MarketingDetPopupService } from './marketing-det-popup.service';
import { MarketingDetService } from './marketing-det.service';

@Component({
    selector: 'jhi-marketing-det-dialog',
    templateUrl: './marketing-det-dialog.component.html'
})
export class MarketingDetDialogComponent implements OnInit {

    marketingDet: MarketingDet;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private marketingDetService: MarketingDetService,
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
        if (this.marketingDet.id !== undefined) {
            this.subscribeToSaveResponse(
                this.marketingDetService.update(this.marketingDet));
        } else {
            this.subscribeToSaveResponse(
                this.marketingDetService.create(this.marketingDet));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<MarketingDet>>) {
        result.subscribe((res: HttpResponse<MarketingDet>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: MarketingDet) {
        this.eventManager.broadcast({ name: 'marketingDetListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-marketing-det-popup',
    template: ''
})
export class MarketingDetPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketingDetPopupService: MarketingDetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.marketingDetPopupService
                    .open(MarketingDetDialogComponent as Component, params['id']);
            } else {
                this.marketingDetPopupService
                    .open(MarketingDetDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
