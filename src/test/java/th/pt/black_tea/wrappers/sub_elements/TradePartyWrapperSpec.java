package th.pt.black_tea.wrappers.sub_elements;

import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.TradePartyType;
import org.junit.Test;

public class TradePartyWrapperSpec {

    @Test
    public void generateCorrectTradeParty() {
        String name = "Avenger Corp";
        TradePartyWrapper wrapper = TradePartyWrapper.getInstance();

        TradePartyType result = wrapper.getElement(name, "12345567");

        assert (result.getName().getValue().equals(name));
    }
}
