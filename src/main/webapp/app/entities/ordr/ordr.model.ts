import { BaseEntity, User } from './../../shared';

export const enum OrderState {
    'NEW',
    'RECEIVED',
    'TO_PAY',
    'TO_SEND',
    'SENT',
    'FINISHED'
}

export class Ordr implements BaseEntity {
    constructor(
        public id?: number,
        public receiver?: string,
        public receiverTel?: string,
        public receiverAddr?: string,
        public deliveryType?: number,
        public insuredPrice?: number,
        public totalQuantity?: number,
        public handingFee?: number,
        public createDate?: any,
        public receiveDate?: any,
        public receiveNo?: string,
        public backDate?: any,
        public backNo?: string,
        public state?: OrderState,
        public ordrCoins?: BaseEntity[],
        public coins?: BaseEntity[],
        public user?: User,
    ) {
    }
}
