package th.pt.black_tea.wrappers.main_elements;

import etda.uncefact.data.standard.qualifieddatatype._1.ThaiInvoiceDocumentCodeType;
import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.ExchangedDocumentType;
import th.pt.black_tea.wrappers.fields.Max35Wrapper;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ExchangedDocumentWrapper {

    private static ExchangedDocumentWrapper wrapper = null;

    //TODO wrap as Enum
    private static String TAX_INVOICE = "ใบกำกับภาษี";
    private static String RECEIPT_TAX_INVOICE = "ใบเสร็จรับเงิน/ใบกากับภาษี";

    private ExchangedDocumentWrapper() {
    }

    public static ExchangedDocumentWrapper getInstance() {
        if (wrapper == null)
            wrapper = new ExchangedDocumentWrapper();
        return wrapper;
    }

    public ExchangedDocumentType getTaxInvoice(String id, Date issueDate) {
        ExchangedDocumentType element = new ExchangedDocumentType();

        element.setID(Max35Wrapper.id(id));
        element.setName(Max35Wrapper.text(TAX_INVOICE));
        element.setTypeCode(mapTypeCode(TAX_INVOICE));
        element.setIssueDateTime(gregorianCalendar(issueDate));
        element.setCreationDateTime(gregorianCalendar(new Date()));

        return element;
    }

    private XMLGregorianCalendar gregorianCalendar(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        DatatypeFactory datatypeFactory = null;
        try {
            datatypeFactory = DatatypeFactory.newInstance();

        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }

        return datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
    }

    private ThaiInvoiceDocumentCodeType mapTypeCode(String name) {
        ThaiInvoiceDocumentCodeType codeType = new ThaiInvoiceDocumentCodeType();
        if (name.equals(TAX_INVOICE)) {
            codeType.setValue("388");
        } else if (name.equals(RECEIPT_TAX_INVOICE)) {
            codeType.setValue("T03");
        }
        return codeType;
    }

}
