/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YdtTestModule } from '../../../test.module';
import { OrdrComponent } from '../../../../../../main/webapp/app/entities/ordr/ordr.component';
import { OrdrService } from '../../../../../../main/webapp/app/entities/ordr/ordr.service';
import { Ordr } from '../../../../../../main/webapp/app/entities/ordr/ordr.model';

describe('Component Tests', () => {

    describe('Ordr Management Component', () => {
        let comp: OrdrComponent;
        let fixture: ComponentFixture<OrdrComponent>;
        let service: OrdrService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [YdtTestModule],
                declarations: [OrdrComponent],
                providers: [
                    OrdrService
                ]
            })
            .overrideTemplate(OrdrComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrdrComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdrService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Ordr(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.ordrs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
