import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ShippingDetComponent } from './shipping-det.component';
import { ShippingDetDetailComponent } from './shipping-det-detail.component';
import { ShippingDetPopupComponent } from './shipping-det-dialog.component';
import { ShippingDetDeletePopupComponent } from './shipping-det-delete-dialog.component';

@Injectable()
export class ShippingDetResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const shippingDetRoute: Routes = [
    {
        path: 'shipping-det',
        component: ShippingDetComponent,
        resolve: {
            'pagingParams': ShippingDetResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ShippingDets'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'shipping-det/:id',
        component: ShippingDetDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ShippingDets'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const shippingDetPopupRoute: Routes = [
    {
        path: 'shipping-det-new',
        component: ShippingDetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ShippingDets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shipping-det/:id/edit',
        component: ShippingDetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ShippingDets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shipping-det/:id/delete',
        component: ShippingDetDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ShippingDets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
