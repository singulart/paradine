import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PopularTimeComponentsPage, PopularTimeDeleteDialog, PopularTimeUpdatePage } from './popular-time.page-object';

const expect = chai.expect;

describe('PopularTime e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let popularTimeComponentsPage: PopularTimeComponentsPage;
  let popularTimeUpdatePage: PopularTimeUpdatePage;
  let popularTimeDeleteDialog: PopularTimeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PopularTimes', async () => {
    await navBarPage.goToEntity('popular-time');
    popularTimeComponentsPage = new PopularTimeComponentsPage();
    await browser.wait(ec.visibilityOf(popularTimeComponentsPage.title), 5000);
    expect(await popularTimeComponentsPage.getTitle()).to.eq('paradineApp.popularTime.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(popularTimeComponentsPage.entities), ec.visibilityOf(popularTimeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PopularTime page', async () => {
    await popularTimeComponentsPage.clickOnCreateButton();
    popularTimeUpdatePage = new PopularTimeUpdatePage();
    expect(await popularTimeUpdatePage.getPageTitle()).to.eq('paradineApp.popularTime.home.createOrEditLabel');
    await popularTimeUpdatePage.cancel();
  });

  it('should create and save PopularTimes', async () => {
    const nbButtonsBeforeCreate = await popularTimeComponentsPage.countDeleteButtons();

    await popularTimeComponentsPage.clickOnCreateButton();

    await promise.all([
      popularTimeUpdatePage.setDayOfWeekInput('dayOfWeek'),
      popularTimeUpdatePage.setOcc01Input('5'),
      popularTimeUpdatePage.setOcc02Input('5'),
      popularTimeUpdatePage.setOcc03Input('5'),
      popularTimeUpdatePage.setOcc04Input('5'),
      popularTimeUpdatePage.setOcc05Input('5'),
      popularTimeUpdatePage.setOcc06Input('5'),
      popularTimeUpdatePage.setOcc07Input('5'),
      popularTimeUpdatePage.setOcc08Input('5'),
      popularTimeUpdatePage.setOcc09Input('5'),
      popularTimeUpdatePage.setOcc10Input('5'),
      popularTimeUpdatePage.setOcc11Input('5'),
      popularTimeUpdatePage.setOcc12Input('5'),
      popularTimeUpdatePage.setOcc13Input('5'),
      popularTimeUpdatePage.setOcc14Input('5'),
      popularTimeUpdatePage.setOcc15Input('5'),
      popularTimeUpdatePage.setOcc16Input('5'),
      popularTimeUpdatePage.setOcc17Input('5'),
      popularTimeUpdatePage.setOcc18Input('5'),
      popularTimeUpdatePage.setOcc19Input('5'),
      popularTimeUpdatePage.setOcc20Input('5'),
      popularTimeUpdatePage.setOcc21Input('5'),
      popularTimeUpdatePage.setOcc22Input('5'),
      popularTimeUpdatePage.setOcc23Input('5'),
      popularTimeUpdatePage.setOcc24Input('5'),
      popularTimeUpdatePage.restaurantSelectLastOption(),
    ]);

    expect(await popularTimeUpdatePage.getDayOfWeekInput()).to.eq('dayOfWeek', 'Expected DayOfWeek value to be equals to dayOfWeek');
    expect(await popularTimeUpdatePage.getOcc01Input()).to.eq('5', 'Expected occ01 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc02Input()).to.eq('5', 'Expected occ02 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc03Input()).to.eq('5', 'Expected occ03 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc04Input()).to.eq('5', 'Expected occ04 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc05Input()).to.eq('5', 'Expected occ05 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc06Input()).to.eq('5', 'Expected occ06 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc07Input()).to.eq('5', 'Expected occ07 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc08Input()).to.eq('5', 'Expected occ08 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc09Input()).to.eq('5', 'Expected occ09 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc10Input()).to.eq('5', 'Expected occ10 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc11Input()).to.eq('5', 'Expected occ11 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc12Input()).to.eq('5', 'Expected occ12 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc13Input()).to.eq('5', 'Expected occ13 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc14Input()).to.eq('5', 'Expected occ14 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc15Input()).to.eq('5', 'Expected occ15 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc16Input()).to.eq('5', 'Expected occ16 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc17Input()).to.eq('5', 'Expected occ17 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc18Input()).to.eq('5', 'Expected occ18 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc19Input()).to.eq('5', 'Expected occ19 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc20Input()).to.eq('5', 'Expected occ20 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc21Input()).to.eq('5', 'Expected occ21 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc22Input()).to.eq('5', 'Expected occ22 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc23Input()).to.eq('5', 'Expected occ23 value to be equals to 5');
    expect(await popularTimeUpdatePage.getOcc24Input()).to.eq('5', 'Expected occ24 value to be equals to 5');

    await popularTimeUpdatePage.save();
    expect(await popularTimeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await popularTimeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last PopularTime', async () => {
    const nbButtonsBeforeDelete = await popularTimeComponentsPage.countDeleteButtons();
    await popularTimeComponentsPage.clickOnLastDeleteButton();

    popularTimeDeleteDialog = new PopularTimeDeleteDialog();
    expect(await popularTimeDeleteDialog.getDialogTitle()).to.eq('paradineApp.popularTime.delete.question');
    await popularTimeDeleteDialog.clickOnConfirmButton();

    expect(await popularTimeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
