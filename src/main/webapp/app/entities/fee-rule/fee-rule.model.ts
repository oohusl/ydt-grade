import { BaseEntity } from './../../shared';

export class FeeRule implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public minValue?: number,
        public maxValue?: number,
        public rate?: number,
        public fixed?: number,
    ) {
    }
}
