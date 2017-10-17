package nl.sandhoofd.contactenapp;

/**
 * Created by svanh on 9-10-2017.
 */

public class Quote {
    private String Quote, category;

    public Quote() {
    }

    public String getQuote() {
        return Quote;
    }

    public void print_quote() {
        System.out.println(Quote + ", " + category);
    }

    public void setQuote(String quote) {
        Quote = quote;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
