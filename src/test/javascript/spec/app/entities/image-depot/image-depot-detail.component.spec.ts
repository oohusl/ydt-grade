/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { YdtTestModule } from '../../../test.module';
import { ImageDepotDetailComponent } from '../../../../../../main/webapp/app/entities/image-depot/image-depot-detail.component';
import { ImageDepotService } from '../../../../../../main/webapp/app/entities/image-depot/image-depot.service';
import { ImageDepot } from '../../../../../../main/webapp/app/entities/image-depot/image-depot.model';

describe('Component Tests', () => {

    describe('ImageDepot Management Detail Component', () => {
        let comp: ImageDepotDetailComponent;
        let fixture: ComponentFixture<ImageDepotDetailComponent>;
        let service: ImageDepotService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [YdtTestModule],
                declarations: [ImageDepotDetailComponent],
                providers: [
                    ImageDepotService
                ]
            })
            .overrideTemplate(ImageDepotDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ImageDepotDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImageDepotService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ImageDepot(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.imageDepot).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
