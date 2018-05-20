import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YdtSharedModule } from '../../shared';
import {
    FeeRuleService,
    FeeRulePopupService,
    FeeRuleComponent,
    FeeRuleDetailComponent,
    FeeRuleDialogComponent,
    FeeRulePopupComponent,
    FeeRuleDeletePopupComponent,
    FeeRuleDeleteDialogComponent,
    feeRuleRoute,
    feeRulePopupRoute,
} from './';

const ENTITY_STATES = [
    ...feeRuleRoute,
    ...feeRulePopupRoute,
];

@NgModule({
    imports: [
        YdtSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FeeRuleComponent,
        FeeRuleDetailComponent,
        FeeRuleDialogComponent,
        FeeRuleDeleteDialogComponent,
        FeeRulePopupComponent,
        FeeRuleDeletePopupComponent,
    ],
    entryComponents: [
        FeeRuleComponent,
        FeeRuleDialogComponent,
        FeeRulePopupComponent,
        FeeRuleDeleteDialogComponent,
        FeeRuleDeletePopupComponent,
    ],
    providers: [
        FeeRuleService,
        FeeRulePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YdtFeeRuleModule {}
