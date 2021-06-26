package android.example.stockvaluefinder;

public class model {
    String companyName;
    double latestPrice;
    double changePercent;
    String symbol;


    public model(String companyName, double latestPrice, double changePercent, String symbol) {
        this.symbol=symbol;
        this.companyName = companyName;
        this.latestPrice = latestPrice;
        this.changePercent=changePercent;
    }
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    public String getCompanyName() {
        return companyName;
    }

    public double getLatestPrice() {
        return latestPrice;
    }

    public double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(double changePercent) {
        this.changePercent = changePercent;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setLatestPrice(double latestPrice) {
        this.latestPrice = latestPrice;
    }
}
