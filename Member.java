// package untitled;

import java.io.Serializable;

import java.util.*;

public class Member implements Serializable {
    public static int totalUser;
    private Double totalSpend = Double.valueOf(0);
    private String userId;
    private String userName;
    private String passWord;
    private String phoneNumber;
    private Integer role = 0;
    private HashMap<String, Order> orders = new HashMap<>(); //ORDER ID AND ORDER //ALL ORDER OF A PARTICULAR MEMBER
    private String memberShip = "none"; //MEMBERSHIP ARE SET AFTER CUSTOMER CREATE ORDER

    public Member() {
    }

    public Member(String userName, String passWord, String phoneNumber) {
        this.userName = userName;
        this.passWord = passWord;
        this.phoneNumber = phoneNumber;
        this.userId = String.valueOf(totalUser);
        this.role = 1;
        totalUser++;
    }

    public HashMap<String, Order> getOrders() {
        return orders;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }


    public void addOrder(Order o) {
        this.orders.put(o.getoId(), o);
    }

    public static void setTotalUser(int totalUser) {
        Member.totalUser = totalUser;
    }


    public String getUsername() {
        return this.userName;
    }

    public String getPassword() {
        return this.passWord;
    }

    public Integer getRole() {
        return this.role;
    }


    //DATA STRUCTURE TO MAKE PRODUCT SORTING AND GET MEMBER BY ID EASIER
    //USER ID + MEMBER


    //GUEST METHOD

    static boolean checkValidGeneral(String input) {
        return input.length() >= 8 && !input.contains(" ");
    }

    public void register() {
        System.out.println("Registering account");
        System.out.println("Please enter username");
        Scanner input = new Scanner(System.in);
        String username = input.nextLine();

        //CHECK FOR EXISTED USERNAME
        if (checkValidGeneral(username)) {
            if (Main.allMember.containsKey(username)) {
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
                        System.out.println("Enter phone number: ");
                        input = new Scanner(System.in);
                        String phonenumber = input.nextLine();
                        try {
                            if (checkValidGeneral(phonenumber)) {
                                int d = Integer.parseInt(phonenumber);
                                Member newMember = new Member(username, password, phonenumber);
                                Main.allMember.put(username, newMember);
                                Main.allMemberByID.put(newMember.getUserId(), newMember);
                                System.out.println("Account has been successfully registered!");
                            } else {
                                System.err.println("Invalid phone number");
                            }
                        } catch (NumberFormatException nfe) {
                            System.err.println("Invalid phone number!");
                        }
                    }

                } else {
                    System.err.println("Invalid phone password");
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
        if (Main.allMember.containsKey(username)) {
            System.out.println("Enter password: ");
            in = new Scanner(System.in);
            String password = in.nextLine();
            //CHECK VALID PASSWORD
            if (Main.allMember.get(username).getPassword().equals(password)) {
                //SET GLOBAL STATUS
                Main.currentUser = Main.allMember.get(username);
                Main.currentStatus = false;
                System.out.println("LOGGED IN!");
                System.out.println("HI " + username);
            } else
                System.err.println("Wrong password!");
        } else
            System.err.println("Username not exist!");
    }


    //GUEST AND MEMBER METHOD
    public void listProducts() {
        HashMap<String, Product> allProduct = Main.allProduct;
        System.out.println("ALL PRODUCTS:");
        for (String i : allProduct.keySet()) {
            System.out.printf("ID: %3s | Name: %9s | Price: %9.0f\n", i, allProduct.get(i).getName(), allProduct.get(i).getPrice());
        }
    }

    public void viewProductDetails() {
        HashMap<String, Product> allProduct = Main.allProduct;
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
        HashMap<String, Category> allCategory = Main.allCategory;
        System.out.println("Category available:");
        for (String cateID : allCategory.keySet()) {
            System.out.println("ID: " + cateID + " NAME: " + allCategory.get(cateID).getCateName());
        }
        System.out.println("Choose category: (By ID) ");
        Scanner command = new Scanner(System.in);
        String commandString = command.nextLine();
        if (allCategory.containsKey(commandString)) {
            HashMap<String, Product> productOfCategory = new HashMap<>(allCategory.get(commandString).getProductOfCategory());
            for (String i : productOfCategory.keySet()) {
                System.out.printf("ID: %3s | Name: %9s | Price: %9.0f \n", productOfCategory.get(i).getpId(), productOfCategory.get(i).getName(), productOfCategory.get(i).getPrice());
            }
        } else {
            System.err.println("Category ID not existed");
        }
    }

    static ArrayList<ArrayList<String>> ar;

    public void sortProduct() {
        ar = new ArrayList<>();
        for (String pid : Main.allProduct.keySet()) {
            ArrayList<String> row = new ArrayList<>();
            row.add(String.valueOf(Main.allProduct.get(pid).getPrice()));
            row.add(pid);
            ar.add(row);
        }
        Collections.sort(ar, new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> o1, ArrayList<String> o2) {
                return o1.get(0).compareTo(o2.get(0));
            }
        });
        try {

            System.out.println("Sorted products: ");
            for (ArrayList<String> row : ar) {
                Product p = Main.allProduct.get(row.get(1));
                System.out.printf("Id: %4s | Name: %9s | Price: %9.0f \n", p.getpId(), p.getName(), Double.valueOf(row.get(0)));
            }
        } catch (Exception e) {
            System.err.println("ERROR");
        }
    }

    //MEMBER ONLY
    public void viewInformation(String callFrom) {
        if (callFrom.equals("main")) {
            System.out.println("Id: " + this.userId);
            System.out.println("Username: " + this.userName);
            System.out.println("Password: " + this.passWord);
            System.out.println("Phone number: " + this.phoneNumber);
            System.out.println("Membership: " + this.memberShip);
            if (!this.orders.isEmpty()) {
                System.out.println("Orders:");
                for (String oid : this.orders.keySet()) {
                    System.out.printf("-Order ID: %3s\n", oid);
                }
            } else {
                System.out.println("Orders: No order yet");
            }
        } else
            System.out.printf("Id: %3s | Username: %10s | Password: %10s | Phone: %10s | Membership: %8s\n", this.userId, this.userName, this.passWord, this.phoneNumber, this.memberShip);
    }

    public void createOrder() {
        HashMap<Product, Integer> orderDetails = new HashMap<>();
        boolean status = true;
        Double curprice = 0.0;
        listProducts();
        while (status) {
            System.out.println("Enter ID of the wanted product: ");
            Scanner command = new Scanner(System.in);
            String commandString = command.nextLine(); //PRODUCT ID
            if (Main.allProduct.containsKey(commandString)) {
                try {
                    Product pr = Main.allProduct.get(commandString);
                    System.out.println("Enter quantity ");
                    command = new Scanner(System.in);
                    commandString = command.nextLine();
                    int commandInt = Integer.parseInt(commandString); //QUANTITY
                    while (commandInt <= 0) {
                        System.out.println("Re-enter the quantity: ");
                        command = new Scanner(System.in);
                        commandString = command.nextLine();
                        commandInt = Integer.parseInt(commandString); //QUANTITY
                    }

                    if (orderDetails.containsKey(pr)) {
                        System.out.println("There is already " + orderDetails.get(pr) + " in the cart and you have added " + commandInt + " more");
                        orderDetails.put(pr, orderDetails.get(pr) + commandInt);
                    } else orderDetails.put(pr, commandInt);
                    curprice += pr.getPrice() * commandInt; //WITHOUT PROMOTION
                    System.out.println("Buy another product (Press 1 to continue buying)");
                    command = new Scanner(System.in);
                    commandString = command.nextLine();
                    Double curpricebf = curprice;
                    if (commandString.equals("1")) {
                        continue;
                    } else {
                        status = false;
                    
                        System.out.printf("Here are you order:\nOrder ID %d\n",Main.allOrder.size());
                        for (Product p : orderDetails.keySet()) {
                            System.out.printf("Product ID: %4s | Name: %9s | Quantity: %4d | Price: %10.0f\n", p.getpId(), p.getName(), orderDetails.get(p), p.getPrice() * Double.valueOf(commandInt));
                        }
                        System.out.println();
                        if (this.memberShip.equals("none")) {
                            System.out.println("There is no promotion for normal member");
                        } else if (this.memberShip.equals("Platinum")) {
                            System.out.println("You are getting 15% discount as an Platinum member!");
                            curprice *= 0.85;
                        } else if (this.memberShip.equals("Gold")) {
                            System.out.println("You are getting 10% discount as an Gold member!");
                            curprice *= 0.90;
                        } else if (this.memberShip.equals("Silver")) {
                            System.out.println("You are getting 5% discount as an Silver member!");
                            curprice *= 0.95;
                        }
                        Order newOrder = new Order(this.userId, orderDetails, curprice, curpricebf);
                        this.totalSpend += curprice;
                        System.out.printf("Total price before promotion: %.0f\n", curpricebf);
                        System.out.printf("Total price after promotion: %.0f\n", curprice);
                        if (this.totalSpend >= 25000000.0) {
                            this.memberShip = "Platinum";
                        }else
                        if (this.totalSpend >= 10000000.0) {
                            this.memberShip = "Gold";
                        }else
                        if (this.totalSpend >= 5000000.0) {
                            this.memberShip = "Silver";
                        }    
                    }


                } catch (NumberFormatException nfe) {
                    status = false;
                    System.err.println("Invalid input!");
                }
            } else {
                status = false;
                System.out.println("Invalid Product ID");
            }
        }
    }

    public void getOrderByOID() throws ClassCastException {
        if (this.orders.isEmpty()) {
            System.err.println("No order placed yet");
        } else {
            System.out.println("Enter Order ID: ");
            Scanner command = new Scanner(System.in);
            String commandString = command.nextLine();

            if (this.orders.containsKey(commandString)) {
                HashMap<Product, Integer> orderDetails;
                orderDetails = Main.allOrder.get(commandString).getOrderDetails();
                System.out.println("Order ID: " + commandString);
                for (Product j : orderDetails.keySet()) {
                    System.out.printf("Product ID: %3s | Quantity: %4d | Price: %.0f\n",j.getpId(),orderDetails.get(j),j.getPrice() * Double.valueOf(orderDetails.get(j)));
                }
                System.out.printf("Price before promotion: %.0f\n",Main.allOrder.get(commandString).getPricebf());
                System.out.printf("Total price after promotion: %.0f\n", Main.allOrder.get(commandString).getTotalPrice());
            } else {
                System.err.println("Order ID not exist");
            }
        }
    }


    //ADMIN
    public void listOrder() {
        System.out.println("ALL ORDERS:");
        for (String oid : Main.allOrder.keySet()) {
            Order order = Main.allOrder.get(oid);
            order.getInfo();
        }
    }

    public void listMember() {
        System.out.println("ALL MEMBERS:");
        for (String i : Main.allMember.keySet()) {
            Main.allMember.get(i).viewInformation("method");
        }
    }

    public void addNewProduct() {
        System.out.println("Category: ");
        for (String i : Main.allCategory.keySet()) {
            System.out.println("Category ID :" + i + " Name: " + Main.allCategory.get(i).getCateName());
        }
        System.out.println("Enter product's category ID:");
        Scanner command = new Scanner(System.in);
        String category = command.nextLine();
        if (!Main.allCategory.containsKey(category)) {
            System.out.println("Category ID not existed");
            return;
        }
        System.out.println("Enter product name: ");
        command = new Scanner(System.in);
        String name = command.nextLine();
        System.out.println("Enter product price:");
        command = new Scanner(System.in);
        String price = command.nextLine();
        try {
            Double priceDouble = Double.parseDouble(price);
            Product p = new Product(name, priceDouble, category);
            Main.allProduct.put(p.getpId(), p);
            System.out.println("Product added successfully");
        } catch (NumberFormatException nfe) {
            System.err.println("Invalid price input!");
        }
    }

    public void updatePrice() {
        System.out.println("Changing product's price \nEnter product ID :");
        Scanner command = new Scanner(System.in);
        String pId = command.nextLine();
        if (Main.allProduct.containsKey(pId)) {
            try {
                System.out.printf("The current price are %11.0f \n Enter update price:", Main.allProduct.get(pId).getPrice());
                command = new Scanner(System.in);
                Double d = Double.parseDouble(command.nextLine());
                Main.allProduct.get(pId).setPrice(d);
                System.out.printf("Price has been updated to %.0f\n", d);
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid input!");
            }
        } else {
            System.err.println("Product ID not exist!");
        }
    }
    public void getOrdersByMemberID() throws ClassCastException {
        System.out.println("Enter Member ID: ");
        Scanner command = new Scanner(System.in);
        String userID = command.nextLine();
        if (Main.allMemberByID.containsKey(userID)) {
            HashMap<String, Order> orders = Main.allMemberByID.get(userID).getOrders();
            if (orders == null) {
                System.out.println("No order placed");
            } else {
                for (String i : orders.keySet()) {
                    HashMap<Product, Integer> orderDetails = orders.get(i).getOrderDetails();
                    System.out.println("Order ID " + i+ ":");
                    for (Product j : orderDetails.keySet()) {
                        System.out.printf("Product ID: %4s | Quantity: %4d | Price: %.0f\n",j.getpId(),orderDetails.get(j),j.getPrice() * Double.valueOf(orderDetails.get(j)));
                    }
                    System.out.printf("Price before promotion: %.0f\n",Main.allOrder.get(i).getPricebf());
                    System.out.printf("Total price after promotion: %.0f\n", Main.allOrder.get(i).getTotalPrice());
                }
            }
        } else {
            System.err.println("Member ID not existed!");
        }
    }

    public void changeOrderStatus() {
        System.out.println("Enter order ID: ");
        Scanner command = new Scanner(System.in);
        String oId = command.nextLine();
        if (Main.allOrder.containsKey(oId)) {
            System.out.println("Enter order status for this order id " + oId + ":");
            command = new Scanner(System.in);
            String orderStatus = command.nextLine();
            Main.allOrder.get(oId).setOrderStatus(orderStatus);
        } else {
            System.err.println("Order ID not existed!");
        }
    }
}