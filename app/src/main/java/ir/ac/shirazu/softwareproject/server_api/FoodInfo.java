package ir.ac.shirazu.softwareproject.server_api;

public class FoodInfo {
    private String foodName;
    private int foodPrice;
    private int foodId;

    public FoodInfo(String foodName, int foodPrice, int foodId) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodId = foodId;
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

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    @Override
    public String toString() {
        return foodName.toString();
    }
}
