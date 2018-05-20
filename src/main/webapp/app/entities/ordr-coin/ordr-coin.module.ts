import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YdtSharedModule } from '../../shared';
import {
    OrdrCoinService,
    OrdrCoinPopupService,
    OrdrCoinComponent,
    OrdrCoinDetailComponent,
    OrdrCoinDialogComponent,
    OrdrCoinPopupComponent,
    OrdrCoinDeletePopupComponent,
    OrdrCoinDeleteDialogComponent,
    ordrCoinRoute,
    ordrCoinPopupRoute,
} from './';

const ENTITY_STATES = [
    ...ordrCoinRoute,
    ...ordrCoinPopupRoute,
];

@NgModule({
    imports: [
        YdtSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OrdrCoinComponent,
        OrdrCoinDetailComponent,
        OrdrCoinDialogComponent,
        OrdrCoinDeleteDialogComponent,
        OrdrCoinPopupComponent,
        OrdrCoinDeletePopupComponent,
    ],
    entryComponents: [
        OrdrCoinComponent,
        OrdrCoinDialogComponent,
        OrdrCoinPopupComponent,
        OrdrCoinDeleteDialogComponent,
        OrdrCoinDeletePopupComponent,
    ],
    providers: [
        OrdrCoinService,
        OrdrCoinPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YdtOrdrCoinModule {}
