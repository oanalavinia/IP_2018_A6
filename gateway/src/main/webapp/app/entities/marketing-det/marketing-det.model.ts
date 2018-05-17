import { BaseEntity } from './../../shared';

export class MarketingDet implements BaseEntity {
    constructor(
        public id?: number,
        public productCODE?: string,
        public name?: string,
        public description?: string,
    ) {
    }
}
