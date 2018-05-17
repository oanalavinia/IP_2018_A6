import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    SalesDetService,
    SalesDetPopupService,
    SalesDetComponent,
    SalesDetDetailComponent,
    SalesDetDialogComponent,
    SalesDetPopupComponent,
    SalesDetDeletePopupComponent,
    SalesDetDeleteDialogComponent,
    salesDetRoute,
    salesDetPopupRoute,
    SalesDetResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...salesDetRoute,
    ...salesDetPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SalesDetComponent,
        SalesDetDetailComponent,
        SalesDetDialogComponent,
        SalesDetDeleteDialogComponent,
        SalesDetPopupComponent,
        SalesDetDeletePopupComponent,
    ],
    entryComponents: [
        SalesDetComponent,
        SalesDetDialogComponent,
        SalesDetPopupComponent,
        SalesDetDeleteDialogComponent,
        SalesDetDeletePopupComponent,
    ],
    providers: [
        SalesDetService,
        SalesDetPopupService,
        SalesDetResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewaySalesDetModule {}
