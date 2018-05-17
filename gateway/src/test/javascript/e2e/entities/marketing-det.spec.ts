import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('MarketingDet e2e test', () => {

    let navBarPage: NavBarPage;
    let marketingDetDialogPage: MarketingDetDialogPage;
    let marketingDetComponentsPage: MarketingDetComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load MarketingDets', () => {
        navBarPage.goToEntity('marketing-det');
        marketingDetComponentsPage = new MarketingDetComponentsPage();
        expect(marketingDetComponentsPage.getTitle())
            .toMatch(/Marketing Dets/);

    });

    it('should load create MarketingDet dialog', () => {
        marketingDetComponentsPage.clickOnCreateButton();
        marketingDetDialogPage = new MarketingDetDialogPage();
        expect(marketingDetDialogPage.getModalTitle())
            .toMatch(/Create or edit a Marketing Det/);
        marketingDetDialogPage.close();
    });

    it('should create and save MarketingDets', () => {
        marketingDetComponentsPage.clickOnCreateButton();
        marketingDetDialogPage.setProductCODEInput('productCODE');
        expect(marketingDetDialogPage.getProductCODEInput()).toMatch('productCODE');
        marketingDetDialogPage.setNameInput('name');
        expect(marketingDetDialogPage.getNameInput()).toMatch('name');
        marketingDetDialogPage.setDescriptionInput('description');
        expect(marketingDetDialogPage.getDescriptionInput()).toMatch('description');
        marketingDetDialogPage.save();
        expect(marketingDetDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MarketingDetComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-marketing-det div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class MarketingDetDialogPage {
    modalTitle = element(by.css('h4#myMarketingDetLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    productCODEInput = element(by.css('input#field_productCODE'));
    nameInput = element(by.css('input#field_name'));
    descriptionInput = element(by.css('input#field_description'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setProductCODEInput = function(productCODE) {
        this.productCODEInput.sendKeys(productCODE);
    };

    getProductCODEInput = function() {
        return this.productCODEInput.getAttribute('value');
    };

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    setDescriptionInput = function(description) {
        this.descriptionInput.sendKeys(description);
    };

    getDescriptionInput = function() {
        return this.descriptionInput.getAttribute('value');
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
