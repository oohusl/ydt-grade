import { BaseEntity, User } from './../../shared';

export class UserAddress implements BaseEntity {
    constructor(
        public id?: number,
        public receiver?: string,
        public receiverTel?: string,
        public receiverCity?: string,
        public receiverAddr?: string,
        public selected?: boolean,
        public user?: User,
    ) {
        this.selected = false;
    }
}
