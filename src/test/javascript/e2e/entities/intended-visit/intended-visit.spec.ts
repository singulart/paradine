import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  IntendedVisitComponentsPage,
  /* IntendedVisitDeleteDialog, */
  IntendedVisitUpdatePage,
} from './intended-visit.page-object';

const expect = chai.expect;

describe('IntendedVisit e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let intendedVisitComponentsPage: IntendedVisitComponentsPage;
  let intendedVisitUpdatePage: IntendedVisitUpdatePage;
  /* let intendedVisitDeleteDialog: IntendedVisitDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load IntendedVisits', async () => {
    await navBarPage.goToEntity('intended-visit');
    intendedVisitComponentsPage = new IntendedVisitComponentsPage();
    await browser.wait(ec.visibilityOf(intendedVisitComponentsPage.title), 5000);
    expect(await intendedVisitComponentsPage.getTitle()).to.eq('paradineApp.intendedVisit.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(intendedVisitComponentsPage.entities), ec.visibilityOf(intendedVisitComponentsPage.noResult)),
      1000
    );
  });

  it('should load create IntendedVisit page', async () => {
    await intendedVisitComponentsPage.clickOnCreateButton();
    intendedVisitUpdatePage = new IntendedVisitUpdatePage();
    expect(await intendedVisitUpdatePage.getPageTitle()).to.eq('paradineApp.intendedVisit.home.createOrEditLabel');
    await intendedVisitUpdatePage.cancel();
  });

  /* it('should create and save IntendedVisits', async () => {
        const nbButtonsBeforeCreate = await intendedVisitComponentsPage.countDeleteButtons();

        await intendedVisitComponentsPage.clickOnCreateButton();

        await promise.all([
            intendedVisitUpdatePage.setUuidInput('b825A3C1-4E31-D2ba-DDf9-bB2d50cfabAF'),
            intendedVisitUpdatePage.setVisitStartDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            intendedVisitUpdatePage.setVisitEndDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            intendedVisitUpdatePage.visitingUserSelectLastOption(),
            intendedVisitUpdatePage.restaurantSelectLastOption(),
        ]);

        expect(await intendedVisitUpdatePage.getUuidInput()).to.eq('b825A3C1-4E31-D2ba-DDf9-bB2d50cfabAF', 'Expected Uuid value to be equals to b825A3C1-4E31-D2ba-DDf9-bB2d50cfabAF');
        expect(await intendedVisitUpdatePage.getVisitStartDateInput()).to.contain('2001-01-01T02:30', 'Expected visitStartDate value to be equals to 2000-12-31');
        expect(await intendedVisitUpdatePage.getVisitEndDateInput()).to.contain('2001-01-01T02:30', 'Expected visitEndDate value to be equals to 2000-12-31');
        const selectedCancelled = intendedVisitUpdatePage.getCancelledInput();
        if (await selectedCancelled.isSelected()) {
            await intendedVisitUpdatePage.getCancelledInput().click();
            expect(await intendedVisitUpdatePage.getCancelledInput().isSelected(), 'Expected cancelled not to be selected').to.be.false;
        } else {
            await intendedVisitUpdatePage.getCancelledInput().click();
            expect(await intendedVisitUpdatePage.getCancelledInput().isSelected(), 'Expected cancelled to be selected').to.be.true;
        }

        await intendedVisitUpdatePage.save();
        expect(await intendedVisitUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await intendedVisitComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last IntendedVisit', async () => {
        const nbButtonsBeforeDelete = await intendedVisitComponentsPage.countDeleteButtons();
        await intendedVisitComponentsPage.clickOnLastDeleteButton();

        intendedVisitDeleteDialog = new IntendedVisitDeleteDialog();
        expect(await intendedVisitDeleteDialog.getDialogTitle())
            .to.eq('paradineApp.intendedVisit.delete.question');
        await intendedVisitDeleteDialog.clickOnConfirmButton();

        expect(await intendedVisitComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
