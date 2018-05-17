import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GatewayMarketingDetModule } from './marketing-det/marketing-det.module';
import { GatewayShippingDetModule } from './shipping-det/shipping-det.module';
import { GatewaySalesDetModule } from './sales-det/sales-det.module';
import { GatewayWarehouseDetModule } from './warehouse-det/warehouse-det.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        GatewayMarketingDetModule,
        GatewayShippingDetModule,
        GatewaySalesDetModule,
        GatewayWarehouseDetModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayEntityModule {}
