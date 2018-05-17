import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ShippingDet } from './shipping-det.model';
import { ShippingDetService } from './shipping-det.service';

@Component({
    selector: 'jhi-shipping-det-detail',
    templateUrl: './shipping-det-detail.component.html'
})
export class ShippingDetDetailComponent implements OnInit, OnDestroy {

    shippingDet: ShippingDet;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private shippingDetService: ShippingDetService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInShippingDets();
    }

    load(id) {
        this.shippingDetService.find(id)
            .subscribe((shippingDetResponse: HttpResponse<ShippingDet>) => {
                this.shippingDet = shippingDetResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInShippingDets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'shippingDetListModification',
            (response) => this.load(this.shippingDet.id)
        );
    }
}
