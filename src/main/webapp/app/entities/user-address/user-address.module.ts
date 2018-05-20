import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YdtSharedModule } from '../../shared';
import { YdtAdminModule } from '../../admin/admin.module';
import {
    UserAddressService,
    UserAddressPopupService,
    UserAddressComponent,
    UserAddressDetailComponent,
    UserAddressDialogComponent,
    UserAddressPopupComponent,
    UserAddressDeletePopupComponent,
    UserAddressDeleteDialogComponent,
    userAddressRoute,
    userAddressPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userAddressRoute,
    ...userAddressPopupRoute,
];

@NgModule({
    imports: [
        YdtSharedModule,
        YdtAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UserAddressComponent,
        UserAddressDetailComponent,
        UserAddressDialogComponent,
        UserAddressDeleteDialogComponent,
        UserAddressPopupComponent,
        UserAddressDeletePopupComponent,
    ],
    entryComponents: [
        UserAddressComponent,
        UserAddressDialogComponent,
        UserAddressPopupComponent,
        UserAddressDeleteDialogComponent,
        UserAddressDeletePopupComponent,
    ],
    providers: [
        UserAddressService,
        UserAddressPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YdtUserAddressModule {}
