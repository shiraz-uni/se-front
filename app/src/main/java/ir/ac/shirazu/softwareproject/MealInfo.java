package ir.ac.shirazu.softwareproject;

import java.io.Serializable;

public class MealInfo implements Serializable {
    private Date date;
    private MealType mealType;
    private ReserveState reserveState;
    private FoodInfo firstFood, secondFood;
    private int reservedFoodId;
    private Self reservedSelf;

    public MealInfo(Date date, MealType mealType, ReserveState reserveState, FoodInfo firstFood,
                    FoodInfo secondFood, int reservedFoodId, Self reservedSelf) {
        this.date = date;
        this.mealType = mealType;
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
    }

    /**
     * This will be useful when the user did not reserve a food.
     * Following properties will set to:
     * <p>
     * reservedFoodId : -1
     * <p>
     * reservedSelf : null
     */
    public MealInfo(Date date, MealType mealType, ReserveState reserveState, FoodInfo firstFood, FoodInfo secondFood) {
        this(date, mealType, reserveState, firstFood,
                secondFood, -1, null);
    }

    /**
     * This will be useful when the state of this meal is UNPLANNED.
     * Following properties will set to:
     * <p>
     * reservedFoodId : -1
     * <p>
     * reservedSelf : firstFood : secondFood : null
     */
    public MealInfo(Date date, MealType mealType, ReserveState reserveState) {
        this(date, mealType, reserveState, null,
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

    public FoodInfo getReservedFoodInfo() {
        return reservedFoodId == firstFood.getFoodId() ? firstFood : secondFood;
    }

    public MealType getMealType() {
        return mealType;
    }

    public String getMealName() {
        String name = "";
        switch (mealType) {
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

}
