import { BaseEntity } from './../../shared';

export const enum State {
    'YES',
    'NO'
}

export class OrdrCoin implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public quantity?: number,
        public packageFlag?: State,
        public declaredValue?: number,
        public ordr?: BaseEntity,
    ) {
    }
}
