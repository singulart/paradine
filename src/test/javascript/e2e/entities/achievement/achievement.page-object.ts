import { element, by, ElementFinder } from 'protractor';

export class AchievementComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-achievement div table .btn-danger'));
  title = element.all(by.css('jhi-achievement div h2#page-heading span')).first();
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

export class AchievementUpdatePage {
  pageTitle = element(by.id('jhi-achievement-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  slugInput = element(by.id('field_slug'));
  nameEnInput = element(by.id('field_nameEn'));
  nameRuInput = element(by.id('field_nameRu'));
  nameUaInput = element(by.id('field_nameUa'));
  descriptionEnInput = element(by.id('field_descriptionEn'));
  descriptionRuInput = element(by.id('field_descriptionRu'));
  descriptionUaInput = element(by.id('field_descriptionUa'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSlugInput(slug: string): Promise<void> {
    await this.slugInput.sendKeys(slug);
  }

  async getSlugInput(): Promise<string> {
    return await this.slugInput.getAttribute('value');
  }

  async setNameEnInput(nameEn: string): Promise<void> {
    await this.nameEnInput.sendKeys(nameEn);
  }

  async getNameEnInput(): Promise<string> {
    return await this.nameEnInput.getAttribute('value');
  }

  async setNameRuInput(nameRu: string): Promise<void> {
    await this.nameRuInput.sendKeys(nameRu);
  }

  async getNameRuInput(): Promise<string> {
    return await this.nameRuInput.getAttribute('value');
  }

  async setNameUaInput(nameUa: string): Promise<void> {
    await this.nameUaInput.sendKeys(nameUa);
  }

  async getNameUaInput(): Promise<string> {
    return await this.nameUaInput.getAttribute('value');
  }

  async setDescriptionEnInput(descriptionEn: string): Promise<void> {
    await this.descriptionEnInput.sendKeys(descriptionEn);
  }

  async getDescriptionEnInput(): Promise<string> {
    return await this.descriptionEnInput.getAttribute('value');
  }

  async setDescriptionRuInput(descriptionRu: string): Promise<void> {
    await this.descriptionRuInput.sendKeys(descriptionRu);
  }

  async getDescriptionRuInput(): Promise<string> {
    return await this.descriptionRuInput.getAttribute('value');
  }

  async setDescriptionUaInput(descriptionUa: string): Promise<void> {
    await this.descriptionUaInput.sendKeys(descriptionUa);
  }

  async getDescriptionUaInput(): Promise<string> {
    return await this.descriptionUaInput.getAttribute('value');
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

export class AchievementDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-achievement-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-achievement'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
