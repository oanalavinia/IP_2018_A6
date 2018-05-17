import { BaseEntity } from './../../shared';

export class ShippingDet implements BaseEntity {
    constructor(
        public id?: number,
        public productCODE?: string,
        public shipCost?: number,
    ) {
    }
}
