import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YdtSharedModule } from '../../shared';
import {
    ImageDepotService,
    ImageDepotPopupService,
    ImageDepotComponent,
    ImageDepotDetailComponent,
    ImageDepotDialogComponent,
    ImageDepotPopupComponent,
    ImageDepotDeletePopupComponent,
    ImageDepotDeleteDialogComponent,
    imageDepotRoute,
    imageDepotPopupRoute,
} from './';

const ENTITY_STATES = [
    ...imageDepotRoute,
    ...imageDepotPopupRoute,
];

@NgModule({
    imports: [
        YdtSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ImageDepotComponent,
        ImageDepotDetailComponent,
        ImageDepotDialogComponent,
        ImageDepotDeleteDialogComponent,
        ImageDepotPopupComponent,
        ImageDepotDeletePopupComponent,
    ],
    entryComponents: [
        ImageDepotComponent,
        ImageDepotDialogComponent,
        ImageDepotPopupComponent,
        ImageDepotDeleteDialogComponent,
        ImageDepotDeletePopupComponent,
    ],
    providers: [
        ImageDepotService,
        ImageDepotPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YdtImageDepotModule {}
