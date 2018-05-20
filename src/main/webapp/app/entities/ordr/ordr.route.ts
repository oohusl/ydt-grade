import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { OrdrComponent } from './ordr.component';
import { OrdrDetailComponent } from './ordr-detail.component';
import { OrdrPopupComponent } from './ordr-dialog.component';
import { OrdrDeletePopupComponent } from './ordr-delete-dialog.component';

@Injectable()
export class OrdrResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const ordrRoute: Routes = [
    {
        path: 'ordr',
        component: OrdrComponent,
        resolve: {
            'pagingParams': OrdrResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.ordr.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ordr/:id',
        component: OrdrDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.ordr.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ordrPopupRoute: Routes = [
    {
        path: 'ordr-new',
        component: OrdrPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.ordr.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ordr/:id/edit',
        component: OrdrPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.ordr.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ordr/:id/delete',
        component: OrdrDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.ordr.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
