import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SalesDetComponent } from './sales-det.component';
import { SalesDetDetailComponent } from './sales-det-detail.component';
import { SalesDetPopupComponent } from './sales-det-dialog.component';
import { SalesDetDeletePopupComponent } from './sales-det-delete-dialog.component';

@Injectable()
export class SalesDetResolvePagingParams implements Resolve<any> {

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

export const salesDetRoute: Routes = [
    {
        path: 'sales-det',
        component: SalesDetComponent,
        resolve: {
            'pagingParams': SalesDetResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalesDets'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sales-det/:id',
        component: SalesDetDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalesDets'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const salesDetPopupRoute: Routes = [
    {
        path: 'sales-det-new',
        component: SalesDetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalesDets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sales-det/:id/edit',
        component: SalesDetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalesDets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sales-det/:id/delete',
        component: SalesDetDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalesDets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
