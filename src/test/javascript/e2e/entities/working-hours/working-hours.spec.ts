import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { WorkingHoursComponentsPage, WorkingHoursDeleteDialog, WorkingHoursUpdatePage } from './working-hours.page-object';

const expect = chai.expect;

describe('WorkingHours e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let workingHoursComponentsPage: WorkingHoursComponentsPage;
  let workingHoursUpdatePage: WorkingHoursUpdatePage;
  let workingHoursDeleteDialog: WorkingHoursDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load WorkingHours', async () => {
    await navBarPage.goToEntity('working-hours');
    workingHoursComponentsPage = new WorkingHoursComponentsPage();
    await browser.wait(ec.visibilityOf(workingHoursComponentsPage.title), 5000);
    expect(await workingHoursComponentsPage.getTitle()).to.eq('paradineApp.workingHours.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(workingHoursComponentsPage.entities), ec.visibilityOf(workingHoursComponentsPage.noResult)),
      1000
    );
  });

  it('should load create WorkingHours page', async () => {
    await workingHoursComponentsPage.clickOnCreateButton();
    workingHoursUpdatePage = new WorkingHoursUpdatePage();
    expect(await workingHoursUpdatePage.getPageTitle()).to.eq('paradineApp.workingHours.home.createOrEditLabel');
    await workingHoursUpdatePage.cancel();
  });

  it('should create and save WorkingHours', async () => {
    const nbButtonsBeforeCreate = await workingHoursComponentsPage.countDeleteButtons();

    await workingHoursComponentsPage.clickOnCreateButton();

    await promise.all([
      workingHoursUpdatePage.setDayOfWeekInput('dayOfWeek'),
      workingHoursUpdatePage.setOpeningHourInput('5'),
      workingHoursUpdatePage.setClosingHourInput('5'),
      workingHoursUpdatePage.restaurantSelectLastOption(),
    ]);

    expect(await workingHoursUpdatePage.getDayOfWeekInput()).to.eq('dayOfWeek', 'Expected DayOfWeek value to be equals to dayOfWeek');
    const selectedClosed = workingHoursUpdatePage.getClosedInput();
    if (await selectedClosed.isSelected()) {
      await workingHoursUpdatePage.getClosedInput().click();
      expect(await workingHoursUpdatePage.getClosedInput().isSelected(), 'Expected closed not to be selected').to.be.false;
    } else {
      await workingHoursUpdatePage.getClosedInput().click();
      expect(await workingHoursUpdatePage.getClosedInput().isSelected(), 'Expected closed to be selected').to.be.true;
    }
    expect(await workingHoursUpdatePage.getOpeningHourInput()).to.eq('5', 'Expected openingHour value to be equals to 5');
    expect(await workingHoursUpdatePage.getClosingHourInput()).to.eq('5', 'Expected closingHour value to be equals to 5');

    await workingHoursUpdatePage.save();
    expect(await workingHoursUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await workingHoursComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last WorkingHours', async () => {
    const nbButtonsBeforeDelete = await workingHoursComponentsPage.countDeleteButtons();
    await workingHoursComponentsPage.clickOnLastDeleteButton();

    workingHoursDeleteDialog = new WorkingHoursDeleteDialog();
    expect(await workingHoursDeleteDialog.getDialogTitle()).to.eq('paradineApp.workingHours.delete.question');
    await workingHoursDeleteDialog.clickOnConfirmButton();

    expect(await workingHoursComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
