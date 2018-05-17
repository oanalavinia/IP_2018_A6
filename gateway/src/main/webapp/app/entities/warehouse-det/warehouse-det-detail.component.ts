import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { WarehouseDet } from './warehouse-det.model';
import { WarehouseDetService } from './warehouse-det.service';

@Component({
    selector: 'jhi-warehouse-det-detail',
    templateUrl: './warehouse-det-detail.component.html'
})
export class WarehouseDetDetailComponent implements OnInit, OnDestroy {

    warehouseDet: WarehouseDet;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private warehouseDetService: WarehouseDetService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWarehouseDets();
    }

    load(id) {
        this.warehouseDetService.find(id)
            .subscribe((warehouseDetResponse: HttpResponse<WarehouseDet>) => {
                this.warehouseDet = warehouseDetResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWarehouseDets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'warehouseDetListModification',
            (response) => this.load(this.warehouseDet.id)
        );
    }
}
