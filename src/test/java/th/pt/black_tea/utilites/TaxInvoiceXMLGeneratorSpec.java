package th.pt.black_tea.utilites;

import etda.uncefact.data.standard.taxinvoice_crossindustryinvoice._2.TaxInvoiceCrossIndustryInvoiceType;
import org.junit.Test;
import th.pt.black_tea.utilities.TaxInvoiceXMLGenerator;
import th.pt.black_tea.wrappers.main_elements.ExchangedDocumentContextWrapper;
import th.pt.black_tea.wrappers.main_elements.ExchangedDocumentWrapper;

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
        taxInvoice.setExchangedDocument(wrapper.getTaxInvoice("TIV-00125", new Date()));
        taxInvoice.setExchangedDocumentContext(docContext.getDefault());

        generator.generate(taxInvoice,stream);
        String result = new String(stream.toByteArray());

        assert(result.contains("etda"));
    }
}
