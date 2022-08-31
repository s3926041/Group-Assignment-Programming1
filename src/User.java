import java.io.Serializable;
import java.util.*;

public class User implements Serializable {


    public static void setCurrentUserID(Integer currentUserID) {
        User.currentUserID = currentUserID;
    }
    public User() {
    }

    public static Integer getCurrentUserID() {
        return currentUserID;
    }

    private static Integer currentUserID =0;
    private String userId;
    private String userName;
    private String passWord;
    private String role = "guest";
    public String getRole() {
        return role;
    }


    public User(String userName,String passWord,String role){
        this.userName = userName;
        this.passWord = passWord;
        this.userId = String.valueOf(currentUserID);
        currentUserID++;
        Data.currentID.put("user",currentUserID);
    }
    public String getUserId() {
        return this.userId;
    }
    public String getUsername() {
        return this.userName;
    }
    public String getPassword() {
        return this.passWord;
    }


    static boolean checkValidGeneral(String input) {
        return input.length() >= 4 && !input.contains(" ");
    }

    //GUEST METHOD
    public void register() {
        System.out.println("Registering account");
        System.out.println("Please enter username");
        Scanner input = new Scanner(System.in);
        String username = input.nextLine();

        //CHECK FOR EXISTED USERNAME
        if (checkValidGeneral(username)) {
            if (Data.allUser.containsKey(username)) {
                System.out.println("Username existed!");
            } else {
                System.out.println("Enter password: ");
                input = new Scanner(System.in);
                String password = input.nextLine();
                if (checkValidGeneral(password)) {
                    System.out.println("Re-enter password: ");
                    input = new Scanner(System.in);
                    String repassword = input.nextLine();
                    if (password.equals(repassword)) {
                        System.out.println("Enter full name: ");
                        input = new Scanner(System.in);
                        String name = input.nextLine();
                        System.out.println("Enter phone number: ");
                        input = new Scanner(System.in);
                        String phonenumber = input.nextLine();
                        try {
                            if (checkValidGeneral(phonenumber)) {
                                int d = Integer.parseInt(phonenumber);
                                User newMember = new Member(username, password,name, phonenumber);
                                Data.allUser.put(username, newMember);
                                Data.allUserByID.put(newMember.getUserId(), newMember);
                                System.out.println("Account has been successfully registered!");
                            } else {
                                System.err.println("Invalid phone number");
                            }
                        } catch (NumberFormatException nfe) {
                            System.err.println("Invalid phone number!");
                        }
                    }

                } else {
                    System.err.println("Password must have length greater than 5 and not having space!");
                }
            }
        } else {
            System.err.println("Invalid username!");
        }
    }

    public void login() {
        System.out.println("Login section");
        System.out.println("Enter user name: ");
        Scanner in = new Scanner(System.in);
        String username = in.nextLine();
        //CHECK VALID USERNAME
        if (Data.allUser.containsKey(username)) {
            System.out.println("Enter password: ");
            in = new Scanner(System.in);
            String password = in.nextLine();
            //CHECK VALID PASSWORD
            if (Data.allUser.get(username).getPassword().equals(password)) {
                //SET GLOBAL STATUS
                Main.currentUser = Data.allUser.get(username);
                System.out.println("LOGGED IN!");
                System.out.println("HI " + username);
                Main.login = true;
                Main.currentStatus = false;
            } else
                System.err.println("Wrong password!");
        } else
            System.err.println("Username not exist!");
    }


    //GENERAL METHOD
    public static void printLine(Integer length){
        System.out.print("|");
        for(int j = 0 ; j < length;j++){
            System.out.print("-");
        }
        System.out.print("|\n");
    }
    public void listProducts() {
        HashMap<String, Product> allProduct = Data.allProduct;
        System.out.println("ALL PRODUCTS:");

        int maxid = 2;
        int maxname = 4;
        int maxprice = 5;
        for (String i : allProduct.keySet()) {
            maxid = Math.max(maxid,i.length());
            maxname = Math.max(maxname,allProduct.get(i).getName().length());
            maxprice = Math.max(maxprice,String.valueOf(allProduct.get(i).getPrice()).length());
        }
        maxname -=3;
        maxprice -= 4;
        printLine((maxid+2)+3+(maxname+4) +3 +(maxprice+5) +2);
        System.out.printf("| %"+maxid+"sID | %"+maxname+"sName | %"+maxprice+"sPrice |\n", "","","");
        printLine((maxid+2)+3+(maxname+4) +3 +(maxprice+5) +2);
        for (String i : allProduct.keySet()) {
            System.out.printf("| %"+(maxid+2)+"s | %"+(maxname+4)+"s | %"+(maxprice+5)+".2f |\n", i, allProduct.get(i).getName(), allProduct.get(i).getPrice());
            printLine((maxid+2)+3+(maxname+4) +3 +(maxprice+5) +2);
        }
    }

    public void viewProductDetails() {
        HashMap<String, Product> allProduct = Data.allProduct;
        System.out.println("Enter product ID: ");
        Scanner command = new Scanner(System.in);
        String pId = command.nextLine();
        if (allProduct.containsKey(pId)) {
            allProduct.get(pId).getInformation();
        } else {
            System.err.println("Product ID not exist!");
        }
    }

    public void listProductsbyCate() {
        HashMap<String, Category> allCategory = Data.allCategory;
        System.out.println("Category available:");
        for (String cateID : allCategory.keySet()) {
            System.out.println("ID: " + cateID + " NAME: " + allCategory.get(cateID).getCateName());
        }
        System.out.println("Choose category: (By ID) ");
        Scanner command = new Scanner(System.in);
        String commandString = command.nextLine();


        if (allCategory.containsKey(commandString)) {
            HashMap<String, Product> productOfCategory = new HashMap<>(allCategory.get(commandString).getProductOfCategory());
            int maxid = 2;
            int maxname = 4;
            int maxprice = 5;
            for (String i : productOfCategory.keySet()) {
                maxid = Math.max(maxid,productOfCategory.get(i).getpId().length());
                maxname = Math.max(maxname, productOfCategory.get(i).getName().length());
                maxprice = Math.max(maxprice,String.valueOf(productOfCategory.get(i).getPrice()).length());
            }
            maxname -=3;
            maxprice -= 4;
            printLine((maxid+2)+3+(maxname+4) +3 +(maxprice+5) +2);
            System.out.printf("| %"+maxid+"sID | %"+maxname+"sName | %"+maxprice+"sPrice |\n", "","","");
            printLine((maxid+2)+3+(maxname+4) +3 +(maxprice+5) +2);

            for (String i : productOfCategory.keySet()) {
                System.out.printf("| %"+(maxid+2)+"s | %"+(maxname+4)+"s | %"+(maxprice+5)+".2f |\n", productOfCategory.get(i).getpId(), productOfCategory.get(i).getName(), productOfCategory.get(i).getPrice());
                printLine((maxid+2)+3+(maxname+4) +3 +(maxprice+5) +2);
            }
        } else {
            System.err.println("Category ID not existed");
        }
    }

    public void sortProduct() {
        ArrayList<ArrayList<String>> ar = new ArrayList<>();
        for (String pid : Data.allProduct.keySet()) {
            ArrayList<String> row = new ArrayList<>();
            row.add(String.valueOf(Data.allProduct.get(pid).getPrice()));
            row.add(pid);
            ar.add(row);
        }
        ar.sort(Comparator.comparing(o -> o.get(0)));
        try {

            System.out.println("Sorted products: ");
            int maxid = 2;
            int maxname = 4;
            int maxprice = 5;
            for (ArrayList<String> row : ar) {
                Product p = Data.allProduct.get(row.get(1));
                maxid = Math.max(maxid,p.getpId().length());
                maxname = Math.max(maxname, p.getName().length());
                maxprice = Math.max(maxprice,String.valueOf(row.get(0)).length());
            }
            maxname -=3;
            maxprice -= 4;

            printLine((maxid+2)+3+(maxname+4) +3 +(maxprice+5) +2);
            System.out.printf("| %"+maxid+"sID | %"+maxname+"sName | %"+maxprice+"sPrice |\n", "","","");
            printLine((maxid+2)+3+(maxname+4) +3 +(maxprice+5) +2);
            for (ArrayList<String> row : ar) {
                Product p = Data.allProduct.get(row.get(1));
                System.out.printf("| %"+(maxid+2)+"s | %"+(maxname+4)+"s | %"+(maxprice+5)+".2f |\n",p.getpId(), p.getName(), Double.valueOf(row.get(0)));
                printLine((maxid+2)+3+(maxname+4) +3 +(maxprice+5) +2);
            }
        } catch (Exception e) {
            System.err.println("ERROR");
        }
    }

    protected String getPhoneNumber(){return null;};
    protected String getMemberShip(){return null;};
    protected String getName(){return null;};
    public void viewInformation(){}
    public void createOrder() {}
    public void getOrderByOID(){}
    public void listOrder() {}
    public void listMember() {}
    public void addNewProduct() {}
    public void updatePrice() {}
    public void getOrdersByMemberID() {}
    public void changeOrderStatus() {}

    protected HashMap<String,Order> getOrders() {
        return null;
    }
    public void addOrder(Order order) {
    }
    public void pressContinue(){
        System.out.println("Press Enter key to continue...");
        Scanner s = new Scanner(System.in);
        s.nextLine();
    }

    public void command(){
        while(Main.currentStatus){
            System.out.print("""
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
                case ("-1") -> Main.currentStatus = false;
                case ("1") -> register();
                case ("2") -> login();
                case ("3") -> listProducts();
                case ("4") -> viewProductDetails();
                case ("5") -> listProductsbyCate();
                case ("6") -> sortProduct();
                default -> System.err.println("Invalid command!");

            }
            if(!commandString.equals("-1")) pressContinue();
        }
    }
}
