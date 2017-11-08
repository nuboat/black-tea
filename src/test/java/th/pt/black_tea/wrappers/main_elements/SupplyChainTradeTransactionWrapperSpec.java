package th.pt.black_tea.wrappers.main_elements;

import etda.uncefact.data.standard.taxinvoice_crossindustryinvoice._2.TaxInvoiceCrossIndustryInvoiceType;
import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.HeaderTradeAgreementType;
import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.SupplyChainTradeTransactionType;
import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.TradePartyType;
import org.junit.Test;
import th.pt.black_tea.mocks.TradeAddressMock;
import th.pt.black_tea.utilities.TaxInvoiceXMLGenerator;
import th.pt.black_tea.wrappers.sub_elements.TradePartyWrapper;

import java.io.ByteArrayOutputStream;

public class SupplyChainTradeTransactionWrapperSpec {

    @Test
    public void generateSupplyChainTradeTransaction() {
        TaxInvoiceXMLGenerator generator = TaxInvoiceXMLGenerator.getInstance();
        TaxInvoiceCrossIndustryInvoiceType taxInvoice = new TaxInvoiceCrossIndustryInvoiceType();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        SupplyChainTradeTransactionWrapper wrapper = SupplyChainTradeTransactionWrapper.getInstance();
        SupplyChainTradeTransactionType chain = wrapper.getDefault();

        HeaderTradeAgreementType agreement = new HeaderTradeAgreementType();
        TradePartyWrapper tradeWrapper = TradePartyWrapper.getInstance();
        TradePartyType buyer = tradeWrapper.getElement("Buyer Corp", "24524r45434", TradeAddressMock.mockBuyerAddress());
        TradePartyType seller = tradeWrapper.getElement("Seller Corp", "467456735473", TradeAddressMock.mockSellerAddress());

        agreement.setSellerTradeParty(seller);
        agreement.setBuyerTradeParty(buyer);

        chain.setApplicableHeaderTradeAgreement(agreement);
        taxInvoice.setSupplyChainTradeTransaction(chain);

        generator.generate(taxInvoice, stream);
        String result = new String(stream.toByteArray());

        assert (result.contains("1030"));
    }
}
