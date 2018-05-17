/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GatewayTestModule } from '../../../test.module';
import { MarketingDetDetailComponent } from '../../../../../../main/webapp/app/entities/marketing-det/marketing-det-detail.component';
import { MarketingDetService } from '../../../../../../main/webapp/app/entities/marketing-det/marketing-det.service';
import { MarketingDet } from '../../../../../../main/webapp/app/entities/marketing-det/marketing-det.model';

describe('Component Tests', () => {

    describe('MarketingDet Management Detail Component', () => {
        let comp: MarketingDetDetailComponent;
        let fixture: ComponentFixture<MarketingDetDetailComponent>;
        let service: MarketingDetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [MarketingDetDetailComponent],
                providers: [
                    MarketingDetService
                ]
            })
            .overrideTemplate(MarketingDetDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketingDetDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketingDetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new MarketingDet(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.marketingDet).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
