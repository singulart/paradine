import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AchievementComponentsPage, AchievementDeleteDialog, AchievementUpdatePage } from './achievement.page-object';

const expect = chai.expect;

describe('Achievement e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let achievementComponentsPage: AchievementComponentsPage;
  let achievementUpdatePage: AchievementUpdatePage;
  let achievementDeleteDialog: AchievementDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Achievements', async () => {
    await navBarPage.goToEntity('achievement');
    achievementComponentsPage = new AchievementComponentsPage();
    await browser.wait(ec.visibilityOf(achievementComponentsPage.title), 5000);
    expect(await achievementComponentsPage.getTitle()).to.eq('paradineApp.achievement.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(achievementComponentsPage.entities), ec.visibilityOf(achievementComponentsPage.noResult)),
      1000
    );
  });

  it('should load create Achievement page', async () => {
    await achievementComponentsPage.clickOnCreateButton();
    achievementUpdatePage = new AchievementUpdatePage();
    expect(await achievementUpdatePage.getPageTitle()).to.eq('paradineApp.achievement.home.createOrEditLabel');
    await achievementUpdatePage.cancel();
  });

  it('should create and save Achievements', async () => {
    const nbButtonsBeforeCreate = await achievementComponentsPage.countDeleteButtons();

    await achievementComponentsPage.clickOnCreateButton();

    await promise.all([
      achievementUpdatePage.setSlugInput('slug'),
      achievementUpdatePage.setNameEnInput('nameEn'),
      achievementUpdatePage.setNameRuInput('nameRu'),
      achievementUpdatePage.setNameUaInput('nameUa'),
      achievementUpdatePage.setDescriptionEnInput('descriptionEn'),
      achievementUpdatePage.setDescriptionRuInput('descriptionRu'),
      achievementUpdatePage.setDescriptionUaInput('descriptionUa'),
    ]);

    expect(await achievementUpdatePage.getSlugInput()).to.eq('slug', 'Expected Slug value to be equals to slug');
    expect(await achievementUpdatePage.getNameEnInput()).to.eq('nameEn', 'Expected NameEn value to be equals to nameEn');
    expect(await achievementUpdatePage.getNameRuInput()).to.eq('nameRu', 'Expected NameRu value to be equals to nameRu');
    expect(await achievementUpdatePage.getNameUaInput()).to.eq('nameUa', 'Expected NameUa value to be equals to nameUa');
    expect(await achievementUpdatePage.getDescriptionEnInput()).to.eq(
      'descriptionEn',
      'Expected DescriptionEn value to be equals to descriptionEn'
    );
    expect(await achievementUpdatePage.getDescriptionRuInput()).to.eq(
      'descriptionRu',
      'Expected DescriptionRu value to be equals to descriptionRu'
    );
    expect(await achievementUpdatePage.getDescriptionUaInput()).to.eq(
      'descriptionUa',
      'Expected DescriptionUa value to be equals to descriptionUa'
    );

    await achievementUpdatePage.save();
    expect(await achievementUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await achievementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Achievement', async () => {
    const nbButtonsBeforeDelete = await achievementComponentsPage.countDeleteButtons();
    await achievementComponentsPage.clickOnLastDeleteButton();

    achievementDeleteDialog = new AchievementDeleteDialog();
    expect(await achievementDeleteDialog.getDialogTitle()).to.eq('paradineApp.achievement.delete.question');
    await achievementDeleteDialog.clickOnConfirmButton();

    expect(await achievementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
