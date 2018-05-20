import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YdtSharedModule } from '../../shared';
import { YdtAdminModule } from '../../admin/admin.module';
import {
    OrdrService,
    OrdrPopupService,
    OrdrComponent,
    OrdrDetailComponent,
    OrdrDialogComponent,
    OrdrPopupComponent,
    OrdrDeletePopupComponent,
    OrdrDeleteDialogComponent,
    ordrRoute,
    ordrPopupRoute,
    OrdrResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...ordrRoute,
    ...ordrPopupRoute,
];

@NgModule({
    imports: [
        YdtSharedModule,
        YdtAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OrdrComponent,
        OrdrDetailComponent,
        OrdrDialogComponent,
        OrdrDeleteDialogComponent,
        OrdrPopupComponent,
        OrdrDeletePopupComponent,
    ],
    entryComponents: [
        OrdrComponent,
        OrdrDialogComponent,
        OrdrPopupComponent,
        OrdrDeleteDialogComponent,
        OrdrDeletePopupComponent,
    ],
    providers: [
        OrdrService,
        OrdrPopupService,
        OrdrResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YdtOrdrModule {}
