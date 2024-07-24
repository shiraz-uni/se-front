package ir.ac.shirazu.softwareproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.ac.shirazu.softwareproject.R;
import ir.ac.shirazu.softwareproject.recycler_view.weekly.WeeklyAdapter;
import ir.ac.shirazu.softwareproject.recycler_view.weekly.WeeklyItem;
import ir.ac.shirazu.softwareproject.server_api.Meal.Date;
import ir.ac.shirazu.softwareproject.server_api.Meal.MealInfo;
import ir.ac.shirazu.softwareproject.server_api.Meal.MealName;
import ir.ac.shirazu.softwareproject.server_api.Meal.MyKit;


public class WeeklyFragment extends Fragment {
    private static final String ITMES_KEY = "WEEKLY ITEMS";
    private Button nextWeekBtn, previousWeekBtn;

    private ArrayList<WeeklyItem> items;
    ArrayList<MealInfo> reservedMeals = MyKit.student.allStudentFoodInfo;
    ArrayList<MealInfo> availableMeals = MealInfo.allAvailableMealInfo;


    public WeeklyFragment() {
        items = new ArrayList<>(7);

//        Date firstDayOfWeek = Date.getFirstDayOfThisWeek();

    }

    private MealInfo isExistInReserved(Date date, MealName mealName, List<MealInfo> reservedMeals) {
        for (MealInfo reservedMeal : reservedMeals) {
            if (reservedMeal.getDate().equals(date) && reservedMeal.getMealName() == mealName)
                return reservedMeal;
        }
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Fill all weekly items with either available or reserved meals
        fillItems();

    }


    private void fillItems() {
        Date date = Date.getToday().increaseDayBy(1);
        for (int i = 0; i < 7; i++) {
            Date day = date.increaseDayBy(i);
            MealInfo breakfast = isExistInReserved(day, MealName.BREAKFAST, reservedMeals);
            MealInfo lunch = isExistInReserved(day, MealName.LUNCH, reservedMeals);
            MealInfo dinner = isExistInReserved(day, MealName.DINNER, reservedMeals);
            if (breakfast == null) {
                breakfast = isExistInReserved(day, MealName.DINNER, availableMeals);
            }
            if (lunch == null) {
                lunch = isExistInReserved(day, MealName.DINNER, availableMeals);
            }
            if (dinner == null) {
                dinner = isExistInReserved(day, MealName.DINNER, availableMeals);
            }
            items.add(new WeeklyItem(breakfast, lunch, dinner));
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootViwe = inflater
                .inflate(R.layout.fragment_weekly, container, false);

        previousWeekBtn = rootViwe.findViewById(R.id.previous_week_btn);
        nextWeekBtn = rootViwe.findViewById(R.id.next_week_btn);
        previousWeekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Previous week", Toast.LENGTH_SHORT).show();
            }
        });
        nextWeekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Next week", Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView mRecyclerView = rootViwe.findViewById(R.id.weekly_recycler_view);

        WeeklyAdapter adapter = new WeeklyAdapter(items, getContext(), getFragmentManager());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return rootViwe;

    }

}
