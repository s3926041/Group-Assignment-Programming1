// package untitled;

import java.io.*;
import java.util.*;

public class Main {
    @SuppressWarnings("unchecked")
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
    static void welcome() throws IOException, ClassNotFoundException {
        System.out.println(
                """
                        COSC2081 GROUP ASSIGNMENT\s
                        STORE ORDER MANAGEMENT SYSTEM\s
                        Instructor: Mr. Minh Vu\s
                        Group: Group Name\s
                        s3926041, Nguyen Thanh Hung\s
                        sXXXXXXX, Nguyen Huy Cao Son\s
                        sXXXXXXX, Nguyen The Duc\s
                        You are a guest
                        """
        );
        //READFILE
        allMember = readFile("member.obj");
        allCategory  =readFile("category.obj");
        allProduct = readFile("product.obj");
        allOrder = readFile("order.obj");
        Member.setTotalUser(Main.allMember == null? 0 : Main.allMember.size());
        Product.setTotalProduct(Main.allProduct == null? 0 : Main.allProduct.size());
        Order.setTotalOrder(Main.allOrder == null ? 0: Main.allOrder.size());
        Category.setTotalCate(Main.allCategory == null? 0  : Main.allCategory.size());
    }
    static void end() throws IOException {
        writeFile(allMember,"member.obj");
        writeFile(allOrder,"order.obj");
        writeFile(allCategory,"category.obj");
        writeFile(allProduct,"product.obj");
        System.out.println("Thanks for shopping with us!");
    }
    static void roleCommandGuest(){
        while(currentStatus){
            System.out.print("""
                \s
                INSTRUCTION FOR GUEST
                -1. QUIT
                1. REGISTER
                2. LOGIN
                3. LIST PRODUCT
                4. VIEW PRODUCT DETAILS BY PRODUCT ID
                5. LIST PRODUCT BY CATEGORY ID
                6. SORT ALL PRODUCT BY PRODUCT PRICE
                """);
            System.out.println("Enter command: ");
            Scanner command = new Scanner(System.in);
            String commandString = command.nextLine();
            switch (commandString) {
                case ("-1") -> {
                    programStatus = false;
                    currentStatus = false;
                }
                case ("1") -> currentUser.register();
                case ("2") -> currentUser.login();
                case ("3") -> currentUser.listProducts();
                case ("4") -> currentUser.viewProductDetails();
                case ("5") -> currentUser.listProductsbyCate();
                case ("6") -> currentUser.sortProduct();
                default -> {
                    currentStatus = false;
                    System.err.println("Invalid command!");
                }
            }
        
    
    
        }

    }

    static void roleCommandMember(){
        while(currentStatus){
            System.out.println("""
                \s
                INSTRUCTION FOR MEMBER
                -1. QUIT
                1. VIEW ACCOUNT INFORMATION
                2. CREATE ORDER
                3. LIST PRODUCT
                4. VIEW PRODUCT DETAILS BY PRODUCT ID
                5. LIST PRODUCT BY CATEGORY ID
                6. SORT ALL PRODUCT BY PRODUCT PRICE
                7. GET ORDER BY ORDER ID\s""");
            System.out.println("Enter command: ");
            Scanner command = new Scanner(System.in);
            String commandString = command.nextLine();
            switch (commandString) {
                case ("-1") -> {
                    programStatus = false;
                    currentStatus = false;
                }
                case ("1") -> currentUser.viewInformation("main");
                case ("2") -> currentUser.createOrder();
                case ("3") -> currentUser.listProducts();
                case ("4") -> currentUser.viewProductDetails();
                case ("5") -> currentUser.listProductsbyCate();
                case ("6") -> currentUser.sortProduct();
                case ("7") -> currentUser.getOrderByOID();
                default -> {
                    currentStatus = false;
                    System.err.println("Invalid command!");
                }
            }
        }
    }
    static void roleCommandAdmin() throws ClassCastException{
        while(currentStatus){
            System.out.println("""
                \s
                INSTRUCTION FOR ADMIN
                -1. QUIT
                1. VIEW ALL PRODUCTS\s
                2. VIEW ALL ORDERS\s
                3. VIEW ALL MEMBERS
                4. ADD NEW PRODUCT
                5. UPDATE PRODUCT PRICE BY ID
                6. GET ORDERS BY CUSTOMER ID
                7. CHANGE ORDER STATUS
                """);
            System.out.println("Enter command: ");
            Scanner command = new Scanner(System.in);
            String commandString = command.nextLine();
            switch (commandString) {
                case "-1" -> {
                    programStatus = false;
                    currentStatus = false;
                }
                case "1" -> currentUser.listProducts();
                case "2" -> currentUser.listOrder();
                case "3" -> currentUser.listMember();
                case "4" -> currentUser.addNewProduct();
                case "5" -> currentUser.updatePrice();
                case "6" -> currentUser.getOrdersByMemberID();
                case "7" -> currentUser.changeOrderStatus();
                default -> {
                    currentStatus = false;
                    System.err.println("Invalid command!\n");
                }
            }
        }
    }
    static Member currentUser =new Member(); //GUEST
    static boolean programStatus = true;
    static boolean currentStatus = true;
    static HashMap<String,Member> allMember = new HashMap<>(); //USERNAME AND MEMBER
    static HashMap<String,Member> allMemberByID = new HashMap<>(); //ID AND MEMBER //COPY OF ALLMEMBER
    static HashMap<String, Order> allOrder = new HashMap<>(); //OID AND ORDER
    static HashMap<String, Category> allCategory = new HashMap<>();//CATE ID AND CATEGORY
    static HashMap<String, Product> allProduct = new HashMap<>(); //pID AND PRODUCT
    public static void copy(){
        allMemberByID =new HashMap<>();
        for(String i : allMember.keySet()) {
            allMemberByID.put(allMember.get(i).getUserId(), allMember.get(i));
        }
    }
    static void start() throws IOException, ClassNotFoundException {
        welcome();
        copy();
        while(programStatus){
            currentStatus =true;
            switch (currentUser.getRole()) {
                case (0) -> roleCommandGuest();

                //MEMBER
                case (1) -> roleCommandMember();

                //ADMIN
                case (2) -> roleCommandAdmin();
                default -> {
                    break;
                }
            }
        }
    }
   static void createData() throws IOException {
       Member admin = new Member("admin","root","");
       admin.setRole(2);
       allMember.put(admin.getUsername(),admin);
       Member member = new Member("hungcpui","puiabc12","0889265054");
       allMember.put(member.getUsername(),member);
       copy();
       Category c1 = new Category("laptop");
       allCategory.put(c1.getCateId(),c1);
       Category c2 = new Category("mobile phone");
       allCategory.put(c2.getCateId(),c2);

       Product p0 = new Product("Iphone 13",Double.parseDouble("100000"),"0");
       allProduct.put(p0.getpId(),p0);
       Product p2 = new Product("MSI2",Double.parseDouble("1200000"),"1");
       allProduct.put(p2.getpId(),p2);

       Product p3 = new Product("Iphone 14",Double.parseDouble("1400000"),"0");
       allProduct.put(p3.getpId(),p3);
       Product p4 = new Product("MSI3",Double.parseDouble("1300000"),"1");
       allProduct.put(p4.getpId(),p4);

       HashMap <Product,Integer> orderDetails = new HashMap<>();
       orderDetails.put(p0,10);
       Order o = new Order("0",orderDetails, p0.getPrice()*10,p0.getPrice()*10);
   }
    
    public static void main(String[] args) throws IOException, ClassNotFoundException,ClassCastException {

        // createData();
        start();
        end();
    }
}


