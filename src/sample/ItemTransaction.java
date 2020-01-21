package sample;

public class ItemTransaction {

    private int itemID;
    private int billID;
    private int productID;
    private int qty;

    public ItemTransaction(int itemID, int billID, int productID, int qty){
        this.itemID = itemID;
        this.billID = billID;
        this.productID = productID;
        this.qty = qty;
    }


    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }
}

