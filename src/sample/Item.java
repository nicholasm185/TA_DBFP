package sample;

public class Item {

    private int itemID;
    private String productName;
    private int qty;
    private int sub_total;

    public Item(int itemID, String productName, int qty, int sub_total) {
        this.itemID = itemID;
        this.productName = productName;
        this.qty = qty;
        this.sub_total = sub_total;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getSub_total() {
        return sub_total;
    }

    public void setSub_total(int sub_total) {
        this.sub_total = sub_total;
    }
}
