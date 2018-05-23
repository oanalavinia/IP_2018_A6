import { BaseEntity } from './../../shared';

export class SalesDet implements BaseEntity {
    constructor(
        public id?: number,
        public price?: number,
        public productId?: number,
    ) {
    }
}
