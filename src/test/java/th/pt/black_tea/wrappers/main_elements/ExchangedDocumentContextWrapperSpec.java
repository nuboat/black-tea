package th.pt.black_tea.wrappers.main_elements;

import etda.uncefact.data.standard.taxinvoice_crossindustryinvoice._2.TaxInvoiceCrossIndustryInvoiceType;
import org.junit.Test;
import th.pt.black_tea.utilities.TaxInvoiceXMLGenerator;

import java.io.ByteArrayOutputStream;

public class ExchangedDocumentContextWrapperSpec {

    @Test
    public void generateCorrectDocumentContext() {
        TaxInvoiceXMLGenerator generator = TaxInvoiceXMLGenerator.getInstance();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ExchangedDocumentContextWrapper docContext = ExchangedDocumentContextWrapper.getInstance();
        TaxInvoiceCrossIndustryInvoiceType taxInvoice = new TaxInvoiceCrossIndustryInvoiceType();
        taxInvoice.setExchangedDocumentContext(docContext.getDefault());

        generator.generate(taxInvoice, stream);
        String result = new String(stream.toByteArray());

        assert (result.contains("schemeAgencyID=\"ETDA\""));
        assert (result.contains("ER3-2560"));
    }

}