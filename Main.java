
public class Main {
    public static void main(String[] args) {
        RateType rate = RateType.NEW;
        System.out.println("RateType: " + rate + ", Value: " + rate.getValue());

        rate = RateType.SECONDHAND;
        System.out.println("RateType: " + rate + ", Value: " + rate.getValue());
    }
}
