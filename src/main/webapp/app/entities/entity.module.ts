import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { YdtOrdrModule } from './ordr/ordr.module';
import { YdtOrdrCoinModule } from './ordr-coin/ordr-coin.module';
import { YdtCoinModule } from './coin/coin.module';
import { YdtUserAddressModule } from './user-address/user-address.module';
import { YdtFeeRuleModule } from './fee-rule/fee-rule.module';
import { YdtImageDepotModule } from './image-depot/image-depot.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        YdtOrdrModule,
        YdtOrdrCoinModule,
        YdtCoinModule,
        YdtUserAddressModule,
        YdtFeeRuleModule,
        YdtImageDepotModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YdtEntityModule {}
