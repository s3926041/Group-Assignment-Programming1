import java.util.*;

public class Test {
    public static void main(String[] args) {
        HashMap<String,Cl> arr= new HashMap<>();
        Cl cl1 = new Cl(1);
        arr.put("1",cl1);
        HashMap<String,Cl> arr2 = new HashMap<>();
        arr2.put("1",cl1);
        arr.get("1").id = 3;
        System.out.println(arr2.get("1").id);
    }

}
class Cl{
    public int id ;
    public Cl(int id){
        this.id = id;
    }
}