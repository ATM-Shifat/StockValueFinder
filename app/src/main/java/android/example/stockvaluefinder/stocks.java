package android.example.stockvaluefinder;

public class stocks {
    String company;
    String ticker;
    Double buy_price;
    int count;

    public stocks(String company, Double buy_price, int count, String t) {
        this.company = company;
        this.buy_price = buy_price;
        this.count = count;
        this.ticker=t;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCompany() {
        return company;
    }

    public Double getBuy_price() {
        return buy_price;
    }

    public int getCount() {
        return count;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setBuy_price(Double buy_price) {
        this.buy_price = buy_price;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
