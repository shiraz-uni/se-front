package ir.ac.shirazu.softwareproject.server_api.Meal;

public enum MealType {
    EMERGENCY , NORMAL ,  FORGET;

    @Override
    public String toString() {
        if(this == NORMAL){
            return "عادی";
        }
        else if(this == EMERGENCY){
            return "اضطراری";
        }
        else {
            return "فراموشی";
        }
    }
}
