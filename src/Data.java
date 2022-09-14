
import java.io.*;
import java.util.HashMap;

public class Data  {
    public static void main(String[] args) throws IOException {
        createSampleData();
    }
    static HashMap<String,User> allUser = new HashMap<>(); //USERNAME AND User
    static HashMap<String,User> allUserByID = new HashMap<>(); //ID AND User //COPY OF ALLUser
    static HashMap<String, Order> allOrder = new HashMap<>(); //OID AND ORDER
    static HashMap<String, Category> allCategory = new HashMap<>();//CATE ID AND CATEGORY
    static HashMap<String, Product> allProduct = new HashMap<>(); //pID AND PRODUCT
    static HashMap<String, Integer> currentID = new HashMap<>(); //ALL CLASS CURRENT ID
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
        allUser = readFile("user.obj");
        allCategory  =readFile("category.obj");
        allProduct = readFile("product.obj");
        allOrder = readFile("order.obj");
        currentID = readFile("currentID.obj");

        if(currentID != null){
            if(currentID.containsKey("user")){
                User.setCurrentUserID(currentID.get("user"));
            }
            if(currentID.containsKey("order")){
                Order.setCurrentOrderId(currentID.get("order"));
            }
            if(currentID.containsKey("product")){
                Product.setCurrentProductId(currentID.get("product"));
            }
            if(currentID.containsKey("category")){
                Category.setCurrentCateId(currentID.get("category"));
            }
        }

        if(allUser!= null){
            allUserByID =new HashMap<>();
            for(String i : allUser.keySet()) {
                allUserByID.put(allUser.get(i).getUserId(), allUser.get(i));
            }
        }

    }
    static void write() throws IOException {
        writeFile(allUser,"user.obj");
        writeFile(allOrder,"order.obj");
        writeFile(allCategory,"category.obj");
        writeFile(allProduct,"product.obj");
        writeFile(currentID,"currentID.obj");
    }

    static void createSampleData() throws IOException {
        new Admin("admin","root");
        new Member("user1","user1","user1","Ha Noi","0123456789");
        new Member("user2","user2","user2","Ha Noi","0123456788");
        new Member("user3","user3","user3","Ha Noi","0123456787");

        new Category("laptop"); //ID 0
        new Category("mobile phone");//ID 1
        new Category("headphone"); // ID 2
        new Category("keyboard");//ID 3
        new Category("mouse"); //ID 4

        new Product("MSI2",Double.parseDouble("1200000"),"0");
        new Product("MSI3",Double.parseDouble("1300000"),"0");

        new Product("SAMSUNG 22",Double.parseDouble("1000000"),"1");
        new Product("Iphone 14",Double.parseDouble("1400000"),"1");

        new Product("Arctic 3",Double.parseDouble("32000"),"2");
        new Product("HyperX Cloud",Double.parseDouble("400000"),"2");

        new Product("Leopold 900",Double.parseDouble("80000"),"3");
        new Product("Razer Huntsman",Double.parseDouble("900000"),"3");

        new Product("Zowie EC2",Double.parseDouble("300000"),"4");
        new Product("Logitech G-PRO",Double.parseDouble("420000"),"4");

        HashMap<String,Double> hash = new HashMap<>();
        hash.put("price",allProduct.get("0").getPrice());
        hash.put("quantity",30.0);
        Double price = hash.get("quantity") * hash.get("price");
        HashMap<Product, HashMap<String,Double>>  orderDetails = new HashMap<>();
        orderDetails.put(allProduct.get("0"),hash);
        new Order(allUser.get("user1"),orderDetails,price,price);
        write();
        System.out.println("Data successfully created!");
    }
}


