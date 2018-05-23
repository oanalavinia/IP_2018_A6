import { BaseEntity } from './../../shared';

export class ShippingDet implements BaseEntity {
    constructor(
        public id?: number,
        public shipCost?: number,
        public productId?: number,
    ) {
    }
}
