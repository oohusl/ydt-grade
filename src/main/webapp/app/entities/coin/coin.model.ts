import { BaseEntity } from './../../shared';

export const enum Fact {
    'OK',
    'NOT',
    'UNKNOWN'
}

export const enum State {
    'YES',
    'NO'
}

export const enum OrderState {
    'NEW',
    'RECEIVED',
    'TO_PAY',
    'TO_SEND',
    'SENT',
    'FINISHED'
}

export class Coin implements BaseEntity {
    constructor(
        public id?: number,
        public certNo?: string,
        public typeNo?: string,
        public year?: string,
        public denom?: number,
        public country?: string,
        public name?: string,
        public packageType?: string,
        public gradingResult?: Fact,
        public blockChainFlag?: State,
        public declaredValue?: number,
        public handingFee?: number,
        public packageFlag?: State,
        public state?: OrderState,
        public frontImage?: string,
        public backImage?: string,
        public ordr?: BaseEntity,
    ) {
    }
}
