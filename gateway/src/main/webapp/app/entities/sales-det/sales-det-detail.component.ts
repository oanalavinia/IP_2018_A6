import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SalesDet } from './sales-det.model';
import { SalesDetService } from './sales-det.service';

@Component({
    selector: 'jhi-sales-det-detail',
    templateUrl: './sales-det-detail.component.html'
})
export class SalesDetDetailComponent implements OnInit, OnDestroy {

    salesDet: SalesDet;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private salesDetService: SalesDetService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSalesDets();
    }

    load(id) {
        this.salesDetService.find(id)
            .subscribe((salesDetResponse: HttpResponse<SalesDet>) => {
                this.salesDet = salesDetResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSalesDets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'salesDetListModification',
            (response) => this.load(this.salesDet.id)
        );
    }
}
