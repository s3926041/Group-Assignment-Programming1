// package untitled;
import java.io.*;

public class Product implements Serializable {
    public static void setCurrentProductId(Integer currentProductId) {
        Product.currentProductId = currentProductId;
    }

    private static Integer currentProductId=0;
    public Product(String name, Double price, String cateId) {
        this.name = name.toUpperCase();
        this.price = price;
        this.cateId = cateId;
        this.pId = String.valueOf(currentProductId);
        currentProductId++;
        Data.currentID.put("product",currentProductId);
        Data.allCategory.get(cateId).addProduct(this);
    }
    private Double price;
    private String name;
    private String pId;
    private String cateId;
    public void setCurrentProductId(int currentProductId) {
        Product.currentProductId = currentProductId;
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
        System.out.println("Id: "+this.pId);
        System.out.println("Name: "+this.name);
        System.out.println("Price: "+ this.price);
        System.out.println("Category: "+Data.allCategory.get(this.cateId).getCateName());
    }
}
