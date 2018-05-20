import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { FeeRuleComponent } from './fee-rule.component';
import { FeeRuleDetailComponent } from './fee-rule-detail.component';
import { FeeRulePopupComponent } from './fee-rule-dialog.component';
import { FeeRuleDeletePopupComponent } from './fee-rule-delete-dialog.component';

export const feeRuleRoute: Routes = [
    {
        path: 'fee-rule',
        component: FeeRuleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.feeRule.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'fee-rule/:id',
        component: FeeRuleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.feeRule.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const feeRulePopupRoute: Routes = [
    {
        path: 'fee-rule-new',
        component: FeeRulePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.feeRule.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fee-rule/:id/edit',
        component: FeeRulePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.feeRule.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fee-rule/:id/delete',
        component: FeeRuleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ydtApp.feeRule.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
