import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;

public class Admin extends User implements Serializable {
    public Admin(String userName, String passWord) {super(userName,passWord,"admin");}
    @Override
    public void listOrder() {
        System.out.println("ALL ORDERS:");
        for (String oid : Data.allOrder.keySet()) {
            Order order = Data.allOrder.get(oid);
            order.getInfo(true);
        }
    }
    @Override
    public void listMember() {
        int maxusername = 8;
        int maxmembership = 10;
        int maxphone = 5;
        int maxid = 2;
        int maxname = 4;
        for(String i : Data.allUser.keySet()){
            if(Data.allUser.get(i).getRole().equals("member")){
                maxid = Math.max(String.valueOf(Data.allUser.get(i).getUserId()).length(),maxid);
                maxusername =  Math.max(i.length(),maxusername);
                maxmembership = Math.max(String.valueOf(Data.allUser.get(i).getMemberShip()).length(),maxmembership);
                maxphone =  Math.max(String.valueOf(Data.allUser.get(i).getPhoneNumber()).length(),maxphone);
                maxname =  Math.max(String.valueOf(Data.allUser.get(i).getName()).length(),maxname);
            }
        }
        maxusername -= 7;
        maxmembership -= 9;
        maxphone -= 4;
        maxid -=1;
        maxname -=3;
        int length = (maxid+2)+3+(maxusername+8)+3+(maxname+4) +3 +(maxphone+5)+3+(maxmembership+10) +2;
        printLine(length);
        System.out.printf("| %"+maxid+"sID | %"+maxusername+"sUsername | %"+maxname+"sName | %" + maxphone +"sPhone | %" +maxmembership +"sMembership |\n", "","","","","");
        printLine(length);
        for(String i : Data.allUser.keySet()){
            if(Data.allUser.get(i).getRole().equals("member")){
                System.out.printf("| %"+(maxid+2)+"s | %"+(maxusername+8)+"s | %"+(maxname+4)+"s | %"+ (maxphone+5)+"s | %" +(maxmembership+10) + "s |\n",Data.allUser.get(i).getUserId(),i,Data.allUser.get(i).getName(),Data.allUser.get(i).getPhoneNumber(),Data.allUser.get(i).getMemberShip() );
                printLine(length);
            }
        }
    }
    @Override
    public void addNewProduct() {
        System.out.println("Category: ");
        for (String i : Data.allCategory.keySet()) {
            System.out.println("Category ID :" + i + " Name: " + Data.allCategory.get(i).getCateName());
        }
        System.out.println("Enter new product's category ID:");
        Scanner command = new Scanner(System.in);
        String category = command.nextLine();
        if (!Data.allCategory.containsKey(category)) {
            System.err.println("Category ID not existed!");
            return;
        }
        System.out.println("Enter new product name: ");
        command = new Scanner(System.in);
        String name = command.nextLine();
        for(String pid: Data.allProduct.keySet()){
            if(Data.allProduct.get(pid).getName().equals(name.toUpperCase())){
                System.out.println("Product name existed !");
                return;
            }
        }

        System.out.println("Enter new product price:");
        command = new Scanner(System.in);
        String price = command.nextLine();
        try {
            Double priceDouble = Double.parseDouble(price);
            Product p = new Product(name, priceDouble, category);
            Data.allProduct.put(p.getpId(), p);
            System.out.println("Product added successfully");
        } catch (NumberFormatException nfe) {
            System.err.println("Invalid price input!");
        }
    }

    @Override
    public void updatePrice() {
        System.out.println("Changing product's price \nEnter product ID :");
        Scanner command = new Scanner(System.in);
        String pId = command.nextLine();
        if (Data.allProduct.containsKey(pId)) {
            try {
                System.out.printf("The current price are %11.2f \n Enter update price:", Data.allProduct.get(pId).getPrice());
                command = new Scanner(System.in);
                Double d = Double.parseDouble(command.nextLine());
                Data.allProduct.get(pId).setPrice(d);
                System.out.printf("Price has been updated to %.2f\n", d);
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid input!");
            }
        } else {
            System.err.println("Product ID not exist!");
        }
    }
    @Override
    public void getOrdersByMemberID() throws ClassCastException {
        System.out.println("Enter Member ID: ");
        Scanner command = new Scanner(System.in);
        String userID = command.nextLine();
        if (Data.allUserByID.containsKey(userID)) {
            HashMap<String, Order> orders = Data.allUserByID.get(userID).getOrders();
            if (orders == null) {
                System.out.println("No order placed");
            } else {
                for (String i : orders.keySet()) {
                    orders.get(i).getInfo(true);
                }
            }
        } else {
            System.err.println("Member ID not existed!");
        }
    }
    @Override
    public void changeOrderStatus() {
        System.out.println("Enter order ID: ");
        Scanner command = new Scanner(System.in);
        String oId = command.nextLine();
        if (Data.allOrder.containsKey(oId)) {
            System.out.println("Enter order status for this order id " + oId + ":");
            command = new Scanner(System.in);
            String orderStatus = command.nextLine();
            Data.allOrder.get(oId).setOrderStatus(orderStatus);
        } else {
            System.err.println("Order ID not existed!");
        }
    }

    @Override
    public void command() throws ClassCastException{
        while(Main.currentStatus){
            System.out.println("""
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
                    Main.programStatus = false;
                    Main.currentStatus = false;
                }
                case "1" -> listProducts();
                case "2" -> listOrder();
                case "3" -> listMember();
                case "4" -> addNewProduct();
                case "5" -> updatePrice();
                case "6" -> getOrdersByMemberID();
                case "7" -> changeOrderStatus();
                default -> System.err.println("Invalid command!\n");
            }
            if(!commandString.equals("-1")) pressContinue();
        }
    }
}
