package projectFiles.entity;

public class Product extends BaseEntity {
    private String name;
    private Integer price;
    private Integer prodQuantity;
    private String prodInfo;

    /**
     * Constructor that takes product id, product name, product price, quantity of product
     * and additional information about product
     *
     * @param id           the product id
     * @param name         the product name
     * @param price        the price of product
     * @param prodQuantity the quantity of product
     * @param prodInfo     the additional information about the product
     */
    public Product(Integer id, String name, Integer price, Integer prodQuantity, String prodInfo) {
        super(id);
        this.name = name;
        this.price = price;
        this.prodQuantity = prodQuantity;
        this.prodInfo = prodInfo;
    }

    /**
     * Getter for private field prodQuantity
     *
     * @return quantity of product
     */
    public Integer getProdQuantity() {
        return prodQuantity;
    }

    /**
     * Setter for private field prodQuantity
     *
     * @param prodQuantity
     */
    public void setProdQuantity(Integer prodQuantity) {
        this.prodQuantity = prodQuantity;
    }

    /**
     * Getter for private field prodInfo
     *
     * @return information about the product
     */
    public String getProdInfo() {
        return prodInfo;
    }

    /**
     * Setter for private field prodInfo
     *
     * @param prodInfo information about the product
     */
    public void setProdInfo(String prodInfo) {
        this.prodInfo = prodInfo;
    }

    /**
     * Getter for private field name
     *
     * @return name of product
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for private field name
     *
     * @param name of product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for private field price
     *
     * @return the price of product
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Setter for private field price
     *
     * @param price the price of product
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * Function for interpreting a class object as a string
     *
     * @return the information string about the object
     */
    @Override
    public String toString() {
        return "{Product:id=" + getId() +
                ",prodName=" + getName() +
                ",price=" + getPrice() +
                ",prodQuantity=" + getProdQuantity() +
                ",prodInfo=" + getProdInfo() + '}';
    }
}
