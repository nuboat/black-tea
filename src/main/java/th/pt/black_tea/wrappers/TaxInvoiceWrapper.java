package th.pt.black_tea.wrappers;

import etda.uncefact.data.standard.taxinvoice_crossindustryinvoice._2.TaxInvoiceCrossIndustryInvoiceType;
import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.HeaderTradeAgreementType;
import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.SupplyChainTradeTransactionType;
import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.TradePartyType;
import th.pt.black_tea.forms.ItemForm;
import th.pt.black_tea.forms.TaxInvoiceForm;
import th.pt.black_tea.mocks.TradeAddressMock;
import th.pt.black_tea.utilities.TaxInvoiceXMLGenerator;
import th.pt.black_tea.wrappers.main_elements.ExchangedDocumentContextWrapper;
import th.pt.black_tea.wrappers.main_elements.ExchangedDocumentWrapper;
import th.pt.black_tea.wrappers.main_elements.SupplyChainTradeTransactionWrapper;
import th.pt.black_tea.wrappers.sub_elements.SupplyChainItemWrapper;
import th.pt.black_tea.wrappers.sub_elements.TradePartyWrapper;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class TaxInvoiceWrapper {

    private static TaxInvoiceWrapper wrapper = null;

    private TaxInvoiceWrapper() {

    }

    public static TaxInvoiceWrapper getInstance() {
        if (wrapper == null)
            wrapper = new TaxInvoiceWrapper();
        return wrapper;
    }

    public String create(TaxInvoiceForm form) {
        TaxInvoiceXMLGenerator generator = TaxInvoiceXMLGenerator.getInstance();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        TaxInvoiceCrossIndustryInvoiceType taxInvoice = new TaxInvoiceCrossIndustryInvoiceType();

        ExchangedDocumentContextWrapper docContext = ExchangedDocumentContextWrapper.getInstance();
        ExchangedDocumentWrapper wrapper = ExchangedDocumentWrapper.getInstance();

        SupplyChainTradeTransactionWrapper supWrapper = SupplyChainTradeTransactionWrapper.getInstance();
        SupplyChainTradeTransactionType chain = supWrapper.getDefault();

        HeaderTradeAgreementType agreement = new HeaderTradeAgreementType();
        TradePartyWrapper tradeWrapper = TradePartyWrapper.getInstance();
        TradePartyType buyer = tradeWrapper.getElement(form.buyer.name, form.buyer.taxId, TradeAddressMock.mockBuyerAddress());
        TradePartyType seller = tradeWrapper.getElement(form.seller.name, form.seller.taxId, TradeAddressMock.mockSellerAddress());

        agreement.setSellerTradeParty(seller);
        agreement.setBuyerTradeParty(buyer);

        SupplyChainItemWrapper itemWrapper = SupplyChainItemWrapper.getInstance();
        chain.setApplicableHeaderTradeAgreement(agreement);

        for (ItemForm item : form.items) {
            chain.getIncludedSupplyChainTradeLineItem().add(itemWrapper.getElement(item.name, item.amount, item.quantity));
        }

        taxInvoice.setSupplyChainTradeTransaction(chain);
        taxInvoice.setExchangedDocument(wrapper.getTaxInvoice(form.number, new Date()));
        taxInvoice.setExchangedDocumentContext(docContext.getDefault());

        generator.generate(taxInvoice, stream);
        String result = new String(stream.toByteArray());

        return result;
    }
}
