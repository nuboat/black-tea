package th.pt.black_tea.wrappers.fields;

import etda.uncefact.data.standard.qualifieddatatype._1.Max16CodeType;
import etda.uncefact.data.standard.qualifieddatatype._1.Max16TextType;

public class Max16Wrapper {

    public static Max16CodeType code(String code){
        Max16CodeType max16 = new Max16CodeType();
        max16.setValue(code);
        return max16;
    }

    public static Max16TextType text(String text){
        Max16TextType max16 = new Max16TextType();
        max16.setValue(text);
        return max16;
    }
}
