/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { YdtTestModule } from '../../../test.module';
import { OrdrDetailComponent } from '../../../../../../main/webapp/app/entities/ordr/ordr-detail.component';
import { OrdrService } from '../../../../../../main/webapp/app/entities/ordr/ordr.service';
import { Ordr } from '../../../../../../main/webapp/app/entities/ordr/ordr.model';

describe('Component Tests', () => {

    describe('Ordr Management Detail Component', () => {
        let comp: OrdrDetailComponent;
        let fixture: ComponentFixture<OrdrDetailComponent>;
        let service: OrdrService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [YdtTestModule],
                declarations: [OrdrDetailComponent],
                providers: [
                    OrdrService
                ]
            })
            .overrideTemplate(OrdrDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrdrDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdrService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Ordr(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.ordr).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
