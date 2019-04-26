package ir.ac.shirazu.softwareproject.recycler_view.list;

public class ListItem {

    private String day;
    private String date;
    private String food;
    private String meal;
    private String type;
    private String price;

    public ListItem(String day, String date, String food, String meal, String tyoe, String price) {
        this.day = day;
        this.date = date;
        this.food = food;
        this.meal = meal;
        this.type = type;
        this.price = price;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getType() {
        return type;
    }

    public void setTyoe(String tyoe) {
        this.type = tyoe;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
