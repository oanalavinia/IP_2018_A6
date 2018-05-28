import { Component, OnInit } from '@angular/core';
import { Principal } from '../shared';
import { ProductDetService } from './product-det.service';
import { WarehouseDet } from '../entities/warehouse-det';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { JhiAlertService, JhiParseLinks } from 'ng-jhipster';

@Component({
    selector: 'jhi-product-det',
    templateUrl: './product-det.component.html'
})
export class ProductDetComponent implements OnInit {
    account: Account;
    warehouseDets: WarehouseDet[];
    error: any;
    success: any;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(private principal: Principal, private service:ProductDetService, private jhiAlertService: JhiAlertService,        private parseLinks: JhiParseLinks
    ) {}

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        console.log("altceva");
        this.service.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
                (res: HttpResponse<WarehouseDet[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }
    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.warehouseDets = data;
        console.log('ceva');
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
