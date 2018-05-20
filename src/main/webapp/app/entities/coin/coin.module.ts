import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YdtSharedModule } from '../../shared';
import {
    CoinService,
    CoinPopupService,
    CoinComponent,
    CoinDetailComponent,
    CoinDialogComponent,
    CoinPopupComponent,
    CoinDeletePopupComponent,
    CoinDeleteDialogComponent,
    coinRoute,
    coinPopupRoute,
    CoinResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...coinRoute,
    ...coinPopupRoute,
];

@NgModule({
    imports: [
        YdtSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CoinComponent,
        CoinDetailComponent,
        CoinDialogComponent,
        CoinDeleteDialogComponent,
        CoinPopupComponent,
        CoinDeletePopupComponent,
    ],
    entryComponents: [
        CoinComponent,
        CoinDialogComponent,
        CoinPopupComponent,
        CoinDeleteDialogComponent,
        CoinDeletePopupComponent,
    ],
    providers: [
        CoinService,
        CoinPopupService,
        CoinResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YdtCoinModule {}
