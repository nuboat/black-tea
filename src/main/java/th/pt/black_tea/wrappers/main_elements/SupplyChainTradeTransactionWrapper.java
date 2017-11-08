package th.pt.black_tea.wrappers.main_elements;

import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.SupplyChainTradeTransactionType;

public class SupplyChainTradeTransactionWrapper {

    private static SupplyChainTradeTransactionWrapper wrapper = null;

    private SupplyChainTradeTransactionWrapper() {

    }

    public static SupplyChainTradeTransactionWrapper getInstance() {
        if (wrapper == null)
            wrapper = new SupplyChainTradeTransactionWrapper();
        return wrapper;
    }

    public SupplyChainTradeTransactionType getDefault() {
        return new SupplyChainTradeTransactionType();
    }
}
