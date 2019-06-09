package ir.ac.shirazu.softwareproject.server_api.Meal;




import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class MyKit {

    private static String loginURL = "http://nowaw.pythonanywhere.com/login/login" ;
    private static String studentInfoURL = "http://nowaw.pythonanywhere.com/login/self_data" ;
    private static String logoutURL = "http://nowaw.pythonanywhere.com/login/logout" ;
    private static HttpURLConnection connection ;
    private static String USER_AGENT = "Mozilla/5.0";
    private static String token;

    //PRIVATE
    private static void parseFoodInfo (String responseBody , Student student){

        try{
            FoodInfo foodInfo = new FoodInfo();
            JSONArray information = new JSONArray(responseBody);
            for ( int i = 0 ; i < information.length() ; i++){
                JSONObject album = information.getJSONObject(i);
                student.setFirstName(album.getString("food_name"));
                student.setLastName(album.getString("food_id"));
                student.setCredit(album.getInt("food_price"));
            }
        }catch (Exception e){

        }



    }


    private static void parseStudentPersonalInfo(String responseBody , Student student){
        try{
            JSONArray albums = new JSONArray(responseBody);
            for ( int i = 0 ; i < albums.length() ; i++){
                JSONObject album = albums.getJSONObject(i);
                student.setFirstName(album.getString("first_name"));
                student.setLastName(album.getString("last_name"));
                student.setCredit(album.getInt("credit"));
                //student.setStudentNumber(album.getString("student_no));
                //student.setId(album.getInt("id"));

            }
        }
        catch (Exception e){

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


    private static String sendPostRequest(String jsonInformation , String serverURL) throws Exception {
        Log.d("HOSSEIN", "In SendPostRequest Method");
        String url = serverURL;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        Log.d("HOSSEIN", "kir1");
        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        Log.d("HOSSEIN", "kir2");
        String urlParameters = jsonInformation ;

        // Send post request
        Log.d("HOSSEIN", "kir3");
        con.setDoOutput(true);
        Log.d("HOSSEIN", "kir3-1");
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        Log.d("HOSSEIN", "kir3-2");
        wr.writeBytes(urlParameters);
        Log.d("HOSSEIN", "kir3-3");
        wr.flush();
        wr.close();
        Log.d("HOSSEIN", "kir4");
        int responseCode = con.getResponseCode();
        //System.out.println("\nSending 'POST' request to URL : " + url);
        //System.out.println("Post parameters : " + urlParameters);
        //System.out.println("Response Code : " + responseCode);
        Log.d("HOSSEIN", "\nSending 'POST' request to URL : " + url);
        Log.d("HOSSEIN", "Post parameters : " + urlParameters);
        Log.d("HOSSEIN", "Response Code : " + responseCode);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //System.out.println(response.toString());
        return response.toString() ;
    }

    private static void totalInfoToJSONs(String responseBody , HashMap<String,JSONObject> list){

        try{
            JSONObject information = new JSONObject(responseBody);

            //HashMap<String , JSONObject> totalInfoToJson = new HashMap<>() ;
            list.put("student" , information.getJSONObject("student")) ;
            list.put("coupons" , information.getJSONObject("coupons")) ;
            list.put("self_data" , information.getJSONObject("self_data")) ;
            //list.add(information.getJSONObject("student"));
            //list.add(information.getJSONObject("coupons"));
            //list.add(information.getJSONObject("self_data"));
        }
        catch (Exception e){

        }

    }


    private static void fillStudentPersonalData(Student student , JSONObject jsonObject){
        try{
            student.setFirstName(jsonObject.getString("first_name"));
            student.setLastName(jsonObject.getString("last_name"));
            student.setStudentNumber(jsonObject.getString("student_no"));
            student.setSudentType(jsonObject.getString("student_type"));
            student.setCredit(jsonObject.getInt("credit"));
        }
        catch(Exception e){

        }



    }

    private static HashMap<String, JSONObject> parseJsonObjectToHashMap(JSONObject object){

        Iterator x = object.keys();
        JSONArray jsonArray = new JSONArray();
        ArrayList<String> jsonObjectKeys = new ArrayList<>() ;
        HashMap<String,JSONObject> hashMap = new HashMap<>() ;
        try{
            while (x.hasNext()){
                String key = (String) x.next();
                jsonArray.put(object.get(key));
                jsonObjectKeys.add(key);
                //hashMap.put(key,object.get(key)) ;
            }

            for ( int i = 0 ; i < jsonArray.length() ; i++){
                JSONObject album = jsonArray.getJSONObject(i);
                //System.out.println("KEY : " + jsonObjectKeys.get(i) + "\n");
                //System.out.println("VALUE : " +  jsonArray.getJSONObject(i) + "\n");
                //System.out.println("\n ================================ \n" );
                hashMap.put(jsonObjectKeys.get(i) , jsonArray.getJSONObject(i));
            }

        }
        catch (Exception e){

        }

        return hashMap ;

    }

    private static void fillStudentMealInfo(Student student , JSONObject coupons ){

        HashMap <String , JSONObject > information = parseJsonObjectToHashMap(coupons);



        ArrayList<String> keySet = new ArrayList<>() ;

        for ( String key : information.keySet()){
            keySet.add(key);
        }

        for(int i = 0 ; i < information.size() ; i++){
            System.out.println("KEY:" + keySet.get(i));
            System.out.println("VALUE:" + information.get(keySet.get(i)));
        }

        student.fillMealInfo(information);
    }




    //PUBLIC
    public static Student studentLogin(String userName , String password){
        Student newStudent = new Student() ;

        try {

            //GET Student LOGIN TOKEN
            String userToken = sendPostRequest(loginInformationToJson(userName , password) , loginURL ) ;
            Log.d("HOSSEIN", "Outside SendPostRequest Method");
            newStudent.setUser_token(userToken);
            Log.d("HOSSEIN", "we have student with Class : " + newStudent);
            Log.d("HOSSEIN", "Token" + userToken);

            //Fill Student's PEROSNAL INFO

            String userInformation = sendPostRequest(tokenToJson(userToken),studentInfoURL);
            //System.out.println(userInformation);



            //JSON Informations
            HashMap<String,JSONObject> list = new HashMap<>() ;
            totalInfoToJSONs(userInformation,list);

            //Fill Student personal Data
            fillStudentPersonalData(newStudent,list.get("student"));


            //Fill MealInfo
            fillStudentMealInfo(newStudent,list.get("coupons"));

            //Show All Data
            //newStudent.show();

            //Logout
            System.out.println("Logging Out");
            sendPostRequest(tokenToJson(userToken),logoutURL);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newStudent ;
    }


    public void test(){
        Log.d("HOSSEIN", "test: ");
        Student newStudent = MyKit.studentLogin("10001" , "123");
        if (newStudent == null ){
            Log.d("HOSSEIN", "kir shodi");
        }
        Log.d("HOSSEIN", newStudent.getFirstName());
        Log.d("HOSSEIN", newStudent.getLastName());
        Log.d("HOSSEIN", newStudent.getStudentNumber());
        Log.d("HOSSEIN", newStudent.getSudentType());
    }

}