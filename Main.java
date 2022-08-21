// package untitled;

import java.io.*;
import java.util.*;

public class Main {

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
        Data.read();
    }
    static void end() throws IOException {
        Data.write();
        System.out.println("Thanks for shopping with us!");
    }
    static void pressContinue(){
        System.out.println("Press Enter key to continue...");
        Scanner s = new Scanner(System.in);
        s.nextLine();
    }
    static void roleCommandGuest(){
        while(currentStatus){
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
                case ("-1") -> {
                    programStatus = false;
                    currentStatus = false;
                }
                case ("1") -> {currentUser.register();pressContinue();}
                case ("2") -> {currentUser.login();pressContinue();}
                case ("3") -> {currentUser.listProducts();pressContinue();}
                case ("4") -> {currentUser.viewProductDetails();pressContinue();}
                case ("5") -> {currentUser.listProductsbyCate();pressContinue();}
                case ("6") -> {currentUser.sortProduct();pressContinue();}
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
                case ("1") -> {currentUser.viewInformation("main"); pressContinue();}
                case ("2") -> {currentUser.createOrder();pressContinue();}
                case ("3") -> {currentUser.listProducts();    pressContinue();}
                case ("4") -> {currentUser.viewProductDetails();  pressContinue();}
                case ("5") -> {currentUser.listProductsbyCate();   pressContinue();}
                case ("6") -> {currentUser.sortProduct();     pressContinue();}
                case ("7") -> {currentUser.getOrderByOID();   pressContinue();}
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
                case "1" -> {currentUser.listProducts(); pressContinue();}
                case "2" -> {currentUser.listOrder(); pressContinue();}
                case "3" -> {currentUser.listMember(); pressContinue();}
                case "4" -> {currentUser.addNewProduct(); pressContinue();}
                case "5" -> {currentUser.updatePrice(); pressContinue();}
                case "6" -> {currentUser.getOrdersByMemberID(); pressContinue();}
                case "7" -> {currentUser.changeOrderStatus(); pressContinue();}
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

    
    public static void main(String[] args) throws IOException, ClassNotFoundException,ClassCastException {
        welcome();
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
        end();
//        Data.createSampleData();
    }
}


