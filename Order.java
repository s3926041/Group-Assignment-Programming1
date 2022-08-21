// package untitled;
import java.io.Serializable;
import java.util.HashMap;

public class Order implements Serializable {
    public static void setCurrentOrderId(Integer currentOrderId) {
        Order.currentOrderId = currentOrderId;
    }

    private static Integer currentOrderId=0;
    private Double totalPrice;

    public Double getPricebf() {
        return pricebf;
    }
    private Double pricebf;
    private String oId;
    private String cId;
    private HashMap<Product,Integer> orderDetails; // PRODUCT AND QUANTITY

    private String orderStatus="UNPAID";

    public Order(String cId, HashMap<Product,Integer> orderDetails,Double totalPrice,Double pricebf) {
        this.pricebf = pricebf;
        this.cId = cId;
        this.orderDetails = orderDetails;
        this.totalPrice = totalPrice;
        Data.allOrder.put(this.oId,this);
        Data.allMemberByID.get(cId).addOrder(this);
        Data.allMember.get(Data.allMemberByID.get(cId).getUsername()).addOrder(this);
        this.oId = String.valueOf(currentOrderId);
        currentOrderId++;
        Data.currentID.put("order",currentOrderId);
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
    public void getInfo(){
        System.out.printf("ID: %3s | Customer ID: %3s | Total Price: %11.0f | Order Status: %8s\n",this.oId,this.cId,this.totalPrice,this.orderStatus);
    }
}

