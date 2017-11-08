package th.pt.black_tea.wrappers;

import org.junit.Test;
import th.pt.black_tea.forms.CorporateForm;
import th.pt.black_tea.forms.ItemForm;
import th.pt.black_tea.forms.TaxInvoiceForm;

import java.util.ArrayList;
import java.util.List;

public class TaxInvoiceWrapperSpec {

    @Test
    public void canGenerateTaxInvoice() {
        TaxInvoiceWrapper wrapper = TaxInvoiceWrapper.getInstance();
        List<ItemForm> items = new ArrayList<ItemForm>();
        items.add(new ItemForm("รถคันแรก", 2000000.00, 1.00));

        TaxInvoiceForm form = new TaxInvoiceForm("INV-001"
                , new CorporateForm("Avenger Corp", "001")
                , new CorporateForm("Justice League Corp", "002")
                , items
        );

        String result = wrapper.create(form);

        assert (result.contains("รถคันแรก"));
    }

}
