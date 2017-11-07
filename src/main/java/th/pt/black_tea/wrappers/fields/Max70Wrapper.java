package th.pt.black_tea.wrappers.fields;

import etda.uncefact.data.standard.qualifieddatatype._1.Max70IDType;
import etda.uncefact.data.standard.qualifieddatatype._1.Max70TextType;

public class Max70Wrapper {

    public static Max70IDType id(String id) {
        Max70IDType max70 = new Max70IDType();
        max70.setValue(id);
        return max70;
    }

    public static Max70TextType text(String text){
        Max70TextType max70 = new Max70TextType();
        max70.setValue(text);
        return max70;
    }
}
