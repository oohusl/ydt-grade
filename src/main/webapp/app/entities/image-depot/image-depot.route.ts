import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ImageDepotComponent } from './image-depot.component';
import { ImageDepotDetailComponent } from './image-depot-detail.component';
import { ImageDepotPopupComponent } from './image-depot-dialog.component';
import { ImageDepotDeletePopupComponent } from './image-depot-delete-dialog.component';

export const imageDepotRoute: Routes = [
    {
        path: 'image-depot',
        component: ImageDepotComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.imageDepot.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'image-depot/:id',
        component: ImageDepotDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.imageDepot.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const imageDepotPopupRoute: Routes = [
    {
        path: 'image-depot-new',
        component: ImageDepotPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.imageDepot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'image-depot/:id/edit',
        component: ImageDepotPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.imageDepot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'image-depot/:id/delete',
        component: ImageDepotDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.imageDepot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
