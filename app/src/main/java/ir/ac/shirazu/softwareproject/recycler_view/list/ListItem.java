package ir.ac.shirazu.softwareproject.recycler_view.list;

public class ListItem {

    private String day;
    private String date;
    private String food;
    private String meal;
    private String type;
    private String price;
    private String reservedPlace;

    public ListItem(String day, String date, String food, String meal,
                    String type, String price, String reservedPlace) {
        this.day = day;
        this.date = date;
        this.food = food;
        this.meal = meal;
        this.type = type;
        this.price = price;
        this.reservedPlace = reservedPlace;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getReservedPlace() {
        return reservedPlace;
    }

    public void setReservedPlace(String reservedPlace) {
        this.reservedPlace = reservedPlace;
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

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
