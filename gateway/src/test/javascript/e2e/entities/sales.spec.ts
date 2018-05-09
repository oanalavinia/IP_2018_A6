import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Sales e2e test', () => {

    let navBarPage: NavBarPage;
    let salesDialogPage: SalesDialogPage;
    let salesComponentsPage: SalesComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Sales', () => {
        navBarPage.goToEntity('sales');
        salesComponentsPage = new SalesComponentsPage();
        expect(salesComponentsPage.getTitle())
            .toMatch(/Sales/);

    });

    it('should load create Sales dialog', () => {
        salesComponentsPage.clickOnCreateButton();
        salesDialogPage = new SalesDialogPage();
        expect(salesDialogPage.getModalTitle())
            .toMatch(/Create or edit a Sales/);
        salesDialogPage.close();
    });

    it('should create and save Sales', () => {
        salesComponentsPage.clickOnCreateButton();
        salesDialogPage.setNameInput('name');
        expect(salesDialogPage.getNameInput()).toMatch('name');
        salesDialogPage.setPriceInput('5');
        expect(salesDialogPage.getPriceInput()).toMatch('5');
        salesDialogPage.save();
        expect(salesDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SalesComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-sales div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class SalesDialogPage {
    modalTitle = element(by.css('h4#mySalesLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    priceInput = element(by.css('input#field_price'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
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
