
import java.io.Serializable;
import java.util.HashMap;

public class Order implements Serializable {
    public static void setCurrentOrderId(Integer currentOrderId) {
        Order.currentOrderId = currentOrderId;
    }
    private static Integer currentOrderId=0;
    private Double totalPrice;
    private Double pricebf;
    private String oId;
    private User member;
    private HashMap<Product, HashMap<String,Double>>  orderDetails; // PRODUCT AND (QUANTITY AND CURRENT PRODUCT PRICE)
    private String orderStatus="UNPAID";
    public Order(User member,HashMap<Product, HashMap<String,Double>>   orderDetails,Double totalPrice,Double pricebf) {
        this.pricebf = pricebf;
        this.member = member;
        this.orderDetails = orderDetails;
        this.totalPrice = totalPrice;
        this.oId = String.valueOf(currentOrderId);
        currentOrderId++;
        Data.currentID.put("order",currentOrderId);
        Data.allOrder.put(this.oId,this);
        member.addOrder(this);
    }
   

    public String getoId() {
        return this.oId;
    }
    public HashMap<Product, HashMap<String,Double>>  getOrderDetails() {
        return this.orderDetails;
    }
    public Double getTotalPrice() {
        return this.totalPrice;
    }
    public void setOrderStatus(String orderStatus){
        this.orderStatus =  orderStatus;
    }
    public void getInfo(boolean req){
        System.out.println("Order ID: " + this.oId);
        if(req) System.out.println("Customer ID: " + this.member.getUserId());
        System.out.println("Details: ");
        int maxQuantity = 8;
        int maxProductId = 10;
        int maxProductName = 4;
        int maxProductPrice = 9;
        //product id product price quantity product name order status length
            for(Product p : orderDetails.keySet()){
                maxProductId =Math.max(maxProductId, p.getpId().length());
                maxProductName =Math.max(maxProductName, p.getName().length());
                maxQuantity =Math.max(maxQuantity, String.valueOf(orderDetails.get(p).get("quantity")).length()-2);
                maxProductPrice =Math.max(maxProductPrice, String.valueOf(orderDetails.get(p).get("price")*orderDetails.get(p).get("quantity")).length());
            }
        maxProductId -= 9;
        maxProductName -= 3;
        maxQuantity -= 7;
        maxProductPrice -=6;
        int length = (maxProductId+10)+3+(maxProductName+4) +3 +(maxProductPrice+9) +3 + (maxQuantity+8) +2;
        User.printLine(length);
        System.out.printf("| %"+maxProductId+"sProduct ID | %"+maxProductName+"sName | %"+maxQuantity+"sQuantity | %"+maxProductPrice+"sSub Price |\n", "","","","");
        User.printLine(length);
            for(Product p : orderDetails.keySet()){
                System.out.printf("| %"+(maxProductId+10)+"s | %"+(maxProductName+4)+"s | %"+(maxQuantity+8)+".0f | %"+(maxProductPrice+9)+".2f |\n",   p.getpId(),p.getName(),orderDetails.get(p).get("quantity"),orderDetails.get(p).get("price")*orderDetails.get(p).get("quantity"));
                User.printLine(length);
        }
        System.out.printf("Total price before promotion: %.2f\n",this.pricebf);
        System.out.printf("Total price after promotion: %.2f\n",this.totalPrice);
        System.out.println();
    }
}

