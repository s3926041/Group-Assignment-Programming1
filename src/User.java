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
        this.role = role;
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
        System.out.println("Enter username (Length greater or equal to 4 no spaces)");
        Scanner input = new Scanner(System.in);
        String username = input.nextLine();

        if (checkValidGeneral(username)) {
            if (Data.allUser.containsKey(username)) {
                System.out.println("Username existed!");
            } else {
                System.out.println("Enter password (Length greater or equal to 4 no spaces)");
                input = new Scanner(System.in);
                String password = input.nextLine();
                if (checkValidGeneral(password)) {
                        System.out.println("Enter full name (Length greater or equal to 4)" );
                        input = new Scanner(System.in);
                        String name = input.nextLine();
                        if(name.length() >= 4){
                            System.out.println("Enter address (Length greater or equal to 4)");
                            input = new Scanner(System.in);
                            String address = input.nextLine();
                            if(address.length() >= 4){
                                System.out.println("Enter phone number (Only contain digits)");
                                input = new Scanner(System.in);
                                String phonenumber = input.nextLine();
                                try {
                                    int d = Integer.parseInt(phonenumber);
                                    User newMember = new Member(username, password,name,address, String.valueOf(d));
                                    Data.allUser.put(username, newMember);
                                    Data.allUserByID.put(newMember.getUserId(), newMember);
                                    System.out.println("Account has been successfully registered!");
                                } catch (NumberFormatException nfe) {
                                    System.err.println("Invalid phone number!");
                                }
                            }
                            else{
                                System.err.println("Address length must greater or equal to 4");
                            }
                        }
                        else{
                            System.err.println("Name length must greater or equal to ");
                        }
                } else {
                    System.err.println("Password must have length greater than 4 and not having spaces!");
                }
            }
        } else {
            System.err.println("Username must have length greater than 4 and not having spaces!");
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
            } else
                System.err.println("Wrong password!");
        } else
            System.err.println("Username not exist!");
    }


    //GENERAL METHOD

    //FORMAT TABLE
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

        int maxId = 2;
        int maxName = 4;
        int maxPrice = 5;
        for (String i : allProduct.keySet()) {
            maxId = Math.max(maxId,i.length());
            maxName = Math.max(maxName,allProduct.get(i).getName().length());
            maxPrice = Math.max(maxPrice,String.valueOf(allProduct.get(i).getPrice()).length());
        }
        maxName -=3;
        maxPrice -= 4;
        int length = (maxId+2)+3+(maxName+4) +3 +(maxPrice+5) +2;
        printLine(length);
        System.out.printf("| %"+maxId+"sID | %"+maxName+"sName | %"+maxPrice+"sPrice |\n", "","","");
        printLine(length);
        for (String i : allProduct.keySet()) {
            System.out.printf("| %"+(maxId+2)+"s | %"+(maxName+4)+"s | %"+(maxPrice+5)+".2f |\n", i, allProduct.get(i).getName(), allProduct.get(i).getPrice());
            printLine(length);
        }
    }

    public void viewProductDetails() {
        HashMap<String, Product> allProduct = Data.allProduct;
        System.out.println("Enter product ID: ");
        Scanner command = new Scanner(System.in);
        String pId = command.nextLine();
        if (allProduct.containsKey(pId)) {
            allProduct.get(pId).getInformation();
            System.out.println();
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
            int maxId = 2;
            int maxName = 4;
            int maxPrice = 5;
            for (String i : productOfCategory.keySet()) {
                maxId = Math.max(maxId,productOfCategory.get(i).getpId().length());
                maxName = Math.max(maxName, productOfCategory.get(i).getName().length());
                maxPrice = Math.max(maxPrice,String.valueOf(productOfCategory.get(i).getPrice()).length());
            }
            maxName -=3;
            maxPrice -= 4;
            int length = (maxId+2)+3+(maxName+4) +3 +(maxPrice+5) +2;
            printLine(length);
            System.out.printf("| %"+maxId+"sID | %"+maxName+"sName | %"+maxPrice+"sPrice |\n", "","","");
            printLine(length);

            for (String i : productOfCategory.keySet()) {
                System.out.printf("| %"+(maxId+2)+"s | %"+(maxName+4)+"s | %"+(maxPrice+5)+".2f |\n", productOfCategory.get(i).getpId(), productOfCategory.get(i).getName(), productOfCategory.get(i).getPrice());
                printLine(length);
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
        ar.sort(Comparator.comparing(o -> Double.parseDouble(o.get(0))));
        try {
            System.out.println("Sorted products: ");
            int maxId = 2;
            int maxName = 4;
            int maxPrice = 5;
            for (ArrayList<String> row : ar) {
                Product p = Data.allProduct.get(row.get(1));
                maxId = Math.max(maxId,p.getpId().length());
                maxName = Math.max(maxName, p.getName().length());
                maxPrice = Math.max(maxPrice,String.valueOf(row.get(0)).length());
            }
            maxName -=3;
            maxPrice -= 4;
            int length = (maxId+2)+3+(maxName+4) +3 +(maxPrice+5) +2;
            printLine(length);
            System.out.printf("| %"+maxId+"sID | %"+maxName+"sName | %"+maxPrice+"sPrice |\n", "","","");
            printLine(length);
            for (ArrayList<String> row : ar) {
                Product p = Data.allProduct.get(row.get(1));
                System.out.printf("| %"+(maxId+2)+"s | %"+(maxName+4)+"s | %"+(maxPrice+5)+".2f |\n",p.getpId(), p.getName(), Double.valueOf(row.get(0)));
                printLine(length);
            }
        } catch (Exception e) {
            System.err.println("ERROR");
        }
    }

    protected String getPhoneNumber(){return null;}
    protected String getMemberShip(){return null;}
    protected String getName(){return null;}
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
    public static void pressContinue(){
        System.out.println("Press Enter key to continue...");
        Scanner s = new Scanner(System.in);
        s.nextLine();
    }

    public void command(){

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
