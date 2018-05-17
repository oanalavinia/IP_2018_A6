import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MarketingDetComponent } from './marketing-det.component';
import { MarketingDetDetailComponent } from './marketing-det-detail.component';
import { MarketingDetPopupComponent } from './marketing-det-dialog.component';
import { MarketingDetDeletePopupComponent } from './marketing-det-delete-dialog.component';

@Injectable()
export class MarketingDetResolvePagingParams implements Resolve<any> {

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

export const marketingDetRoute: Routes = [
    {
        path: 'marketing-det',
        component: MarketingDetComponent,
        resolve: {
            'pagingParams': MarketingDetResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MarketingDets'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'marketing-det/:id',
        component: MarketingDetDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MarketingDets'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketingDetPopupRoute: Routes = [
    {
        path: 'marketing-det-new',
        component: MarketingDetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MarketingDets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'marketing-det/:id/edit',
        component: MarketingDetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MarketingDets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'marketing-det/:id/delete',
        component: MarketingDetDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MarketingDets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
