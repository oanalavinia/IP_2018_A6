import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('WarehouseDet e2e test', () => {

    let navBarPage: NavBarPage;
    let warehouseDetDialogPage: WarehouseDetDialogPage;
    let warehouseDetComponentsPage: WarehouseDetComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load WarehouseDets', () => {
        navBarPage.goToEntity('warehouse-det');
        warehouseDetComponentsPage = new WarehouseDetComponentsPage();
        expect(warehouseDetComponentsPage.getTitle())
            .toMatch(/Warehouse Dets/);

    });

    it('should load create WarehouseDet dialog', () => {
        warehouseDetComponentsPage.clickOnCreateButton();
        warehouseDetDialogPage = new WarehouseDetDialogPage();
        expect(warehouseDetDialogPage.getModalTitle())
            .toMatch(/Create or edit a Warehouse Det/);
        warehouseDetDialogPage.close();
    });

    it('should create and save WarehouseDets', () => {
        warehouseDetComponentsPage.clickOnCreateButton();
        warehouseDetDialogPage.setProductCODEInput('productCODE');
        expect(warehouseDetDialogPage.getProductCODEInput()).toMatch('productCODE');
        warehouseDetDialogPage.setStockInput('5');
        expect(warehouseDetDialogPage.getStockInput()).toMatch('5');
        warehouseDetDialogPage.save();
        expect(warehouseDetDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class WarehouseDetComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-warehouse-det div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class WarehouseDetDialogPage {
    modalTitle = element(by.css('h4#myWarehouseDetLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    productCODEInput = element(by.css('input#field_productCODE'));
    stockInput = element(by.css('input#field_stock'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setProductCODEInput = function(productCODE) {
        this.productCODEInput.sendKeys(productCODE);
    };

    getProductCODEInput = function() {
        return this.productCODEInput.getAttribute('value');
    };

    setStockInput = function(stock) {
        this.stockInput.sendKeys(stock);
    };

    getStockInput = function() {
        return this.stockInput.getAttribute('value');
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
