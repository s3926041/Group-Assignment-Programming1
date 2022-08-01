// package untitled;
import java.io.Serializable;
import java.util.HashMap;

public class Category implements Serializable {
    private static int totalCate;
    private String cateId;
    private String cateName;
    private HashMap<String,Product> productOfCategory = new HashMap<>();
    public Category(String cateName) {
        this.cateId = String.valueOf(totalCate);
        this.cateName = cateName;
        totalCate++;
    }
    public static void setTotalCate(int totalCate) {
        Category.totalCate = totalCate;
    }
    public String getCateId() {
        return cateId;
    }
    public String getCateName() {
        return cateName;
    }
    public HashMap<String, Product> getProductOfCategory() {
        return this.productOfCategory;
    }
    public void addProduct(Product p) { this.productOfCategory.put(p.getpId(),p); }

}
