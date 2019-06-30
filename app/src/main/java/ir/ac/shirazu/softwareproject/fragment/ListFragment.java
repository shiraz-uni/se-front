package ir.ac.shirazu.softwareproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import ir.ac.shirazu.softwareproject.R;
import ir.ac.shirazu.softwareproject.recycler_view.list.ListAdapter;
import ir.ac.shirazu.softwareproject.server_api.Meal.Date;
import ir.ac.shirazu.softwareproject.server_api.Meal.MealInfo;
import ir.ac.shirazu.softwareproject.server_api.Meal.MealName;
import ir.ac.shirazu.softwareproject.server_api.Meal.MealType;
import ir.ac.shirazu.softwareproject.server_api.Meal.MyKit;
import ir.ac.shirazu.softwareproject.server_api.Meal.ReserveState;
import ir.ac.shirazu.softwareproject.server_api.Meal.Utility;

public class ListFragment extends Fragment {

    RecyclerView recyclerView;
    ListAdapter mAdapter;
    Spinner restaurantSpinner, dateSpinner, mealSpinner;
    View mealInformationContainer;
    Button buyMealButton;
    RadioButton firstFood, secondFood;
    RadioGroup foodRadioGroup;
    public ArrayList<MealInfo> datalist = new ArrayList<MealInfo>();
    int[] mealbought;
    String coupon[];
    public static String foundCouponId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = rootView.findViewById(R.id.bought_recycle);
        mealInformationContainer = rootView.findViewById(R.id.meal_information_container);
        buyMealButton = rootView.findViewById(R.id.buy_meal);
        restaurantSpinner = rootView.findViewById(R.id.restaurant_spinner);
        dateSpinner = rootView.findViewById(R.id.date_spinner);
        setDateAdapter();
        setRestaurantAdapter();
        mealSpinner = rootView.findViewById(R.id.meal_spinner);

        firstFood = rootView.findViewById(R.id.first_food);
        secondFood = rootView.findViewById(R.id.second_food);
        foodRadioGroup = rootView.findViewById(R.id.food_radio_group);

        prepareData();
        mAdapter = new ListAdapter(datalist, getActivity());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        onClickListeners(rootView);
        return rootView;
    }

    private void setRestaurantAdapter() {
        Utility utility = Utility.getInstance(getContext().getApplicationContext());
        List<String> list = utility.getSelfsName();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext().getApplicationContext(),
                android.R.layout.simple_spinner_item, list);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        restaurantSpinner.setAdapter(spinnerAdapter);
    }

    private void setDateAdapter() {
        ArrayList<String> list = new ArrayList<>();
        list.add("تاریخ");
        // list.add("شنبه 1398/02/03");
        // ToDo: Add dates here like as following:
        //  list.add("date")
        for (MealInfo e : MealInfo.allAvailableMealInfo) {
            if (list.contains(e.getDate().toString())) continue;
            list.add(e.getDate().toString());

        }


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext().getApplicationContext(), android.R.layout.simple_spinner_item, list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(spinnerAdapter);

    }

    private void onClickListeners(final View rootView) {


        restaurantSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                    updateUI();
                } else {
                    // Your code to process the selection

                    updateUI();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                    updateUI();
                } else {
                    // Your code to process the selection

                    updateUI();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mealSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                    updateUI();
                } else {
                    // Your code to process the selection

                    updateUI();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buyMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ToDo: perform the buy operations here:
                // buy operations
                int slctfood;
                if (foodRadioGroup.getCheckedRadioButtonId() != -1) {
                    int checkedId = foodRadioGroup.getCheckedRadioButtonId();
                    if (checkedId == firstFood.getId()) slctfood = 1;
                    else slctfood = 2;
                    RadioButton radioButton = rootView.findViewById(checkedId); /////?????

                    MealName mlname = MealName.BREAKFAST;
                    if (mealSpinner.getSelectedItem().equals("ناهار")) mlname = MealName.LUNCH;
                    else if (mealSpinner.getSelectedItem().equals("شام")) mlname = MealName.DINNER;
                    else if (mealSpinner.getSelectedItem().equals("صبحانه"))
                        mlname = MealName.BREAKFAST;

                    //Todo add the bought food to recycler view
//                    FoodInfo first = new FoodInfo(firstFood.getText() + "", 1200); ///Handle food  price and  food Id
//
//                    FoodInfo second = new FoodInfo(secondFood.getText() + "", 1300); //also as above
//
//
//                    Self resturant = new Self(1, restaurantSpinner.getSelectedItem() + "");

                    MealInfo selectedMealInfo = MealInfo.allAvailableMealInfo.get(Integer.valueOf(coupon[2]));
                    selectedMealInfo.setReservedFoodId(slctfood);
                    selectedMealInfo.setSelfData(restaurantSpinner.getSelectedItem() + "", restaurantSpinner.getSelectedItemPosition() - 1);
                    selectedMealInfo.setMealType(MealType.NORMAL);
                    selectedMealInfo.setReserveState(ReserveState.EDITABLE_RESERVED);


                    if (mealbought[0] != -1) {
                        if (mealbought[0] != slctfood || mealbought[2] + 1 != restaurantSpinner.getSelectedItemPosition()) {
                            datalist.remove(mealbought[1]);
                            deleteFromAllStudentInfo(foundCouponId);
                            selectedMealInfo.setCouponId(foundCouponId);
                            MyKit.student.allStudentFoodInfo.add(selectedMealInfo);
                            datalist.add(mealbought[1], selectedMealInfo);
                            mAdapter.notifyDataSetChanged();

                            foodRadioGroup.clearCheck();
                            restaurantSpinner.setSelection(0);
                            dateSpinner.setSelection(0);
                            mealSpinner.setSelection(0);
                            buyMealButton.setVisibility(View.GONE);
                        } else
                            Toast.makeText(getContext(), "رستوران یا  غذا را تغییر دهید!", Toast.LENGTH_LONG).show();

                    } else {
                        datalist.add(0, selectedMealInfo);
                        mAdapter.notifyDataSetChanged();
                        MyKit.student.allStudentFoodInfo.add(selectedMealInfo);
                        foodRadioGroup.clearCheck();
                        restaurantSpinner.setSelection(0);
                        dateSpinner.setSelection(0);
                        mealSpinner.setSelection(0);
                        buyMealButton.setVisibility(View.GONE);
                    }


                } else {
                    Toast.makeText(getContext(), "غذا را انتخاب کنید!", Toast.LENGTH_LONG).show();
                }
            }
        });

        foodRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                buyMealButton.setVisibility(View.VISIBLE);
            }
        });

    }

    private void updateUI() {

        if (restaurantSpinner.getSelectedItemPosition() != 0 && dateSpinner.getSelectedItemPosition() != 0 && mealSpinner.getSelectedItemPosition() != 0) {
            mealInformationContainer.setVisibility(View.VISIBLE);
            coupon = getFoodsFromerver(dateSpinner.getSelectedItem().toString(), mealSpinner.getSelectedItem().toString());
            firstFood.setText(coupon[0]);
            secondFood.setText(coupon[1]);
            mealbought = mealBoughtalready(dateSpinner.getSelectedItem().toString(), restaurantSpinner.getSelectedItemPosition(), mealSpinner.getSelectedItem().toString());

            if (mealbought[0] != -1) {
                // Log.d("here","i pressed");
                // Log.d("hereee",mealbought[0]+"");
                if (mealbought[0] == 1) firstFood.setChecked(true);
                else secondFood.setChecked(true);
            } else {
                foodRadioGroup.clearCheck();
            }
            //ToDo send request  to server and fetch the foods which are  available at  that time

            //following piece of scripts are for testing radio buttons

//            firstFood.setText("کوفت");
//            secondFood.setText("زهرمار");

        } else {

            mealInformationContainer.setVisibility(View.GONE);
            foodRadioGroup.clearCheck();
            buyMealButton.setVisibility(View.GONE);
        }
    }

    public void prepareData() {
        for (MealInfo e : MyKit.student.allStudentFoodInfo) {

            Date date = new Date(e.getDate().getDay(), e.getDate().getMonth(), e.getDate().getYear());
            MealInfo tmp = new MealInfo(date, e.getMealName(), null, e.getFirstFood(), e.getSecondFood(), e.getReservedFoodId(), e.getReservedSelf(), MealType.NORMAL);
            tmp.setCouponId(e.getCouponId());
            datalist.add(tmp);

        }


    }

    public String[] getFoodsFromerver(String date, String meal) {
        String[] s = new String[3];
        Date mydate = new Date(date, true);
        MealName mlname = MealName.BREAKFAST;
        if (meal.equals("ناهار")) mlname = MealName.LUNCH;
        else if (meal.equals("شام")) mlname = MealName.DINNER;
        else if (meal.equals("صبحانه")) mlname = MealName.BREAKFAST;

        for (MealInfo e : MealInfo.allAvailableMealInfo) {
            if (e.getDate().compare(mydate) && e.getMealName() == mlname) {
                s[0] = e.getFirstFood().getFoodName() + e.getFirstFood().getFoodPrice();
                s[1] = e.getSecondFood().getFoodName() + e.getSecondFood().getFoodPrice();
                s[2] = MealInfo.allAvailableMealInfo.indexOf(e) + "";
                return s;

            }
        }
        return null;
    }

    public int[] mealBoughtalready(String date, int restaurant, String mealName) {
        int[] tmp = new int[3];
        tmp[0] = -1;

        for (MealInfo e : datalist) {
            if (e.getDate().compare(date) && e.getMealNamePersian().equals(mealName)) {
                tmp[0] = e.getReservedFoodId();
                tmp[1] = datalist.indexOf(e);
                tmp[2] = e.getReservedSelf().getSelfId();
                foundCouponId = e.getCouponId();
                return tmp;

            }
        }
        return tmp;

    }

    public static void deleteFromAllStudentInfo(String a) {

        for (int i = 0; i < MyKit.student.allStudentFoodInfo.size(); i++) {
            if (MyKit.student.allStudentFoodInfo.get(i).getCouponId().equals(a))
                MyKit.student.allStudentFoodInfo.remove(i);
        }
    }


}
