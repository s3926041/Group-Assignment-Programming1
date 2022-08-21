import java.io.*;
import java.util.HashMap;

public class Data  {
    @SuppressWarnings("unchecked")
    static HashMap<String,Member> allMember = new HashMap<>(); //USERNAME AND MEMBER
    static HashMap<String,Member> allMemberByID = new HashMap<>(); //ID AND MEMBER //COPY OF ALLMEMBER
    static HashMap<String, Order> allOrder = new HashMap<>(); //OID AND ORDER
    static HashMap<String, Category> allCategory = new HashMap<>();//CATE ID AND CATEGORY
    static HashMap<String, Product> allProduct = new HashMap<>(); //pID AND PRODUCT
    static <O> HashMap<String,O> readFile(String file) throws IOException, ClassNotFoundException {
        try{
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois  = new ObjectInputStream(fis);
            HashMap<String, O> ar = (HashMap<String, O>) ois.readObject();
            ois.close();
            return ar;
        } catch (FileNotFoundException e){
            return null;
        }
    }
    static <O> void writeFile(HashMap<String,O> ar,String file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos  = new ObjectOutputStream(fos);
        oos.writeObject(ar);
        oos.close();
    }

    static void read() throws IOException, ClassNotFoundException {
        allMember = readFile("member.obj");
        allCategory  =readFile("category.obj");
        allProduct = readFile("product.obj");
        allOrder = readFile("order.obj");
        Member.setTotalUser(allMember == null? 0 : allMember.size());
        Product.setTotalProduct(allProduct == null? 0 : allProduct.size());
        Order.setTotalOrder(allOrder == null ? 0: allOrder.size());
        Category.setTotalCate(allCategory == null? 0  : allCategory.size());
        allMemberByID =new HashMap<>();
        for(String i : allMember.keySet()) {
            allMemberByID.put(allMember.get(i).getUserId(), allMember.get(i));
        }
    }
    static void write() throws IOException {
        writeFile(allMember,"member.obj");
        writeFile(allOrder,"order.obj");
        writeFile(allCategory,"category.obj");
        writeFile(allProduct,"product.obj");
    }

//    static void createSampleData() throws IOException {
//        Member admin = new Member("admin","root","");
//        admin.setRole(2);
//        allMember.put(admin.getUsername(),admin);
//        Member member = new Member("hungcpui","puiabc12","0889265054");
//        allMember.put(member.getUsername(),member);
//        Category c1 = new Category("laptop");
//        allCategory.put(c1.getCateId(),c1);
//        Category c2 = new Category("mobile phone");
//        allCategory.put(c2.getCateId(),c2);
//
//        Product p0 = new Product("Iphone 13",Double.parseDouble("100000"),"0");
//        allProduct.put(p0.getpId(),p0);
//        Product p2 = new Product("MSI2",Double.parseDouble("1200000"),"1");
//        allProduct.put(p2.getpId(),p2);
//
//        Product p3 = new Product("Iphone 14",Double.parseDouble("1400000"),"0");
//        allProduct.put(p3.getpId(),p3);
//        Product p4 = new Product("MSI3",Double.parseDouble("1300000"),"1");
//        allProduct.put(p4.getpId(),p4);
//
//        HashMap <Product,Integer> orderDetails = new HashMap<>();
//        orderDetails.put(p0,10);
//        Order o = new Order("0",orderDetails, p0.getPrice()*10,p0.getPrice()*10);
//    }
}


