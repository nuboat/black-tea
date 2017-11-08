package th.pt.black_tea.wrappers.sub_elements;

import etda.uncefact.data.standard.qualifieddatatype._1.AmountType;
import etda.uncefact.data.standard.qualifieddatatype._1.QuantityType;
import etda.uncefact.data.standard.taxinvoice_reusableaggregatebusinessinformationentity._2.*;
import th.pt.black_tea.wrappers.fields.Max256Wrapper;
import th.pt.black_tea.wrappers.fields.Max35Wrapper;

import java.math.BigDecimal;

public class SupplyChainItemWrapper {

    private static SupplyChainItemWrapper wrapper = null;

    private SupplyChainItemWrapper() {

    }

    public static SupplyChainItemWrapper getInstance() {
        if (wrapper == null)
            wrapper = new SupplyChainItemWrapper();
        return wrapper;
    }

    public SupplyChainTradeLineItemType getElement(String name, double amount, double quantity) {
        SupplyChainTradeLineItemType item = new SupplyChainTradeLineItemType();
        item.setAssociatedDocumentLineDocument(getLine());
        item.setSpecifiedTradeProduct(getProduct(name));
        item.setSpecifiedLineTradeAgreement(getAgreement(amount));
        item.setSpecifiedLineTradeDelivery(getDelivery(quantity));
        item.setSpecifiedLineTradeSettlement(getSettlement());

        return item;
    }

    private DocumentLineDocumentType getLine() {
        DocumentLineDocumentType line = new DocumentLineDocumentType();
        //TODO fix hard code
        line.setLineID(Max35Wrapper.id("1"));
        return line;
    }

    private TradeProductType getProduct(String name) {
        TradeProductType product = new TradeProductType();
        product.getName().add(Max256Wrapper.text(name));

        return product;
    }

    private LineTradeAgreementType getAgreement(double amount) {
        LineTradeAgreementType agreement = new LineTradeAgreementType();
        TradePriceType price = new TradePriceType();
        AmountType amountType = new AmountType();
        amountType.setValue(new BigDecimal(amount));
        price.getChargeAmount().add(amountType);
        agreement.setGrossPriceProductTradePrice(price);

        return agreement;
    }

    private LineTradeDeliveryType getDelivery(double quantity) {
        LineTradeDeliveryType delivery = new LineTradeDeliveryType();
        QuantityType quantityType = new QuantityType();
        quantityType.setUnitCode("คัน");
        quantityType.setValue(new BigDecimal(quantity));
        delivery.setBilledQuantity(quantityType);

        return delivery;
    }

    private LineTradeSettlementType getSettlement() {
        LineTradeSettlementType settlement = new LineTradeSettlementType();

        return settlement;
    }


}
