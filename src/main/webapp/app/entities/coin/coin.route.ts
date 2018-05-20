import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CoinComponent } from './coin.component';
import { CoinDetailComponent } from './coin-detail.component';
import { CoinPopupComponent } from './coin-dialog.component';
import { CoinDeletePopupComponent } from './coin-delete-dialog.component';

@Injectable()
export class CoinResolvePagingParams implements Resolve<any> {

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

export const coinRoute: Routes = [
    {
        path: 'coin',
        component: CoinComponent,
        resolve: {
            'pagingParams': CoinResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.coin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'coin/:id',
        component: CoinDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.coin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const coinPopupRoute: Routes = [
    {
        path: 'coin-new',
        component: CoinPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.coin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'coin/:id/edit',
        component: CoinPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.coin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'coin/:id/delete',
        component: CoinDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.coin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
