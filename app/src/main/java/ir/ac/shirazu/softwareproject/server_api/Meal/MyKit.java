package ir.ac.shirazu.softwareproject.server_api.Meal;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import ir.ac.shirazu.softwareproject.activity.LoginActivity;
import ir.ac.shirazu.softwareproject.activity.LoginCallBack;

public class MyKit {
    public static Student student;
    private static String loginURL = "http://nowaw.pythonanywhere.com/login/login";
    private static String studentInfoURL = "http://nowaw.pythonanywhere.com/login/self_data";
    private static String logoutURL = "http://nowaw.pythonanywhere.com/login/logout";
    private static String deleteURL = "http://nowaw.pythonanywhere.com/login/delete";
    private static HttpURLConnection connection;
    private static String USER_AGENT = "Mozilla/5.0";
    private static String token;
    private static String requestResult;
    private LoginCallBack loginCallBack;
    private Context context;

    public MyKit(Context context) {
        this.context = context;
    }

    public void setLoginCallBack(LoginActivity loginCallBack) {
        this.loginCallBack = loginCallBack;

    }


    //PRIVATE
    private static void parseFoodInfo(String responseBody, Student student) {

        try {
//            FoodInfo foodInfo = new FoodInfo();
            JSONArray information = new JSONArray(responseBody);
            for (int i = 0; i < information.length(); i++) {
                JSONObject album = information.getJSONObject(i);
                student.setFirstName(album.getString("food_name"));
                student.setLastName(album.getString("food_id"));
                student.setCredit(album.getInt("food_price"));
            }
        } catch (Exception e) {

        }


    }


    private static void parseStudentPersonalInfo(String responseBody, Student student) {
        try {
            JSONArray albums = new JSONArray(responseBody);
            for (int i = 0; i < albums.length(); i++) {
                JSONObject album = albums.getJSONObject(i);
                student.setFirstName(album.getString("first_name"));
                student.setLastName(album.getString("last_name"));
                student.setCredit(album.getInt("credit"));
                //student.setStudentNumber(album.getString("student_no));
                //student.setId(album.getInt("id"));

            }
        } catch (Exception e) {

        }

    }

    private static String loginInformationToJson(String user, String password) {
        return "{\"user\" : " + user + ", \"password\": \"" + password + "\"} ";
    }

    private static String logoutInformationToJson(String token) {
        return "{\"token\" : \"" + token + " \"}";
    }

    private static String tokenToJson(String token) {
        return "{\"token\" : \"" + token + " \"}";
    }


    private static String sendPostRequest(String jsonInformation, String serverURL) throws Exception {

        String url = serverURL;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        String urlParameters = jsonInformation;
        // Send post request
        con.setDoOutput(true);
        //   Thread.sleep(300);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        //Thread.sleep(300);
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.d("HOSSEIN", "\nSending 'POST' request to URL : " + url);
        Log.d("HOSSEIN", "Post parameters : " + urlParameters);
        Log.d("HOSSEIN", "Response Code : " + responseCode);

//        InputStream input = obj.openStream();
        InputStream input = con.getInputStream();

        InputStreamReader inputStreamReader = new InputStreamReader(input);
        BufferedReader in = new BufferedReader(inputStreamReader);
        String inputLine = "";
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String a = response.toString();

        Log.d("responseeee", a);

        if (response.toString().contains("Wrong credentials")) {
            return null;
        } else {
            return response.toString();
        }


    }

    private static void totalInfoToJSONs(String responseBody, HashMap<String, JSONObject> list) {

        try {
            JSONObject information = new JSONObject(responseBody);

            //HashMap<String , JSONObject> totalInfoToJson = new HashMap<>() ;
            list.put("student", information.getJSONObject("student"));
            list.put("coupons", information.getJSONObject("coupons"));
            list.put("self_data", information.getJSONObject("self_data"));
            //list.add(information.getJSONObject("student"));
            //list.add(information.getJSONObject("coupons"));
            //list.add(information.getJSONObject("self_data"));
        } catch (Exception e) {

        }

    }


    private static void fillStudentPersonalData(Student student, JSONObject jsonObject) {
        try {
            student.setFirstName(jsonObject.getString("first_name"));
            student.setLastName(jsonObject.getString("last_name"));
            student.setStudentNumber(jsonObject.getString("student_no"));
            student.setStudentType(jsonObject.getString("student_type"));
            student.setCredit(jsonObject.getInt("credit"));
        } catch (Exception e) {

        }


    }

    private static HashMap<String, JSONObject> parseJsonObjectToHashMap(JSONObject object) {

        Iterator x = object.keys();
        JSONArray jsonArray = new JSONArray();
        ArrayList<String> jsonObjectKeys = new ArrayList<>();
        HashMap<String, JSONObject> hashMap = new HashMap<>();
        try {
            while (x.hasNext()) {
                String key = (String) x.next();
                jsonArray.put(object.get(key));
                jsonObjectKeys.add(key);
                //hashMap.put(key,object.get(key)) ;
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject album = jsonArray.getJSONObject(i);
                //System.out.println("KEY : " + jsonObjectKeys.get(i) + "\n");
                //System.out.println("VALUE : " +  jsonArray.getJSONObject(i) + "\n");
                //System.out.println("\n ================================ \n" );
                hashMap.put(jsonObjectKeys.get(i), jsonArray.getJSONObject(i));
            }

        } catch (Exception e) {

        }

        return hashMap;

    }

    private static void fillStudentMealInfo(Student student, JSONObject coupons) {
        HashMap<String, JSONObject> information = parseJsonObjectToHashMap(coupons);
        student.fillMealInfo(information);
    }

    private static void fillSelfFoodInfo(JSONObject foodInfo) {
        HashMap<String, JSONObject> information = parseJsonObjectToHashMap(foodInfo);
        MealInfo.fillAvailableMeals(information);
    }

    //PUBLIC
    public Student studentLogin(String userName, String password) {
        Student newStudent = new Student(context);


        try {

            //GET Student LOGIN TOKEN
            String userToken = sendPostRequest(loginInformationToJson(userName, password), loginURL);
            if (userToken == null) {
                return null;
            }
            Log.d("HOSSEIN", "Outside SendPostRequest Method");
            newStudent.setUser_token(userToken);


            //Fill Student's PEROSNAL INFO

            String userInformation = sendPostRequest(tokenToJson(userToken), studentInfoURL);


            //JSON Informations
            HashMap<String, JSONObject> list = new HashMap<>();
            totalInfoToJSONs(userInformation, list);


            //Fill Student personal Data
            fillStudentPersonalData(newStudent, list.get("student"));


            //Fill MealInfo
            fillStudentMealInfo(newStudent, list.get("coupons"));


            //Fill Self FoodInfo
            fillSelfFoodInfo(list.get("self_data"));


            //Logging out
            sendPostRequest(tokenToJson(userToken), logoutURL);


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return newStudent;
    }


    protected String logout(String prams[]) {  /////////////This function was former Ondoingbackground()
        String jsonInformation = prams[0];
        String url = prams[1];
        OutputStream out = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setReadTimeout(2000);
            con.setConnectTimeout(2000);
            con.setRequestMethod("POST");
            con.connect();

            BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer response = new StringBuffer();
            String value;
            while ((value = bf.readLine()) != null) {
                response.append(value);
            }
            return response.toString();

        } catch (Exception e) {

        }


        return null;
    }


    public void doLogin(final String user, final String pass) {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    loginCallBack.onPreExecute();
                    MyKit.student = studentLogin(user, pass);
                    loginCallBack.onPostExecute();

                } catch (Exception e) {

                }

            }
        });
        thread.start();
    }


    //Newly Added Functions :


    private void sendIONICPostRequest(JsonObject json, String serverUrl, Context context) {
        Ion.with(context)
                .load(serverUrl)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        requestResult = result.toString();
                    }
                });

    }

    public void deleteCoupon(Student student, String couponId, String studentToken, Context context) throws Exception {

        String foodDeleteJson = deleteInfoToJson(couponId, studentToken);
        sendPostRequest(foodDeleteJson, deleteURL);
        /*
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("coupon_id" , Integer.toString(couponId));
        jsonObject.addProperty("token" , studentToken);
        sendIONICPostRequest(jsonObject , deleteURL , context);
        */
        MealInfo mealInfo = null;
        for (MealInfo meal : student.allStudentFoodInfo) {
            if (meal.getCouponId() == couponId) {
                mealInfo = meal;
                break;
            }
        }

        if (mealInfo != null) {
            student.allStudentFoodInfo.remove(mealInfo);


            int foodPrice = 0;

            if (mealInfo.state == true) {
                foodPrice = mealInfo.getFirstFood().getFoodPrice();
            } else {
                foodPrice = mealInfo.getSecondFood().getFoodPrice();
            }

            student.setCredit(student.getCredit() + foodPrice);
        }

    }

    public static String deleteInfoToJson(String couponId, String studentToken) {

        return "{\"coupon_id\" :\"" + couponId + "\", \"token\" : \"" + studentToken + "\" } ";
    }



}


