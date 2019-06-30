package ir.ac.shirazu.softwareproject.server_api.Meal;


import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {

    private String firstName, lastName;
    private String user_token;
    private String studentNumber;
    private int credit;
    private int id;
    private String studentType;
    private Context context;
    private Self self;

    public ArrayList<MealInfo> allStudentFoodInfo = new ArrayList<>();

    public Student(Context context){
        this.context = context;
    }

    public void fillMealInfo(HashMap<String, JSONObject> foodData) {
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


                newMeal.setCouponId(foodInfoObject.getString("coupon_id"));
                newMeal.state = foodInfoObject.getBoolean("state");
                if  ( newMeal.state == true ){
                    newMeal.setReservedFoodId(1);
                }
                else newMeal.setReservedFoodId(2);
                //newMeal.setReservedFoodId(foodInfoObject.getString("food_id"));
                int id = Integer.valueOf(foodInfoObject.getString("self_name"));
                self = Utility.getInstance(context).getSelf(id);
                newMeal.setSelfData(self.getSelfName(), self.getSelfId());
                this.allStudentFoodInfo.add(newMeal);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
        private void showAllMeals(){
            int i = 1 ;
            for(MealInfo meal : allStudentFoodInfo){
                System.out.println("+++++++ Meal(" + (i++)  +") +++++++\n");
                meal.show();
            }
        }
    */
    public String getSudentType() {
        return studentType;
    }

    public void setSudentType(String studentType) {
        this.studentType = studentType;
    }

    public ArrayList<MealInfo> getAllStudentFoodInfo() {
        return allStudentFoodInfo;
    }

    public void setAllStudentFoodInfo(ArrayList<MealInfo> allStudentFoodInfo) {
        this.allStudentFoodInfo = allStudentFoodInfo;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}