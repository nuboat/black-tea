package th.pt.black_tea.wrappers.sub_elements;

import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.TaxRegistrationType;
import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.TradeAddressType;
import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.TradePartyType;
import th.pt.black_tea.wrappers.fields.Max256Wrapper;
import th.pt.black_tea.wrappers.fields.Max35Wrapper;

public class TradePartyWrapper {

    private static TradePartyWrapper wrapper = null;

    private TradePartyWrapper() {

    }

    public static TradePartyWrapper getInstance() {
        if (wrapper == null)
            wrapper = new TradePartyWrapper();
        return wrapper;
    }

    public TradePartyType getElement(String name, String taxId, TradeAddressType address) {
        TradePartyType tradeParty = new TradePartyType();
        tradeParty.setName(Max256Wrapper.text(name));
        tradeParty.setSpecifiedTaxRegistration(parseTaxId(taxId));
        tradeParty.setPostalTradeAddress(address);
        return tradeParty;
    }

    private TaxRegistrationType parseTaxId(String taxId) {
        TaxRegistrationType taxRegistration = new TaxRegistrationType();
        taxRegistration.setID(Max35Wrapper.id(taxId, "TXID"));

        return taxRegistration;
    }

}
