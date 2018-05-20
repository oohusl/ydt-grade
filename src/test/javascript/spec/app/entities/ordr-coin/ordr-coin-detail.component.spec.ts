/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { YdtTestModule } from '../../../test.module';
import { OrdrCoinDetailComponent } from '../../../../../../main/webapp/app/entities/ordr-coin/ordr-coin-detail.component';
import { OrdrCoinService } from '../../../../../../main/webapp/app/entities/ordr-coin/ordr-coin.service';
import { OrdrCoin } from '../../../../../../main/webapp/app/entities/ordr-coin/ordr-coin.model';

describe('Component Tests', () => {

    describe('OrdrCoin Management Detail Component', () => {
        let comp: OrdrCoinDetailComponent;
        let fixture: ComponentFixture<OrdrCoinDetailComponent>;
        let service: OrdrCoinService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [YdtTestModule],
                declarations: [OrdrCoinDetailComponent],
                providers: [
                    OrdrCoinService
                ]
            })
            .overrideTemplate(OrdrCoinDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrdrCoinDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdrCoinService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new OrdrCoin(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.ordrCoin).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
