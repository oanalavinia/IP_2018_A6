import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('SalesDet e2e test', () => {

    let navBarPage: NavBarPage;
    let salesDetDialogPage: SalesDetDialogPage;
    let salesDetComponentsPage: SalesDetComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load SalesDets', () => {
        navBarPage.goToEntity('sales-det');
        salesDetComponentsPage = new SalesDetComponentsPage();
        expect(salesDetComponentsPage.getTitle())
            .toMatch(/Sales Dets/);

    });

    it('should load create SalesDet dialog', () => {
        salesDetComponentsPage.clickOnCreateButton();
        salesDetDialogPage = new SalesDetDialogPage();
        expect(salesDetDialogPage.getModalTitle())
            .toMatch(/Create or edit a Sales Det/);
        salesDetDialogPage.close();
    });

    it('should create and save SalesDets', () => {
        salesDetComponentsPage.clickOnCreateButton();
        salesDetDialogPage.setProductCODEInput('productCODE');
        expect(salesDetDialogPage.getProductCODEInput()).toMatch('productCODE');
        salesDetDialogPage.setPriceInput('5');
        expect(salesDetDialogPage.getPriceInput()).toMatch('5');
        salesDetDialogPage.save();
        expect(salesDetDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SalesDetComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-sales-det div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class SalesDetDialogPage {
    modalTitle = element(by.css('h4#mySalesDetLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    productCODEInput = element(by.css('input#field_productCODE'));
    priceInput = element(by.css('input#field_price'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setProductCODEInput = function(productCODE) {
        this.productCODEInput.sendKeys(productCODE);
    };

    getProductCODEInput = function() {
        return this.productCODEInput.getAttribute('value');
    };

    setPriceInput = function(price) {
        this.priceInput.sendKeys(price);
    };

    getPriceInput = function() {
        return this.priceInput.getAttribute('value');
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
