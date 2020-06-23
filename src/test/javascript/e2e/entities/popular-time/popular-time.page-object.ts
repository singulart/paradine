import { element, by, ElementFinder } from 'protractor';

export class PopularTimeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-popular-time div table .btn-danger'));
  title = element.all(by.css('jhi-popular-time div h2#page-heading span')).first();
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

export class PopularTimeUpdatePage {
  pageTitle = element(by.id('jhi-popular-time-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  dayOfWeekInput = element(by.id('field_dayOfWeek'));
  occ06Input = element(by.id('field_occ06'));
  occ07Input = element(by.id('field_occ07'));
  occ08Input = element(by.id('field_occ08'));
  occ09Input = element(by.id('field_occ09'));
  occ10Input = element(by.id('field_occ10'));
  occ11Input = element(by.id('field_occ11'));
  occ12Input = element(by.id('field_occ12'));
  occ13Input = element(by.id('field_occ13'));
  occ14Input = element(by.id('field_occ14'));
  occ15Input = element(by.id('field_occ15'));
  occ16Input = element(by.id('field_occ16'));
  occ17Input = element(by.id('field_occ17'));
  occ18Input = element(by.id('field_occ18'));
  occ19Input = element(by.id('field_occ19'));
  occ20Input = element(by.id('field_occ20'));
  occ21Input = element(by.id('field_occ21'));
  occ22Input = element(by.id('field_occ22'));
  occ23Input = element(by.id('field_occ23'));

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

  async setOcc06Input(occ06: string): Promise<void> {
    await this.occ06Input.sendKeys(occ06);
  }

  async getOcc06Input(): Promise<string> {
    return await this.occ06Input.getAttribute('value');
  }

  async setOcc07Input(occ07: string): Promise<void> {
    await this.occ07Input.sendKeys(occ07);
  }

  async getOcc07Input(): Promise<string> {
    return await this.occ07Input.getAttribute('value');
  }

  async setOcc08Input(occ08: string): Promise<void> {
    await this.occ08Input.sendKeys(occ08);
  }

  async getOcc08Input(): Promise<string> {
    return await this.occ08Input.getAttribute('value');
  }

  async setOcc09Input(occ09: string): Promise<void> {
    await this.occ09Input.sendKeys(occ09);
  }

  async getOcc09Input(): Promise<string> {
    return await this.occ09Input.getAttribute('value');
  }

  async setOcc10Input(occ10: string): Promise<void> {
    await this.occ10Input.sendKeys(occ10);
  }

  async getOcc10Input(): Promise<string> {
    return await this.occ10Input.getAttribute('value');
  }

  async setOcc11Input(occ11: string): Promise<void> {
    await this.occ11Input.sendKeys(occ11);
  }

  async getOcc11Input(): Promise<string> {
    return await this.occ11Input.getAttribute('value');
  }

  async setOcc12Input(occ12: string): Promise<void> {
    await this.occ12Input.sendKeys(occ12);
  }

  async getOcc12Input(): Promise<string> {
    return await this.occ12Input.getAttribute('value');
  }

  async setOcc13Input(occ13: string): Promise<void> {
    await this.occ13Input.sendKeys(occ13);
  }

  async getOcc13Input(): Promise<string> {
    return await this.occ13Input.getAttribute('value');
  }

  async setOcc14Input(occ14: string): Promise<void> {
    await this.occ14Input.sendKeys(occ14);
  }

  async getOcc14Input(): Promise<string> {
    return await this.occ14Input.getAttribute('value');
  }

  async setOcc15Input(occ15: string): Promise<void> {
    await this.occ15Input.sendKeys(occ15);
  }

  async getOcc15Input(): Promise<string> {
    return await this.occ15Input.getAttribute('value');
  }

  async setOcc16Input(occ16: string): Promise<void> {
    await this.occ16Input.sendKeys(occ16);
  }

  async getOcc16Input(): Promise<string> {
    return await this.occ16Input.getAttribute('value');
  }

  async setOcc17Input(occ17: string): Promise<void> {
    await this.occ17Input.sendKeys(occ17);
  }

  async getOcc17Input(): Promise<string> {
    return await this.occ17Input.getAttribute('value');
  }

  async setOcc18Input(occ18: string): Promise<void> {
    await this.occ18Input.sendKeys(occ18);
  }

  async getOcc18Input(): Promise<string> {
    return await this.occ18Input.getAttribute('value');
  }

  async setOcc19Input(occ19: string): Promise<void> {
    await this.occ19Input.sendKeys(occ19);
  }

  async getOcc19Input(): Promise<string> {
    return await this.occ19Input.getAttribute('value');
  }

  async setOcc20Input(occ20: string): Promise<void> {
    await this.occ20Input.sendKeys(occ20);
  }

  async getOcc20Input(): Promise<string> {
    return await this.occ20Input.getAttribute('value');
  }

  async setOcc21Input(occ21: string): Promise<void> {
    await this.occ21Input.sendKeys(occ21);
  }

  async getOcc21Input(): Promise<string> {
    return await this.occ21Input.getAttribute('value');
  }

  async setOcc22Input(occ22: string): Promise<void> {
    await this.occ22Input.sendKeys(occ22);
  }

  async getOcc22Input(): Promise<string> {
    return await this.occ22Input.getAttribute('value');
  }

  async setOcc23Input(occ23: string): Promise<void> {
    await this.occ23Input.sendKeys(occ23);
  }

  async getOcc23Input(): Promise<string> {
    return await this.occ23Input.getAttribute('value');
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

export class PopularTimeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-popularTime-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-popularTime'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
