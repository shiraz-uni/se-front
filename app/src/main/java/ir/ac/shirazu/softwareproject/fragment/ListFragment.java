package ir.ac.shirazu.softwareproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ir.ac.shirazu.softwareproject.Date;
import ir.ac.shirazu.softwareproject.FoodInfo;
import ir.ac.shirazu.softwareproject.MealInfo;
import ir.ac.shirazu.softwareproject.MealName;
import ir.ac.shirazu.softwareproject.MealType;
import ir.ac.shirazu.softwareproject.R;
import ir.ac.shirazu.softwareproject.ReserveState;
import ir.ac.shirazu.softwareproject.Self;
import ir.ac.shirazu.softwareproject.Utility;
import ir.ac.shirazu.softwareproject.recycler_view.list.ListAdapter;
import ir.ac.shirazu.softwareproject.recycler_view.list.ListItem;

public class ListFragment extends Fragment {

    RecyclerView recyclerView;
    ListAdapter mAdapter;
    Spinner restaurantSpinner, dateSpinner, mealSpinner;
    View mealInformationContainer;
    Button buyMealButton;
    RadioButton firstFood, secondFood;
    RadioGroup foodRadioGroup;
    public static ArrayList<MealInfo> datalist = new ArrayList<MealInfo>()  ;


    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
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
        mAdapter = new ListAdapter(datalist);
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
        list.add("شنبه 1398/02/03");
        // ToDo: Add dates here like as following:
        //  list.add("date")


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
                int slctfood ;
                int checkedId = foodRadioGroup.getCheckedRadioButtonId();
                if(checkedId == firstFood.getId()) slctfood  =1;
                else slctfood = 2;
                RadioButton radioButton = rootView.findViewById(checkedId); /////?????

                MealName mlname  = MealName.BREAKFAST;
                if (mealSpinner.getSelectedItem().equals("ناهار")) mlname = MealName.LUNCH;
                else if (mealSpinner.getSelectedItem().equals("شام")) mlname = MealName.DINNER;
                else if (mealSpinner.getSelectedItem().equals("صبحانه"))  mlname = MealName.BREAKFAST;


                FoodInfo first = new FoodInfo(firstFood.getText()+"",1200,1); ///Handle food  price and  food Id

                FoodInfo second = new FoodInfo(secondFood.getText()+"",1300,2); //also as above


                Self resturant = new Self(1,restaurantSpinner.getSelectedItem()+"");



                MealInfo temp = new MealInfo(new Date(dateSpinner.getSelectedItem().toString()),mlname,ReserveState.EDITABLE_RESERVED,first,second,slctfood,resturant,MealType.NORMAL);

                int [] x= mealBoughtalready(dateSpinner.getSelectedItem().toString() , restaurantSpinner.getSelectedItemPosition() , mealSpinner.getSelectedItem().toString());

                if(x[0] != -1 ){
                    if (x[0] != slctfood) {
                        datalist.remove(x[1]);
                        datalist.add(0, temp);
                        mAdapter.notifyDataSetChanged();
                    }
                }
                else {
                    datalist.add(0, temp);
                    mAdapter.notifyDataSetChanged();
                }

                foodRadioGroup.clearCheck();
                restaurantSpinner.setSelection(0);
                dateSpinner.setSelection(0);
                mealSpinner.setSelection(0);
                buyMealButton.setVisibility(View.GONE);










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
            int [] mealbought = mealBoughtalready(dateSpinner.getSelectedItem().toString() , restaurantSpinner.getSelectedItemPosition() , mealSpinner.getSelectedItem().toString());
            if(mealbought[0] != -1) {
               // Log.d("here","i pressed");
               // Log.d("hereee",mealbought[0]+"");
                if ( mealbought[0] == 1) firstFood.setChecked(true);
                else secondFood.setChecked(true);
            }
            else {
                foodRadioGroup.clearCheck();
            }
            //ToDo send request  to server and fetch the foods which are  available at  that time

            //following piece of scripts are for testing radio buttons

            firstFood.setText("کوفت");
            secondFood.setText("زهرمار");

        }
        else {

            mealInformationContainer.setVisibility(View.GONE);
            foodRadioGroup.clearCheck();
            buyMealButton.setVisibility(View.GONE);
        }
    }

    public static void prepareData() {

        datalist.add(new MealInfo(new Date(3,2,1398), MealName.BREAKFAST, ReserveState.EDITABLE_RESERVED,new FoodInfo("کوفت",1200,1),new FoodInfo("زهرمار",1300,2),1,new Self(2, "غذاخوري دانشكده مهندسي نفت و گاز"), MealType.NORMAL));


        datalist.add(new MealInfo(new Date(2,2,1398), MealName.BREAKFAST, ReserveState.EDITABLE_RESERVED,new FoodInfo("زهرمار",1200,1),new FoodInfo("فاک پلو",1300,2),2,new Self(12, "سلف ارم"), MealType.NORMAL));


        datalist.add(new MealInfo(new Date(5,2,1398), MealName.BREAKFAST, ReserveState.EDITABLE_RESERVED,new FoodInfo("کوفت",1200,1),new FoodInfo("زهرمار",1300,2),2,new Self(12,"دانشکده معماری"), MealType.NORMAL));












    }

    public int[] mealBoughtalready(String date , int restaurant  , String mealName ){
        int [] tmp = new int[2];
        tmp[0] = -1;

        for (MealInfo e : datalist){
            if (e.getDate().compare(date) && restaurant == e.getReservedSelf().getSelfId() && e.getMealNamePersian().equals(mealName) ){
                tmp[0] = e.getReservedFoodId();
                tmp[1] = datalist.indexOf(e);
                return tmp;

            }
        }
        return tmp;

    }
}
