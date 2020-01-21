package sample;

public class ItemTransactionCart {
    private int productID;
    private String productName;
    private int productPrice;
    private int qty;
    private int subtotal;

    public ItemTransactionCart(int productID, String productName, int productPrice, int qty, int subtotal) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.qty = qty;
        this.subtotal = subtotal;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
