package th.pt.black_tea.wrappers.fields;

import etda.uncefact.data.standard.qualifieddatatype._1.Max35IDType;
import etda.uncefact.data.standard.qualifieddatatype._1.Max35TextType;

public class Max35Wrapper {

    public static Max35IDType id(String id) {
        Max35IDType max35 = new Max35IDType();
        max35.setValue(id);
        return max35;
    }

    public static Max35IDType id(String id, String schemeId) {
        Max35IDType max35 = new Max35IDType();
        max35.setValue(id);
        max35.setSchemeID(schemeId);
        return max35;
    }

    public static Max35TextType text(String text) {
        Max35TextType max35 = new Max35TextType();
        max35.setValue(text);
        return max35;
    }
}
