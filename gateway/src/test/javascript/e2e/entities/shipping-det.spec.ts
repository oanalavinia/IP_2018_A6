import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('ShippingDet e2e test', () => {

    let navBarPage: NavBarPage;
    let shippingDetDialogPage: ShippingDetDialogPage;
    let shippingDetComponentsPage: ShippingDetComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ShippingDets', () => {
        navBarPage.goToEntity('shipping-det');
        shippingDetComponentsPage = new ShippingDetComponentsPage();
        expect(shippingDetComponentsPage.getTitle())
            .toMatch(/Shipping Dets/);

    });

    it('should load create ShippingDet dialog', () => {
        shippingDetComponentsPage.clickOnCreateButton();
        shippingDetDialogPage = new ShippingDetDialogPage();
        expect(shippingDetDialogPage.getModalTitle())
            .toMatch(/Create or edit a Shipping Det/);
        shippingDetDialogPage.close();
    });

    it('should create and save ShippingDets', () => {
        shippingDetComponentsPage.clickOnCreateButton();
        shippingDetDialogPage.setProductCODEInput('productCODE');
        expect(shippingDetDialogPage.getProductCODEInput()).toMatch('productCODE');
        shippingDetDialogPage.setShipCostInput('5');
        expect(shippingDetDialogPage.getShipCostInput()).toMatch('5');
        shippingDetDialogPage.save();
        expect(shippingDetDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ShippingDetComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-shipping-det div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class ShippingDetDialogPage {
    modalTitle = element(by.css('h4#myShippingDetLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    productCODEInput = element(by.css('input#field_productCODE'));
    shipCostInput = element(by.css('input#field_shipCost'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setProductCODEInput = function(productCODE) {
        this.productCODEInput.sendKeys(productCODE);
    };

    getProductCODEInput = function() {
        return this.productCODEInput.getAttribute('value');
    };

    setShipCostInput = function(shipCost) {
        this.shipCostInput.sendKeys(shipCost);
    };

    getShipCostInput = function() {
        return this.shipCostInput.getAttribute('value');
    };

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
