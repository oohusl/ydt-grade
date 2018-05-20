import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { OrdrCoinComponent } from './ordr-coin.component';
import { OrdrCoinDetailComponent } from './ordr-coin-detail.component';
import { OrdrCoinPopupComponent } from './ordr-coin-dialog.component';
import { OrdrCoinDeletePopupComponent } from './ordr-coin-delete-dialog.component';

export const ordrCoinRoute: Routes = [
    {
        path: 'ordr-coin',
        component: OrdrCoinComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.ordrCoin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ordr-coin/:id',
        component: OrdrCoinDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.ordrCoin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ordrCoinPopupRoute: Routes = [
    {
        path: 'ordr-coin-new',
        component: OrdrCoinPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.ordrCoin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ordr-coin/:id/edit',
        component: OrdrCoinPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.ordrCoin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ordr-coin/:id/delete',
        component: OrdrCoinDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.ordrCoin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
