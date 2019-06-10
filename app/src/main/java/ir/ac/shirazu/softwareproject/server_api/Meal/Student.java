package ir.ac.shirazu.softwareproject.server_api.Meal;


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

    public ArrayList<MealInfo> allStudentFoodInfo = new ArrayList<>();

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
                newMeal.setDate_String(date_type[0]);
                //get meal information
                JSONObject foodInfoObject = foodData.get(keySet.get(i));
                int price1 = foodInfoObject.getInt("price1");
                int price2 = foodInfoObject.getInt("price2");
                String foodName1 = foodInfoObject.getString("food_name1");
                String foodName2 = foodInfoObject.getString("food_name2");
                newMeal.setFoods(foodName1, price1, foodName2, price2);


                newMeal.setCouponId(foodInfoObject.getInt("coupon_id"));
                newMeal.state = foodInfoObject.getBoolean("state");
                newMeal.setReservedFoodId(foodInfoObject.getInt("food_id"));
                newMeal.setSelfData(foodInfoObject.getString("self_name"), 0);

                this.allStudentFoodInfo.add(newMeal);

            }

        } catch (Exception e) {

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
/*
    private void showPersonalInfo(){
        System.out.println("FirstName : " + this.firstName);
        System.out.println("LastName : " + this.lastName);
        System.out.println("StudentNumber : " + this.studentNumber);
        System.out.println("Token : " + this.getUser_token());
        System.out.println("Credit : " + this.credit);
        System.out.println("ID : " + this.id);
        System.out.println("\n");
    }
*/
/*
    public void show(){
        System.out.println("\n=================== PERSONAL INFO ===============================\n");
        showPersonalInfo();
        System.out.println("\n=================== FOOD INFO ===============================\n");
        showAllMeals();
    }
    */

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