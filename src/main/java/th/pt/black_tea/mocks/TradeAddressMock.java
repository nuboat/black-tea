package th.pt.black_tea.mocks;

import etda.uncefact.data.standard.qualifieddatatype._1.CountryIDType;
import etda.uncefact.data.standard.qualifieddatatype._1.TISI1099CityName;
import etda.uncefact.data.standard.qualifieddatatype._1.TISI1099CitySubDivisionName;
import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.TradeAddressType;
import th.pt.black_tea.wrappers.fields.Max16Wrapper;
import th.pt.black_tea.wrappers.fields.Max35Wrapper;
import th.pt.black_tea.wrappers.fields.Max70Wrapper;
import un.unece.uncefact.identifierlist.standard.iso.isotwo_lettercountrycode.secondedition2006.ISOTwoletterCountryCodeContentType;

public class TradeAddressMock {

    public static TradeAddressType mockSellerAddress() {
        TISI1099CitySubDivisionName citySubDivisionName = new TISI1099CitySubDivisionName();
        citySubDivisionName.setValue("101804");
        TISI1099CityName cityName = new TISI1099CityName();
        cityName.setValue("1018");

        CountryIDType country = new CountryIDType();
        country.setValue(ISOTwoletterCountryCodeContentType.TH);
        country.setSchemeID("3166-1 alpha-2");

        TradeAddressType address = new TradeAddressType();
        address.setBuildingNumber(Max16Wrapper.text("16/217"));
        address.setStreetName(Max70Wrapper.text("ถ. กรุงธนบุรี"));
        address.setCitySubDivisionName(citySubDivisionName);
        address.setCityName(cityName);
        address.setCountrySubDivisionID(Max35Wrapper.id("10"));
        address.setCountryID(country);
        address.setPostcodeCode(Max16Wrapper.code("10600"));

        return address;
    }

    public static TradeAddressType mockBuyerAddress() {
        TISI1099CitySubDivisionName citySubDivisionName = new TISI1099CitySubDivisionName();
        citySubDivisionName.setValue("103001");
        TISI1099CityName cityName = new TISI1099CityName();
        cityName.setValue("1030");

        CountryIDType country = new CountryIDType();
        country.setValue(ISOTwoletterCountryCodeContentType.TH);
        country.setSchemeID("3166-1 alpha-2");

        TradeAddressType address = new TradeAddressType();
        address.setBuildingNumber(Max16Wrapper.text("214"));
        address.setStreetName(Max70Wrapper.text("ถ. วิภาวดี"));
        address.setCitySubDivisionName(citySubDivisionName);
        address.setCityName(cityName);
        address.setCountrySubDivisionID(Max35Wrapper.id("10"));
        address.setCountryID(country);
        address.setPostcodeCode(Max16Wrapper.code("10900"));

        return address;
    }
}
