import { BaseEntity } from './../../shared';

export class WarehouseDet implements BaseEntity {
    constructor(
        public id?: number,
        public productCODE?: string,
        public stock?: number,
    ) {
    }
}
