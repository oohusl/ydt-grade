/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YdtTestModule } from '../../../test.module';
import { OrdrCoinComponent } from '../../../../../../main/webapp/app/entities/ordr-coin/ordr-coin.component';
import { OrdrCoinService } from '../../../../../../main/webapp/app/entities/ordr-coin/ordr-coin.service';
import { OrdrCoin } from '../../../../../../main/webapp/app/entities/ordr-coin/ordr-coin.model';

describe('Component Tests', () => {

    describe('OrdrCoin Management Component', () => {
        let comp: OrdrCoinComponent;
        let fixture: ComponentFixture<OrdrCoinComponent>;
        let service: OrdrCoinService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [YdtTestModule],
                declarations: [OrdrCoinComponent],
                providers: [
                    OrdrCoinService
                ]
            })
            .overrideTemplate(OrdrCoinComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrdrCoinComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdrCoinService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new OrdrCoin(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.ordrCoins[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
