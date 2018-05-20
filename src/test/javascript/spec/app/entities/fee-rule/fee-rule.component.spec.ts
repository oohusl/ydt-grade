/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YdtTestModule } from '../../../test.module';
import { FeeRuleComponent } from '../../../../../../main/webapp/app/entities/fee-rule/fee-rule.component';
import { FeeRuleService } from '../../../../../../main/webapp/app/entities/fee-rule/fee-rule.service';
import { FeeRule } from '../../../../../../main/webapp/app/entities/fee-rule/fee-rule.model';

describe('Component Tests', () => {

    describe('FeeRule Management Component', () => {
        let comp: FeeRuleComponent;
        let fixture: ComponentFixture<FeeRuleComponent>;
        let service: FeeRuleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [YdtTestModule],
                declarations: [FeeRuleComponent],
                providers: [
                    FeeRuleService
                ]
            })
            .overrideTemplate(FeeRuleComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FeeRuleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FeeRuleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new FeeRule(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.feeRules[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
