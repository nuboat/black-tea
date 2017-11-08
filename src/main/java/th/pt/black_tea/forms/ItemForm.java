package th.pt.black_tea.forms;

public class ItemForm {

    public ItemForm(String name
            , double amount
            , double quantity) {
        this.name = name;
        this.amount = amount;
        this.quantity = quantity;
    }

    public String name;

    public double amount;

    public double quantity;
}
