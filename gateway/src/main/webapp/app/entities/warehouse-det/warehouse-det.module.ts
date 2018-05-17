import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    WarehouseDetService,
    WarehouseDetPopupService,
    WarehouseDetComponent,
    WarehouseDetDetailComponent,
    WarehouseDetDialogComponent,
    WarehouseDetPopupComponent,
    WarehouseDetDeletePopupComponent,
    WarehouseDetDeleteDialogComponent,
    warehouseDetRoute,
    warehouseDetPopupRoute,
    WarehouseDetResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...warehouseDetRoute,
    ...warehouseDetPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        WarehouseDetComponent,
        WarehouseDetDetailComponent,
        WarehouseDetDialogComponent,
        WarehouseDetDeleteDialogComponent,
        WarehouseDetPopupComponent,
        WarehouseDetDeletePopupComponent,
    ],
    entryComponents: [
        WarehouseDetComponent,
        WarehouseDetDialogComponent,
        WarehouseDetPopupComponent,
        WarehouseDetDeleteDialogComponent,
        WarehouseDetDeletePopupComponent,
    ],
    providers: [
        WarehouseDetService,
        WarehouseDetPopupService,
        WarehouseDetResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayWarehouseDetModule {}
