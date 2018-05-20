/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YdtTestModule } from '../../../test.module';
import { CoinComponent } from '../../../../../../main/webapp/app/entities/coin/coin.component';
import { CoinService } from '../../../../../../main/webapp/app/entities/coin/coin.service';
import { Coin } from '../../../../../../main/webapp/app/entities/coin/coin.model';

describe('Component Tests', () => {

    describe('Coin Management Component', () => {
        let comp: CoinComponent;
        let fixture: ComponentFixture<CoinComponent>;
        let service: CoinService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [YdtTestModule],
                declarations: [CoinComponent],
                providers: [
                    CoinService
                ]
            })
            .overrideTemplate(CoinComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoinComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoinService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Coin(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.coins[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
