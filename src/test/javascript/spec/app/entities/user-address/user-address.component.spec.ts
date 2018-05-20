/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YdtTestModule } from '../../../test.module';
import { UserAddressComponent } from '../../../../../../main/webapp/app/entities/user-address/user-address.component';
import { UserAddressService } from '../../../../../../main/webapp/app/entities/user-address/user-address.service';
import { UserAddress } from '../../../../../../main/webapp/app/entities/user-address/user-address.model';

describe('Component Tests', () => {

    describe('UserAddress Management Component', () => {
        let comp: UserAddressComponent;
        let fixture: ComponentFixture<UserAddressComponent>;
        let service: UserAddressService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [YdtTestModule],
                declarations: [UserAddressComponent],
                providers: [
                    UserAddressService
                ]
            })
            .overrideTemplate(UserAddressComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserAddressComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserAddressService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new UserAddress(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.userAddresses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
