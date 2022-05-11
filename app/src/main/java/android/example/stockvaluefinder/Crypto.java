package android.example.stockvaluefinder;

public class Crypto {
    private String name;
    private double value;

    public Crypto(){
        name = "NotSet";
        value = 0;
    }
    public String getName() {
        return name;
    }
    public double getValue() {
        return value;
    }

    public void setName(String oneName) {
        this.name = oneName;
    }
    public void setValue(double oneValue) {
        this.value = oneValue;
    }
}
