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
import java.util.Arrays;
import java.util.List;

import ir.ac.shirazu.softwareproject.R;
import ir.ac.shirazu.softwareproject.recycler_view.list.ListAdapter;
import ir.ac.shirazu.softwareproject.recycler_view.list.ListItem;

public class ListFragment extends Fragment {

    RecyclerView recyclerView;
    ListAdapter mAdapter;
    Spinner resturantSpinner, dateSpinner, mealSpinner;
    View mealInformationContainer;
    Button buyMealButton;
    RadioButton firstFood, secondFood;
    RadioGroup foodRadioGroup;
    public static List<ListItem> datalist;

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
        resturantSpinner = rootView.findViewById(R.id.restaurant_spinner);
        dateSpinner = rootView.findViewById(R.id.date_spinner);
        setDateAdapter();
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

    private void setDateAdapter() {
        ArrayList<String> list = new ArrayList<>();
        list.add("تاریخ");
        list.add("1398/02/26");
        // ToDo: Add dates here like as following:
        //  list.add("date")


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(spinnerAdapter);
    }

    private void onClickListeners(final View rootView) {


        resturantSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                int checkedId = foodRadioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = rootView.findViewById(checkedId);
                String selectedFood = radioButton.getText().toString();

                ListItem boughtItem = new ListItem("چهارشنبه", dateSpinner.getSelectedItem().toString(),
                        selectedFood, mealSpinner.getSelectedItem().toString(), "عادی", "2550",
                        resturantSpinner.getSelectedItem().toString());
                datalist.add(boughtItem);
                mAdapter.notifyDataSetChanged();


                foodRadioGroup.clearCheck();
                resturantSpinner.setSelection(0);
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
        if (resturantSpinner.getSelectedItemPosition() != 0 && dateSpinner.getSelectedItemPosition() != 0 && mealSpinner.getSelectedItemPosition() != 0)
            mealInformationContainer.setVisibility(View.VISIBLE);
        else {
            mealInformationContainer.setVisibility(View.GONE);
            foodRadioGroup.clearCheck();
            buyMealButton.setVisibility(View.GONE);
        }
    }

    public static void prepareData() {
        datalist = new ArrayList<>();
        datalist.add(new ListItem("شنبه", "1398/02/28", "چلو کباب با دلستر", "ناهار"
                , "عادی", "3800", "غذاخوری دانشکده نفت و گاز"));
        datalist.add(new ListItem("دوشنبه", "1398/02/30", "پلو خورشت قیمه با ژله", "شام"
                , "اضطراری", "4500", "سالن غذاخوری دست غیب(بیرون بر)"));


    }
}
