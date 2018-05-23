import { BaseEntity } from './../../shared';

export class WarehouseDet implements BaseEntity {
    constructor(
        public id?: number,
        public stock?: number,
        public productId?: number,
    ) {
    }
}
