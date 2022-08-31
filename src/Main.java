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
    static User currentUser =new User(); //GUEST
    static boolean programStatus = true;
    static boolean currentStatus = true;
    static boolean login = false;
    static void run() throws IOException, ClassNotFoundException {
        welcome();
//        System.out.println(Data.allUser);
            currentUser.command();
            if(login) {
                currentStatus =true;
                currentUser.command();
            }
        end();
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException,ClassCastException {
//        Data.createSampleData();
        run();

    }
}


