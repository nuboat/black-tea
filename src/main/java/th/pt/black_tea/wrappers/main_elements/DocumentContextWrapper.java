package th.pt.black_tea.wrappers.main_elements;

import etda.uncefact.data.standard.qualifieddatatype._1.Max35IDType;
import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.DocumentContextParameterType;
import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.ExchangedDocumentContextType;

public class DocumentContextWrapper {

    private static DocumentContextWrapper wrapper = null;

    private ExchangedDocumentContextType element = new ExchangedDocumentContextType();

    private DocumentContextWrapper() {
        Max35IDType max35IDType = new Max35IDType();
        max35IDType.setValue("ER3-2560");
        max35IDType.setSchemeAgencyID("ETDA");
        max35IDType.setSchemeVersionID("v2.0");

        DocumentContextParameterType docContext = new DocumentContextParameterType();
        docContext.setID(max35IDType);

        element.setGuidelineSpecifiedDocumentContextParameter(docContext);
    }

    public static DocumentContextWrapper getInstance() {
        if (wrapper == null)
            wrapper = new DocumentContextWrapper();
        return wrapper;
    }

    public ExchangedDocumentContextType getDefault() {
        return element;
    }
}
