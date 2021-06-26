package android.example.stockvaluefinder;

public class Modelclassfor_recycle {
    public double price;
    public String company_name;
    public double changePercent;
    public String ticker;



    public Modelclassfor_recycle(double price, String company_name, double changePercent,String ticker) {
        this.price = price;
        this.company_name = company_name;
        this.ticker=ticker;
        this.changePercent=changePercent;

    }


    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(double changePercent) {
        this.changePercent = changePercent;
    }

    public double getPrice() {
        return price;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
