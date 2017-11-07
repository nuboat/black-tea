package th.pt.black_tea.wrappers.main_elements;

import etda.uncefact.data.standard.taxinvoice_crossindustryinvoice._2.TaxInvoiceCrossIndustryInvoiceType;
import org.junit.Test;
import th.pt.black_tea.utilities.TaxInvoiceXMLGenerator;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class ExchangedDocumentWrapperSpec {

    @Test
    public void generateCorrectExchangedDocument() {
        String id = "111";
        TaxInvoiceXMLGenerator generator = TaxInvoiceXMLGenerator.getInstance();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ExchangedDocumentWrapper wrapper = ExchangedDocumentWrapper.getInstance();
        TaxInvoiceCrossIndustryInvoiceType taxInvoice = new TaxInvoiceCrossIndustryInvoiceType();
        taxInvoice.setExchangedDocument(wrapper.getTaxInvoice(id, new Date()));

        generator.generate(taxInvoice, stream);
        String result = new String(stream.toByteArray());

        assert (result.contains(id));

    }
}
