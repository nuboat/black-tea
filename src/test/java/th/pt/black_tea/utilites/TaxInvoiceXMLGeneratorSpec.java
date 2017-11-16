package th.pt.black_tea.utilites;

import etda.uncefact.data.standard.taxinvoice_crossindustryinvoice._2.TaxInvoiceCrossIndustryInvoiceType;
import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.HeaderTradeAgreementType;
import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.SupplyChainTradeTransactionType;
import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.TradePartyType;
import org.junit.Test;
import th.pt.black_tea.mocks.TradeAddressMock;
import th.pt.black_tea.utilities.TaxInvoiceXMLGenerator;
import th.pt.black_tea.wrappers.main_elements.ExchangedDocumentContextWrapper;
import th.pt.black_tea.wrappers.main_elements.ExchangedDocumentWrapper;
import th.pt.black_tea.wrappers.main_elements.SupplyChainTradeTransactionWrapper;
import th.pt.black_tea.wrappers.sub_elements.SupplyChainItemWrapper;
import th.pt.black_tea.wrappers.sub_elements.TradePartyWrapper;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class TaxInvoiceXMLGeneratorSpec {

    @Test
    public void canGenerateTaxInvoiceXML() {
        TaxInvoiceXMLGenerator generator = TaxInvoiceXMLGenerator.getInstance();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        generator.generate(new TaxInvoiceCrossIndustryInvoiceType(),stream);
        String result = new String(stream.toByteArray());

        assert(result.contains("etda"));
    }

    @Test
    public void canGenerateFullXML() {
        TaxInvoiceXMLGenerator generator = TaxInvoiceXMLGenerator.getInstance();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        TaxInvoiceCrossIndustryInvoiceType taxInvoice = new TaxInvoiceCrossIndustryInvoiceType();

        ExchangedDocumentContextWrapper docContext = ExchangedDocumentContextWrapper.getInstance();
        ExchangedDocumentWrapper wrapper = ExchangedDocumentWrapper.getInstance();

        SupplyChainTradeTransactionWrapper supWrapper = SupplyChainTradeTransactionWrapper.getInstance();
        SupplyChainTradeTransactionType chain = supWrapper.getDefault();

        HeaderTradeAgreementType agreement = new HeaderTradeAgreementType();
        TradePartyWrapper tradeWrapper = TradePartyWrapper.getInstance();
        TradePartyType buyer = tradeWrapper.getElement("Buyer Corp", "24524r45434", TradeAddressMock.mockBuyerAddress());
        TradePartyType seller = tradeWrapper.getElement("Seller Corp", "467456735473", TradeAddressMock.mockSellerAddress());

        agreement.setSellerTradeParty(seller);
        agreement.setBuyerTradeParty(buyer);

        SupplyChainItemWrapper itemWrapper = SupplyChainItemWrapper.getInstance();
        chain.setApplicableHeaderTradeAgreement(agreement);
        chain.getIncludedSupplyChainTradeLineItem().add(itemWrapper.getElement("รถคันแรก", 300.00, 1));

        taxInvoice.setSupplyChainTradeTransaction(chain);
        taxInvoice.setExchangedDocument(wrapper.getTaxInvoice("TIV-00125", new Date()));
        taxInvoice.setExchangedDocumentContext(docContext.getDefault());

        String result = generator.generate(taxInvoice,stream);

        assert(result.contains("etda"));
    }
}
