// package untitled;
import java.io.Serializable;
import java.util.HashMap;

public class Category implements Serializable {
    public static void setCurrentCateId(Integer currentCateId) {
        Category.currentCateId = currentCateId;
    }

    private static Integer currentCateId=0;
    private String cateId;
    private String cateName;
    private HashMap<String,Product> productOfCategory = new HashMap<>();
    public Category(String cateName) {
        if(currentCateId == null){
            currentCateId=0;
        }
        this.cateId = String.valueOf(currentCateId);
        this.cateName = cateName;

        currentCateId++;
        Data.currentID.put("category",currentCateId);
    }
    public void setCurrentCateId(int currentCateId) {
        Category.currentCateId = currentCateId;
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
