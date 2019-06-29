package ir.ac.shirazu.softwareproject.server_api.Meal;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MealInfo implements Serializable {
    private Date date;
    private MealName mealName;
    private ReserveState reserveState;
    private FoodInfo firstFood, secondFood;
    private int reservedFoodId;
    private Self reservedSelf;
    private MealType mealType;
    private String couponId;
    public Boolean state;
    public static ArrayList<MealInfo> allAvailableMealInfo = new ArrayList<>();


    public MealInfo(Date date, MealName mealName, ReserveState reserveState, FoodInfo firstFood,
                    FoodInfo secondFood, int reservedFoodId, Self reservedSelf, MealType mealType) {
        this.date = date;
        this.mealName = mealName;
        this.reserveState = reserveState;
        if (reserveState == ReserveState.UNPLANNED) {
            firstFood = secondFood = null;
            reservedSelf = null;
            reservedFoodId = -1;
        }

        this.firstFood = firstFood;
        this.secondFood = secondFood;
        this.reservedFoodId = reservedFoodId;
        this.reservedSelf = reservedSelf;
        this.mealType = mealType;
    }

    /**
     * This will be useful when the user did not reserve a food.
     * Following properties will set to:
     * <p>
     * reservedFoodId : -1
     * <p>
     * reservedSelf : null
     */
    public MealInfo(Date date, MealName mealName, ReserveState reserveState, FoodInfo firstFood, FoodInfo secondFood) {
        this(date, mealName, reserveState, firstFood,
                secondFood, -1, null, null);
    }

    /**
     * This will be useful when the state of this meal is UNPLANNED.
     * Following properties will set to:
     * <p>
     * reservedFoodId : -1
     * <p>
     * reservedSelf : firstFood : secondFood : null
     */
    public MealInfo(Date date, MealName mealName, ReserveState reserveState) {
        this(date, mealName, reserveState, null,
                null);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isReserved() {
        if (reserveState != null) {
            if (reserveState == ReserveState.EDITABLE_RESERVED ||
                    reserveState == ReserveState.NON_EDITABLE_RESERVED)
                return true;
        }
        return false;
    }

//    public FoodInfo getReservedFoodInfo() {
//        return reservedFoodId == firstFood.getFoodId() ? firstFood : secondFood;
//    }

    public MealName getMealName() {
        return mealName;
    }

    public String getMealNamePersian() {
        String name = "";
        switch (mealName) {
            case BREAKFAST:
                name = "صبحانه";
                break;
            case LUNCH:
                name = "ناهار";
                break;
            case DINNER:
                name = "شام";
                break;
        }
        return name;
    }

    public FoodInfo getFirstFood() {
        return firstFood;
    }

    public FoodInfo getSecondFood() {
        return secondFood;
    }

    public ReserveState getReserveState() {
        return reserveState;
    }

    public void setReserveState(ReserveState reserveState) {
        this.reserveState = reserveState;
    }

    public int getReservedFoodId() {
        return reservedFoodId;
    }

    public void setReservedFoodId(int reservedFoodId) {
        this.reservedFoodId = reservedFoodId;
    }

    public Self getReservedSelf() {
        return reservedSelf;
    }

    public void setReservedSelf(Self reservedSelf) {
        this.reservedSelf = reservedSelf;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    //Hossein's Edits
    public void setMealName(String mealNamestr) {

        switch (mealNamestr) {
            case "dinner":
                this.mealName = MealName.DINNER;
                break;

            case "lunch":
                this.mealName = MealName.LUNCH;
                break;
            case "breakfast":
                this.mealName = MealName.BREAKFAST;
                break;
        }
    }

    public void setSelfData(String selfName, int selfId) {
        this.reservedSelf = new Self();
        this.reservedSelf.setSelfName(selfName);
        this.reservedSelf.setSelfId(selfId);
    }

    public FoodInfo getReservedFoodInfo() {
        if  (reservedFoodId  == 1 ) return firstFood ;
        else return secondFood;
    }



    public void setFoods(String firstFoodName, int firstFoodPrice, String secondFoodName, int secondFoodPrice) {
        this.firstFood = new FoodInfo(firstFoodName,firstFoodPrice);
        this.secondFood = new FoodInfo(secondFoodName,secondFoodPrice);
    }

    public void setSelf(String selfName) {
        this.reservedSelf.setSelfName(selfName);
    }

    public MealInfo() {
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public static void fillAvailableMeals(HashMap<String, JSONObject> foodData){
        try {
            ArrayList<String> keySet = new ArrayList<>();

            for (String key : foodData.keySet()) {
                keySet.add(key);
            }

            for (int i = 0; i < foodData.size(); i++) {
                //create meal
                MealInfo newMeal = new MealInfo();
                //fill meal date and type
                String[] date_type = keySet.get(i).split("_");
                newMeal.setMealName(date_type[1]);
                newMeal.setDate(new Date(date_type[0],false));

                //get meal information
                JSONObject foodInfoObject = foodData.get(keySet.get(i));
                int price1 = foodInfoObject.getInt("price1");
                int price2 = foodInfoObject.getInt("price2");
                String foodName1 = foodInfoObject.getString("food_name1");
                String foodName2 = foodInfoObject.getString("food_name2");
                newMeal.setFoods(foodName1, price1, foodName2, price2);
                newMeal.setCouponId(foodInfoObject.getString("key_id"));
                allAvailableMealInfo.add(newMeal);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}