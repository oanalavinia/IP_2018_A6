import { BaseEntity } from './../../shared';

export class Sales implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public price?: number,
    ) {
    }
}
