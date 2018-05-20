/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { YdtTestModule } from '../../../test.module';
import { CoinDetailComponent } from '../../../../../../main/webapp/app/entities/coin/coin-detail.component';
import { CoinService } from '../../../../../../main/webapp/app/entities/coin/coin.service';
import { Coin } from '../../../../../../main/webapp/app/entities/coin/coin.model';

describe('Component Tests', () => {

    describe('Coin Management Detail Component', () => {
        let comp: CoinDetailComponent;
        let fixture: ComponentFixture<CoinDetailComponent>;
        let service: CoinService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [YdtTestModule],
                declarations: [CoinDetailComponent],
                providers: [
                    CoinService
                ]
            })
            .overrideTemplate(CoinDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoinDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoinService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Coin(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.coin).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
