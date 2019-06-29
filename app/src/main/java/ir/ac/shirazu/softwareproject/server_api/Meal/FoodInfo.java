package ir.ac.shirazu.softwareproject.server_api.Meal;

public class FoodInfo {
    private String foodName;
    private int foodPrice;


    public FoodInfo(String foodName, int foodPrice, String foodId) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;

    }

    public FoodInfo(String foodName, int foodPrice) {
        this(foodName, foodPrice, null);
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }





    @Override
    public String toString() {
        return foodName.toString();
    }
}