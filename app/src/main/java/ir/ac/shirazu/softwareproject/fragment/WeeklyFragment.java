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
import ir.ac.shirazu.softwareproject.server_api.Meal.MyKit;


public class WeeklyFragment extends Fragment {
    private static final String ITMES_KEY = "WEEKLY ITEMS";
    private Button nextWeekBtn, previousWeekBtn;
    private List<WeeklyItem> items;

    public WeeklyFragment() {
        List<MealInfo> availableMeals = MealInfo.allAvailableMealInfo;
        List<MealInfo> reservedMeals = MyKit.student.allStudentFoodInfo;

        Date today = Date.getToday();
        Date firstDayOfWeek = Date.getFirstDayOfThisWeek();

        List<WeeklyItem> items = new ArrayList<>();

        for (int i = 0; i < 7; i++)
            items.add(null);

        // Fill all weekly items with either available or reserved meals
        fillItems(availableMeals, items, today);
        fillItems(reservedMeals, items, firstDayOfWeek);

        this.items = items;
    }

    private void fillItems(List<MealInfo> meals, List<WeeklyItem> itemsToFill, Date date) {
        for (int i = 0; i < 7; i++) {
            if (i != 0)
                date.setToNextDay();

            WeeklyItem thisDayWeeklyItem = new WeeklyItem(null, null, null);
            for (MealInfo meal : meals) {
                Date thisMealDate = meal.getDate();

                if (thisMealDate.getDay() == date.getDay()
                        && thisMealDate.getMonth() == date.getMonth()) {
                    switch (meal.getMealName()) {
                        case BREAKFAST:
                            thisDayWeeklyItem.setBreakfastInfo(meal);
                            break;
                        case LUNCH:
                            thisDayWeeklyItem.setLunchInfo(meal);
                            break;
                        case DINNER:
                            thisDayWeeklyItem.setDinnerInfo(meal);
                            break;
                    }
                }
            }
            itemsToFill.set(i,thisDayWeeklyItem);
        }
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
