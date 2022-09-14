
import java.io.Serializable;
import java.util.HashMap;

public class Category implements Serializable {
    public static void setCurrentCateId(Integer currentCateId) {
        Category.currentCateId = currentCateId;
    }
    private static Integer currentCateId=0;
    private String cateId;
    private String cateName;
    private final HashMap<String,Product> productOfCategory = new HashMap<>();
    public Category(String cateName) {
        this.cateId = String.valueOf(currentCateId);
        this.cateName = cateName;
        currentCateId++;
        Data.currentID.put("category",currentCateId);
        Data.allCategory.put(this.cateId,this);
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
