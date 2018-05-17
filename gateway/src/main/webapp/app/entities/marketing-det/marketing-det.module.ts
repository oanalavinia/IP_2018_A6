import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    MarketingDetService,
    MarketingDetPopupService,
    MarketingDetComponent,
    MarketingDetDetailComponent,
    MarketingDetDialogComponent,
    MarketingDetPopupComponent,
    MarketingDetDeletePopupComponent,
    MarketingDetDeleteDialogComponent,
    marketingDetRoute,
    marketingDetPopupRoute,
    MarketingDetResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...marketingDetRoute,
    ...marketingDetPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MarketingDetComponent,
        MarketingDetDetailComponent,
        MarketingDetDialogComponent,
        MarketingDetDeleteDialogComponent,
        MarketingDetPopupComponent,
        MarketingDetDeletePopupComponent,
    ],
    entryComponents: [
        MarketingDetComponent,
        MarketingDetDialogComponent,
        MarketingDetPopupComponent,
        MarketingDetDeleteDialogComponent,
        MarketingDetDeletePopupComponent,
    ],
    providers: [
        MarketingDetService,
        MarketingDetPopupService,
        MarketingDetResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayMarketingDetModule {}
