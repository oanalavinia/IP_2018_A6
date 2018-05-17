import { BaseEntity } from './../../shared';

export class SalesDet implements BaseEntity {
    constructor(
        public id?: number,
        public productCODE?: string,
        public price?: number,
    ) {
    }
}
