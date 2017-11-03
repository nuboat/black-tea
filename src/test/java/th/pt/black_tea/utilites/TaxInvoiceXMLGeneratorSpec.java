package th.pt.black_tea.utilites;

import etda.uncefact.data.standard.taxinvoice_crossindustryinvoice._2.TaxInvoiceCrossIndustryInvoiceType;
import org.junit.Test;
import th.pt.black_tea.utilities.TaxInvoiceXMLGenerator;

import java.io.ByteArrayOutputStream;

public class TaxInvoiceXMLGeneratorSpec {

    @Test
    public void canGenerateTaxInvoiceXML() {
        TaxInvoiceXMLGenerator generator = TaxInvoiceXMLGenerator.getInstance();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        generator.generate(new TaxInvoiceCrossIndustryInvoiceType(),stream);
        String result = new String(stream.toByteArray());

        assert(result.contains("etda"));
    }
}
