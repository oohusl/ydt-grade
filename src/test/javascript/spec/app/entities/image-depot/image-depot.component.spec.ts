/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YdtTestModule } from '../../../test.module';
import { ImageDepotComponent } from '../../../../../../main/webapp/app/entities/image-depot/image-depot.component';
import { ImageDepotService } from '../../../../../../main/webapp/app/entities/image-depot/image-depot.service';
import { ImageDepot } from '../../../../../../main/webapp/app/entities/image-depot/image-depot.model';

describe('Component Tests', () => {

    describe('ImageDepot Management Component', () => {
        let comp: ImageDepotComponent;
        let fixture: ComponentFixture<ImageDepotComponent>;
        let service: ImageDepotService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [YdtTestModule],
                declarations: [ImageDepotComponent],
                providers: [
                    ImageDepotService
                ]
            })
            .overrideTemplate(ImageDepotComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ImageDepotComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImageDepotService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ImageDepot(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.imageDepots[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
