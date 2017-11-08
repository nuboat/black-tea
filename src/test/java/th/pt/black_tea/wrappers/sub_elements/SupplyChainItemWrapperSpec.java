package th.pt.black_tea.wrappers.sub_elements;

import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.SupplyChainTradeLineItemType;
import org.junit.Test;

public class SupplyChainItemWrapperSpec {

    @Test
    public void generateCorrectSupplyChainItem() {
        String name = "รถคันแรก";
        SupplyChainItemWrapper wrapper = SupplyChainItemWrapper.getInstance();

        SupplyChainTradeLineItemType result = wrapper.getElement(name, 300.00, 2);

        assert (result.getSpecifiedTradeProduct().getName().get(0).getValue().equals(name));
    }
}
