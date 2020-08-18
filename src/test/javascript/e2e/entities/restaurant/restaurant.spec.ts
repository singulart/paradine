import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RestaurantComponentsPage, RestaurantDeleteDialog, RestaurantUpdatePage } from './restaurant.page-object';

const expect = chai.expect;

describe('Restaurant e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let restaurantComponentsPage: RestaurantComponentsPage;
  let restaurantUpdatePage: RestaurantUpdatePage;
  let restaurantDeleteDialog: RestaurantDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Restaurants', async () => {
    await navBarPage.goToEntity('restaurant');
    restaurantComponentsPage = new RestaurantComponentsPage();
    await browser.wait(ec.visibilityOf(restaurantComponentsPage.title), 5000);
    expect(await restaurantComponentsPage.getTitle()).to.eq('paradineApp.restaurant.home.title');
    await browser.wait(ec.or(ec.visibilityOf(restaurantComponentsPage.entities), ec.visibilityOf(restaurantComponentsPage.noResult)), 1000);
  });

  it('should load create Restaurant page', async () => {
    await restaurantComponentsPage.clickOnCreateButton();
    restaurantUpdatePage = new RestaurantUpdatePage();
    expect(await restaurantUpdatePage.getPageTitle()).to.eq('paradineApp.restaurant.home.createOrEditLabel');
    await restaurantUpdatePage.cancel();
  });

  it('should create and save Restaurants', async () => {
    const nbButtonsBeforeCreate = await restaurantComponentsPage.countDeleteButtons();

    await restaurantComponentsPage.clickOnCreateButton();

    await promise.all([
      restaurantUpdatePage.setNameInput('name'),
      restaurantUpdatePage.setAltName1Input('altName1'),
      restaurantUpdatePage.setAddressEnInput('addressEn'),
      restaurantUpdatePage.setAddressRuInput('addressRu'),
      restaurantUpdatePage.setAddressUaInput('addressUa'),
      restaurantUpdatePage.setGooglePlacesIdInput('googlePlacesId'),
      restaurantUpdatePage.setGeolatInput('5'),
      restaurantUpdatePage.setGeolngInput('5'),
      restaurantUpdatePage.setPhotoUrlInput('v5cVG/hvQdgCBSHPyN5P.hFzt2OEG=XpfX6jY?TQ=C/%jkj+E(.wrqbum'),
      restaurantUpdatePage.setAltName2Input('altName2'),
      restaurantUpdatePage.setAltName3Input('altName3'),
      restaurantUpdatePage.setCapacityInput('5'),
      restaurantUpdatePage.setCreatedAtInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      restaurantUpdatePage.setUpdatedAtInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      restaurantUpdatePage.setUuidInput('9dE1b5Ab-FA8F-0Fb6-Fc3A-C6Eb86DeD48b'),
      restaurantUpdatePage.citySelectLastOption(),
    ]);

    expect(await restaurantUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await restaurantUpdatePage.getAltName1Input()).to.eq('altName1', 'Expected AltName1 value to be equals to altName1');
    expect(await restaurantUpdatePage.getAddressEnInput()).to.eq('addressEn', 'Expected AddressEn value to be equals to addressEn');
    expect(await restaurantUpdatePage.getAddressRuInput()).to.eq('addressRu', 'Expected AddressRu value to be equals to addressRu');
    expect(await restaurantUpdatePage.getAddressUaInput()).to.eq('addressUa', 'Expected AddressUa value to be equals to addressUa');
    expect(await restaurantUpdatePage.getGooglePlacesIdInput()).to.eq(
      'googlePlacesId',
      'Expected GooglePlacesId value to be equals to googlePlacesId'
    );
    expect(await restaurantUpdatePage.getGeolatInput()).to.eq('5', 'Expected geolat value to be equals to 5');
    expect(await restaurantUpdatePage.getGeolngInput()).to.eq('5', 'Expected geolng value to be equals to 5');
    expect(await restaurantUpdatePage.getPhotoUrlInput()).to.eq(
      'v5cVG/hvQdgCBSHPyN5P.hFzt2OEG=XpfX6jY?TQ=C/%jkj+E(.wrqbum',
      'Expected PhotoUrl value to be equals to v5cVG/hvQdgCBSHPyN5P.hFzt2OEG=XpfX6jY?TQ=C/%jkj+E(.wrqbum'
    );
    expect(await restaurantUpdatePage.getAltName2Input()).to.eq('altName2', 'Expected AltName2 value to be equals to altName2');
    expect(await restaurantUpdatePage.getAltName3Input()).to.eq('altName3', 'Expected AltName3 value to be equals to altName3');
    expect(await restaurantUpdatePage.getCapacityInput()).to.eq('5', 'Expected capacity value to be equals to 5');
    expect(await restaurantUpdatePage.getCreatedAtInput()).to.contain(
      '2001-01-01T02:30',
      'Expected createdAt value to be equals to 2000-12-31'
    );
    expect(await restaurantUpdatePage.getUpdatedAtInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updatedAt value to be equals to 2000-12-31'
    );
    expect(await restaurantUpdatePage.getUuidInput()).to.eq(
      '9dE1b5Ab-FA8F-0Fb6-Fc3A-C6Eb86DeD48b',
      'Expected Uuid value to be equals to 9dE1b5Ab-FA8F-0Fb6-Fc3A-C6Eb86DeD48b'
    );

    await restaurantUpdatePage.save();
    expect(await restaurantUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await restaurantComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Restaurant', async () => {
    const nbButtonsBeforeDelete = await restaurantComponentsPage.countDeleteButtons();
    await restaurantComponentsPage.clickOnLastDeleteButton();

    restaurantDeleteDialog = new RestaurantDeleteDialog();
    expect(await restaurantDeleteDialog.getDialogTitle()).to.eq('paradineApp.restaurant.delete.question');
    await restaurantDeleteDialog.clickOnConfirmButton();

    expect(await restaurantComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
