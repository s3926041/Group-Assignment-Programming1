// package untitled;
import java.io.*;

public class Product implements Serializable {
    private static int totalProduct;
    public Product(String name, Double price, String cateId) {
        this.name = name.toUpperCase();
        this.pId = String.valueOf(totalProduct);
        this.price = price;
        this.cateId = cateId;
        Data.allCategory.get(cateId).addProduct(this);
        totalProduct++;
    }
    private Double price;
    private String name;
    private String pId;
    private String cateId;
    public static void setTotalProduct(int totalProduct) {
        Product.totalProduct = totalProduct;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getName() {
        return this.name;
    }
    public String getpId() {
        return this.pId;
    }
    public Double getPrice() {
        return this.price;
    }


    public void getInformation() {
        System.out.printf("ID: %3s | Name: %9s | Price: %9.0f | Category ID: %3s \n",this.pId,this.name,this.price,this.cateId);
    }
}
