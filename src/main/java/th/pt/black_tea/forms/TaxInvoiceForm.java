package th.pt.black_tea.forms;

import java.util.List;

public class TaxInvoiceForm {

    public TaxInvoiceForm(String number
            , CorporateForm seller
            , CorporateForm buyer
            , List<ItemForm> items) {
        this.number = number;
        this.seller = seller;
        this.buyer = buyer;
        this.items = items;
    }

    public String number;

    public CorporateForm seller;

    public CorporateForm buyer;

    public List<ItemForm> items;
}
