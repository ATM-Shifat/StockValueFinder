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

    public void setName(String newName) {
        this.name = newName;
    }
    public void setValue(double newValue) {
        this.value = newValue;
    }
}
