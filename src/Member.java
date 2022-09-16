import java.io.Serializable;

import java.util.*;

public class Member extends User implements Serializable {

    private Double totalSpend = 0.0;
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getMemberShip() {
        return memberShip;
    }

    private String phoneNumber;

    public String getAddress() {
        return address;
    }

    private String address;
    private HashMap<String, Order> orders = new HashMap<>(); //ORDER ID AND ORDER //ALL ORDER OF A PARTICULAR MEMBER
    private String memberShip = "Regular"; //MEMBERSHIP ARE SET AFTER CUSTOMER CREATE ORDER
    public Member(String userName, String passWord,String name,String address, String phoneNumber) {
       super(userName,passWord,"member");
       this.address = address;
       this.phoneNumber = phoneNumber;
       this.name = name;
       Data.allUser.put(userName,this);
    }
    public HashMap<String, Order> getOrders() {
        return orders;
    }

    public void addOrder(Order o) {
        this.orders.put(o.getoId(), o);
    }

    //MEMBER ONLY
    @Override
    public void viewInformation() {
            System.out.println("Id: " + getUserId());
            System.out.println("Username: " + getUsername());
            System.out.println("Phone number: " + this.phoneNumber);
            System.out.println("Address: " + this.address);
            System.out.println("Membership: " + this.memberShip);
            if (!this.orders.isEmpty()) {
                System.out.println("Orders:");
                System.out.println();
                for (String oid: this.orders.keySet()) {
                    this.orders.get(oid).getInfo(false);
                }
            } else {
                System.out.println("Orders: No order yet");
            }
            System.out.println();
    }
    @Override
    public void createOrder() {
        HashMap<Product, HashMap<String,Double>>   orderDetails = new HashMap<>();
        HashMap<String,Double> hash ;
        boolean status = true;
        Double curprice = 0.0;
        listProducts();
        while (status) {
            System.out.println("Enter ID of the wanted product: ");
            Scanner command = new Scanner(System.in);
            String commandString = command.nextLine(); //PRODUCT ID
            if (Data.allProduct.containsKey(commandString)) {
                try {
                    Product pr = Data.allProduct.get(commandString);
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
                        System.out.printf("There is already %.0f in the cart and you have added %d more\n",orderDetails.get(pr).get("quantity"), commandInt);
                        hash = new HashMap<>(orderDetails.get(pr));
                        hash.put("quantity",hash.get("quantity")+Double.parseDouble(commandString));
                        orderDetails.put(pr,hash);
                    } else{
                        hash = new HashMap<>();
                        hash.put("quantity",Double.valueOf(commandString));
                        hash.put("price",pr.getPrice());
                        orderDetails.put(pr, hash);

                    }
                    curprice += pr.getPrice() * commandInt; //WITHOUT PROMOTION
                    System.out.println("Buy another product (Enter 1 to continue buying any key else will finish the order)");
                    command = new Scanner(System.in);
                    commandString = command.nextLine();
                    Double curpricebf = curprice;
                    if (!commandString.equals("1")) {
                        status = false;
                        Order newOrder = new Order(this, orderDetails, curprice, curpricebf);
                        newOrder.getInfo(false);
                        switch (this.memberShip) {
                            case "Regular" -> System.out.println("There is no promotion for Regular member");
                            case "Platinum" -> {
                                System.out.println("You are getting 15% discount as an Platinum member!");
                                curprice *= 0.85;
                            }
                            case "Gold" -> {
                                System.out.println("You are getting 10% discount as an Gold member!");
                                curprice *= 0.90;
                            }
                            case "Silver" -> {
                                System.out.println("You are getting 5% discount as an Silver member!");
                                curprice *= 0.95;
                            }
                        }
                        this.totalSpend += curprice;
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
                System.err.println("Invalid Product ID");
            }
        }
    }
    @Override
    public void getOrderByOID() throws ClassCastException {
        if (this.orders.isEmpty()) {
            System.err.println("No order placed yet");
        } else {
            System.out.println("Enter Order ID: ");
            Scanner command = new Scanner(System.in);
            String commandString = command.nextLine();
            if (this.orders.containsKey(commandString)) {
                this.orders.get(commandString).getInfo(true);
            } else {
                System.err.println("Order ID not exist or you do not have permission to view it!");
            }
        }
    }
@Override
    public void command(){
            System.out.println("""
                INSTRUCTION FOR MEMBER
                -1. QUIT
                0. LOGOUT
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
                case ("-1") -> Main.currentStatus = false;
                case "0" -> {
                    Main.currentUser = new User();
                    System.out.println("LOGGED OUT!");
                }
                case ("1") -> viewInformation();
                case ("2") -> createOrder();
                case ("3") -> listProducts();
                case ("4") -> viewProductDetails();
                case ("5") -> listProductsbyCate();
                case ("6") -> sortProduct();
                case ("7") -> getOrderByOID();
                default -> System.err.println("Invalid command!");
            }
            if(!commandString.equals("-1") & !commandString.equals("0")) pressContinue();
    }

}