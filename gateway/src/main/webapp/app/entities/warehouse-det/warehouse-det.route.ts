import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { WarehouseDetComponent } from './warehouse-det.component';
import { WarehouseDetDetailComponent } from './warehouse-det-detail.component';
import { WarehouseDetPopupComponent } from './warehouse-det-dialog.component';
import { WarehouseDetDeletePopupComponent } from './warehouse-det-delete-dialog.component';

@Injectable()
export class WarehouseDetResolvePagingParams implements Resolve<any> {

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

export const warehouseDetRoute: Routes = [
    {
        path: 'warehouse-det',
        component: WarehouseDetComponent,
        resolve: {
            'pagingParams': WarehouseDetResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WarehouseDets'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'warehouse-det/:id',
        component: WarehouseDetDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WarehouseDets'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const warehouseDetPopupRoute: Routes = [
    {
        path: 'warehouse-det-new',
        component: WarehouseDetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WarehouseDets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'warehouse-det/:id/edit',
        component: WarehouseDetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WarehouseDets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'warehouse-det/:id/delete',
        component: WarehouseDetDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WarehouseDets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
