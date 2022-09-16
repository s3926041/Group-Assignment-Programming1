import java.io.*;

public class Main {
    static void welcome() throws IOException, ClassNotFoundException {
        System.out.println(
                """
                        COSC2081 GROUP ASSIGNMENT\s
                        STORE ORDER MANAGEMENT SYSTEM\s
                        Instructor: Mr. Minh Vu\s
                        Group: Astraloss\s
                        s3926041, Nguyen Thanh Hung\s
                        s3926634, Nguyen Huy Cao Son\s
                        s3929899, Nguyen The Duc\s
                        """
        );
        Data.read();
        User.pressContinue();
    }
    static void end() throws IOException {
        Data.write();
        System.out.println("Thanks for shopping with us!");
    }
    static User currentUser =new User(); //GUEST
    static boolean currentStatus = true;
    public static void main(String[] args) throws IOException, ClassNotFoundException,ClassCastException {
        welcome();
        while(currentStatus){
            currentUser.command();
        }
        end();
    }
}


