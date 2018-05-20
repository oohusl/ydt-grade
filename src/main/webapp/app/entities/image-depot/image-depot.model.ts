import { BaseEntity } from './../../shared';

export class ImageDepot implements BaseEntity {
    constructor(
        public id?: number,
        public imageContentType?: string,
        public image?: any,
        public createDate?: any,
    ) {
    }
}
