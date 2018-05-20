import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { UserAddressComponent } from './user-address.component';
import { UserAddressDetailComponent } from './user-address-detail.component';
import { UserAddressPopupComponent } from './user-address-dialog.component';
import { UserAddressDeletePopupComponent } from './user-address-delete-dialog.component';

export const userAddressRoute: Routes = [
    {
        path: 'user-address',
        component: UserAddressComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.userAddress.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-address/:id',
        component: UserAddressDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.userAddress.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userAddressPopupRoute: Routes = [
    {
        path: 'user-address-new',
        component: UserAddressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.userAddress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-address/:id/edit',
        component: UserAddressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.userAddress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-address/:id/delete',
        component: UserAddressDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.userAddress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
