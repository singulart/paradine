import { element, by, ElementFinder } from 'protractor';

export class WorkingHoursComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-working-hours div table .btn-danger'));
  title = element.all(by.css('jhi-working-hours div h2#page-heading span')).first();
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

export class WorkingHoursUpdatePage {
  pageTitle = element(by.id('jhi-working-hours-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  dayOfWeekInput = element(by.id('field_dayOfWeek'));
  closedInput = element(by.id('field_closed'));
  openingHourInput = element(by.id('field_openingHour'));
  closingHourInput = element(by.id('field_closingHour'));

  restaurantSelect = element(by.id('field_restaurant'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDayOfWeekInput(dayOfWeek: string): Promise<void> {
    await this.dayOfWeekInput.sendKeys(dayOfWeek);
  }

  async getDayOfWeekInput(): Promise<string> {
    return await this.dayOfWeekInput.getAttribute('value');
  }

  getClosedInput(): ElementFinder {
    return this.closedInput;
  }

  async setOpeningHourInput(openingHour: string): Promise<void> {
    await this.openingHourInput.sendKeys(openingHour);
  }

  async getOpeningHourInput(): Promise<string> {
    return await this.openingHourInput.getAttribute('value');
  }

  async setClosingHourInput(closingHour: string): Promise<void> {
    await this.closingHourInput.sendKeys(closingHour);
  }

  async getClosingHourInput(): Promise<string> {
    return await this.closingHourInput.getAttribute('value');
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

export class WorkingHoursDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-workingHours-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-workingHours'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
