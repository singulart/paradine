import { element, by, ElementFinder } from 'protractor';

export class IntendedVisitComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-intended-visit div table .btn-danger'));
  title = element.all(by.css('jhi-intended-visit div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class IntendedVisitUpdatePage {
  pageTitle = element(by.id('jhi-intended-visit-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  uuidInput = element(by.id('field_uuid'));
  visitStartDateInput = element(by.id('field_visitStartDate'));
  visitEndDateInput = element(by.id('field_visitEndDate'));
  cancelledInput = element(by.id('field_cancelled'));

  visitingUserSelect = element(by.id('field_visitingUser'));
  restaurantSelect = element(by.id('field_restaurant'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setUuidInput(uuid: string): Promise<void> {
    await this.uuidInput.sendKeys(uuid);
  }

  async getUuidInput(): Promise<string> {
    return await this.uuidInput.getAttribute('value');
  }

  async setVisitStartDateInput(visitStartDate: string): Promise<void> {
    await this.visitStartDateInput.sendKeys(visitStartDate);
  }

  async getVisitStartDateInput(): Promise<string> {
    return await this.visitStartDateInput.getAttribute('value');
  }

  async setVisitEndDateInput(visitEndDate: string): Promise<void> {
    await this.visitEndDateInput.sendKeys(visitEndDate);
  }

  async getVisitEndDateInput(): Promise<string> {
    return await this.visitEndDateInput.getAttribute('value');
  }

  getCancelledInput(): ElementFinder {
    return this.cancelledInput;
  }

  async visitingUserSelectLastOption(): Promise<void> {
    await this.visitingUserSelect.all(by.tagName('option')).last().click();
  }

  async visitingUserSelectOption(option: string): Promise<void> {
    await this.visitingUserSelect.sendKeys(option);
  }

  getVisitingUserSelect(): ElementFinder {
    return this.visitingUserSelect;
  }

  async getVisitingUserSelectedOption(): Promise<string> {
    return await this.visitingUserSelect.element(by.css('option:checked')).getText();
  }

  async restaurantSelectLastOption(): Promise<void> {
    await this.restaurantSelect.all(by.tagName('option')).last().click();
  }

  async restaurantSelectOption(option: string): Promise<void> {
    await this.restaurantSelect.sendKeys(option);
  }

  getRestaurantSelect(): ElementFinder {
    return this.restaurantSelect;
  }

  async getRestaurantSelectedOption(): Promise<string> {
    return await this.restaurantSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class IntendedVisitDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-intendedVisit-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-intendedVisit'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
