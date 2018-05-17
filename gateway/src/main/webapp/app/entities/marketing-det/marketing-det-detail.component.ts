import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { MarketingDet } from './marketing-det.model';
import { MarketingDetService } from './marketing-det.service';

@Component({
    selector: 'jhi-marketing-det-detail',
    templateUrl: './marketing-det-detail.component.html'
})
export class MarketingDetDetailComponent implements OnInit, OnDestroy {

    marketingDet: MarketingDet;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private marketingDetService: MarketingDetService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarketingDets();
    }

    load(id) {
        this.marketingDetService.find(id)
            .subscribe((marketingDetResponse: HttpResponse<MarketingDet>) => {
                this.marketingDet = marketingDetResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMarketingDets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'marketingDetListModification',
            (response) => this.load(this.marketingDet.id)
        );
    }
}
