package th.pt.black_tea.utilities;

import etda.uncefact.data.standard.taxinvoice_crossindustryinvoice._2.ObjectFactory;
import etda.uncefact.data.standard.taxinvoice_crossindustryinvoice._2.TaxInvoiceCrossIndustryInvoiceType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.OutputStream;

public class TaxInvoiceXMLGenerator {
    private ObjectFactory factory = new ObjectFactory();


    private static TaxInvoiceXMLGenerator generator = null;

    private TaxInvoiceXMLGenerator() {
    }

    public static TaxInvoiceXMLGenerator getInstance() {
        if (generator == null)
            generator = new TaxInvoiceXMLGenerator();
        return generator;
    }

    public OutputStream generate(TaxInvoiceCrossIndustryInvoiceType taxInvoice, OutputStream stream) {
        JAXBElement<TaxInvoiceCrossIndustryInvoiceType> jaxB = factory.createTaxInvoiceCrossIndustryInvoice(taxInvoice);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TaxInvoiceCrossIndustryInvoiceType.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(jaxB, stream);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return stream;
    }

}
