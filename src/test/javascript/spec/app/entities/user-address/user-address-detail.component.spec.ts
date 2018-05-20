/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { YdtTestModule } from '../../../test.module';
import { UserAddressDetailComponent } from '../../../../../../main/webapp/app/entities/user-address/user-address-detail.component';
import { UserAddressService } from '../../../../../../main/webapp/app/entities/user-address/user-address.service';
import { UserAddress } from '../../../../../../main/webapp/app/entities/user-address/user-address.model';

describe('Component Tests', () => {

    describe('UserAddress Management Detail Component', () => {
        let comp: UserAddressDetailComponent;
        let fixture: ComponentFixture<UserAddressDetailComponent>;
        let service: UserAddressService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [YdtTestModule],
                declarations: [UserAddressDetailComponent],
                providers: [
                    UserAddressService
                ]
            })
            .overrideTemplate(UserAddressDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserAddressDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserAddressService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new UserAddress(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.userAddress).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
