package th.pt.black_tea.wrappers.fields;

import etda.uncefact.data.standard.qualifieddatatype._1.Max140TextType;

public class Max140Wrapper {

    public static Max140TextType text(String text){
        Max140TextType max140 = new Max140TextType();
        max140.setValue(text);
        return max140;
    }
}
