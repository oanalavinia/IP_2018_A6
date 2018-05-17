import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    ShippingDetService,
    ShippingDetPopupService,
    ShippingDetComponent,
    ShippingDetDetailComponent,
    ShippingDetDialogComponent,
    ShippingDetPopupComponent,
    ShippingDetDeletePopupComponent,
    ShippingDetDeleteDialogComponent,
    shippingDetRoute,
    shippingDetPopupRoute,
    ShippingDetResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...shippingDetRoute,
    ...shippingDetPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ShippingDetComponent,
        ShippingDetDetailComponent,
        ShippingDetDialogComponent,
        ShippingDetDeleteDialogComponent,
        ShippingDetPopupComponent,
        ShippingDetDeletePopupComponent,
    ],
    entryComponents: [
        ShippingDetComponent,
        ShippingDetDialogComponent,
        ShippingDetPopupComponent,
        ShippingDetDeleteDialogComponent,
        ShippingDetDeletePopupComponent,
    ],
    providers: [
        ShippingDetService,
        ShippingDetPopupService,
        ShippingDetResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayShippingDetModule {}
