import { element, by, ElementFinder } from 'protractor';

export class RestaurantComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-restaurant div table .btn-danger'));
  title = element.all(by.css('jhi-restaurant div h2#page-heading span')).first();
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

export class RestaurantUpdatePage {
  pageTitle = element(by.id('jhi-restaurant-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  altName1Input = element(by.id('field_altName1'));
  addressEnInput = element(by.id('field_addressEn'));
  addressRuInput = element(by.id('field_addressRu'));
  addressUaInput = element(by.id('field_addressUa'));
  googlePlacesIdInput = element(by.id('field_googlePlacesId'));
  geolatInput = element(by.id('field_geolat'));
  geolngInput = element(by.id('field_geolng'));
  photoUrlInput = element(by.id('field_photoUrl'));
  altName2Input = element(by.id('field_altName2'));
  altName3Input = element(by.id('field_altName3'));
  capacityInput = element(by.id('field_capacity'));
  createdAtInput = element(by.id('field_createdAt'));
  updatedAtInput = element(by.id('field_updatedAt'));
  uuidInput = element(by.id('field_uuid'));

  citySelect = element(by.id('field_city'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setAltName1Input(altName1: string): Promise<void> {
    await this.altName1Input.sendKeys(altName1);
  }

  async getAltName1Input(): Promise<string> {
    return await this.altName1Input.getAttribute('value');
  }

  async setAddressEnInput(addressEn: string): Promise<void> {
    await this.addressEnInput.sendKeys(addressEn);
  }

  async getAddressEnInput(): Promise<string> {
    return await this.addressEnInput.getAttribute('value');
  }

  async setAddressRuInput(addressRu: string): Promise<void> {
    await this.addressRuInput.sendKeys(addressRu);
  }

  async getAddressRuInput(): Promise<string> {
    return await this.addressRuInput.getAttribute('value');
  }

  async setAddressUaInput(addressUa: string): Promise<void> {
    await this.addressUaInput.sendKeys(addressUa);
  }

  async getAddressUaInput(): Promise<string> {
    return await this.addressUaInput.getAttribute('value');
  }

  async setGooglePlacesIdInput(googlePlacesId: string): Promise<void> {
    await this.googlePlacesIdInput.sendKeys(googlePlacesId);
  }

  async getGooglePlacesIdInput(): Promise<string> {
    return await this.googlePlacesIdInput.getAttribute('value');
  }

  async setGeolatInput(geolat: string): Promise<void> {
    await this.geolatInput.sendKeys(geolat);
  }

  async getGeolatInput(): Promise<string> {
    return await this.geolatInput.getAttribute('value');
  }

  async setGeolngInput(geolng: string): Promise<void> {
    await this.geolngInput.sendKeys(geolng);
  }

  async getGeolngInput(): Promise<string> {
    return await this.geolngInput.getAttribute('value');
  }

  async setPhotoUrlInput(photoUrl: string): Promise<void> {
    await this.photoUrlInput.sendKeys(photoUrl);
  }

  async getPhotoUrlInput(): Promise<string> {
    return await this.photoUrlInput.getAttribute('value');
  }

  async setAltName2Input(altName2: string): Promise<void> {
    await this.altName2Input.sendKeys(altName2);
  }

  async getAltName2Input(): Promise<string> {
    return await this.altName2Input.getAttribute('value');
  }

  async setAltName3Input(altName3: string): Promise<void> {
    await this.altName3Input.sendKeys(altName3);
  }

  async getAltName3Input(): Promise<string> {
    return await this.altName3Input.getAttribute('value');
  }

  async setCapacityInput(capacity: string): Promise<void> {
    await this.capacityInput.sendKeys(capacity);
  }

  async getCapacityInput(): Promise<string> {
    return await this.capacityInput.getAttribute('value');
  }

  async setCreatedAtInput(createdAt: string): Promise<void> {
    await this.createdAtInput.sendKeys(createdAt);
  }

  async getCreatedAtInput(): Promise<string> {
    return await this.createdAtInput.getAttribute('value');
  }

  async setUpdatedAtInput(updatedAt: string): Promise<void> {
    await this.updatedAtInput.sendKeys(updatedAt);
  }

  async getUpdatedAtInput(): Promise<string> {
    return await this.updatedAtInput.getAttribute('value');
  }

  async setUuidInput(uuid: string): Promise<void> {
    await this.uuidInput.sendKeys(uuid);
  }

  async getUuidInput(): Promise<string> {
    return await this.uuidInput.getAttribute('value');
  }

  async citySelectLastOption(): Promise<void> {
    await this.citySelect.all(by.tagName('option')).last().click();
  }

  async citySelectOption(option: string): Promise<void> {
    await this.citySelect.sendKeys(option);
  }

  getCitySelect(): ElementFinder {
    return this.citySelect;
  }

  async getCitySelectedOption(): Promise<string> {
    return await this.citySelect.element(by.css('option:checked')).getText();
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

export class RestaurantDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-restaurant-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-restaurant'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
