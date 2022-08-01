// package untitled;
import java.io.Serializable;
import java.util.HashMap;

public class Order implements Serializable {
    private Double totalPrice;

    public Double getPricebf() {
        return pricebf;
    }

    private Double pricebf;
    private static int totalOrder ;
    private String oId;
    private String cId;
    private HashMap<Product,Integer> orderDetails; // PRODUCT AND QUANTITY

    private String orderStatus="UNPAID";

    public Order(String cId, HashMap<Product,Integer> orderDetails,Double totalPrice,Double pricebf) {
        this.pricebf = pricebf;
        this.oId = String.valueOf(totalOrder);
        this.cId = cId;
        this.orderDetails = orderDetails;
        this.totalPrice = totalPrice;
        Main.allOrder.put(this.oId,this);
        Main.allMemberByID.get(cId).addOrder(this);
        Main.allMember.get(Main.allMemberByID.get(cId).getUsername()).addOrder(this);
        totalOrder++;
    }
   

    public String getoId() {
        return this.oId;
    }
    public HashMap<Product,Integer> getOrderDetails() {
        return this.orderDetails;
    }
    public Double getTotalPrice() {
        return this.totalPrice;
    }
    public void setOrderStatus(String orderStatus){
        this.orderStatus =  orderStatus;
    }
    public static void setTotalOrder(int totalOrder) {
        Order.totalOrder = totalOrder;
    }


    public void getInfo(){
        System.out.printf("ID: %3s | Customer ID: %3s | Total Price: %11.0f | Order Status: %8s\n",this.oId,this.cId,this.totalPrice,this.orderStatus);
    }
}

