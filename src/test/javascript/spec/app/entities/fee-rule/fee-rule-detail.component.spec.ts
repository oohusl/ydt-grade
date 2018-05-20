/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { YdtTestModule } from '../../../test.module';
import { FeeRuleDetailComponent } from '../../../../../../main/webapp/app/entities/fee-rule/fee-rule-detail.component';
import { FeeRuleService } from '../../../../../../main/webapp/app/entities/fee-rule/fee-rule.service';
import { FeeRule } from '../../../../../../main/webapp/app/entities/fee-rule/fee-rule.model';

describe('Component Tests', () => {

    describe('FeeRule Management Detail Component', () => {
        let comp: FeeRuleDetailComponent;
        let fixture: ComponentFixture<FeeRuleDetailComponent>;
        let service: FeeRuleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [YdtTestModule],
                declarations: [FeeRuleDetailComponent],
                providers: [
                    FeeRuleService
                ]
            })
            .overrideTemplate(FeeRuleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FeeRuleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FeeRuleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new FeeRule(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.feeRule).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
