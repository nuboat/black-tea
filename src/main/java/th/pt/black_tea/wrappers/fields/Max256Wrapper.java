package th.pt.black_tea.wrappers.fields;

import etda.uncefact.data.standard.qualifieddatatype._1.Max256IDType;
import etda.uncefact.data.standard.qualifieddatatype._1.Max256TextType;

public class Max256Wrapper {

    public static Max256IDType id(String id) {
        Max256IDType max256 = new Max256IDType();
        max256.setValue(id);
        return max256;
    }

    public static Max256TextType text(String text) {
        Max256TextType max256 = new Max256TextType();
        max256.setValue(text);
        return max256;
    }
}
