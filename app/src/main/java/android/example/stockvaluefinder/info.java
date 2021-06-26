package android.example.stockvaluefinder;

public class info {
    String name,phone,bank;
    double points;



    public info(String name, String phone, String bank,double points) {
        this.name = name;
        this.phone = phone;
        this.bank = bank;
        this.points=points;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getBank() {
        return bank;
    }
    public double getPoints() {
        return points;
    }
}
