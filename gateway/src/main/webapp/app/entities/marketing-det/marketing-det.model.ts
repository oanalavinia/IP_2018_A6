import { BaseEntity } from './../../shared';

export class MarketingDet implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
    ) {
    }
}
