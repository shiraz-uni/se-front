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
import java.util.Random;

import ir.ac.shirazu.softwareproject.server_api.Date;
import ir.ac.shirazu.softwareproject.server_api.FoodInfo;
import ir.ac.shirazu.softwareproject.server_api.MealInfo;
import ir.ac.shirazu.softwareproject.server_api.MealName;
import ir.ac.shirazu.softwareproject.R;
import ir.ac.shirazu.softwareproject.server_api.ReserveState;
import ir.ac.shirazu.softwareproject.server_api.Utility;
import ir.ac.shirazu.softwareproject.recycler_view.weekly.WeeklyAdapter;
import ir.ac.shirazu.softwareproject.recycler_view.weekly.WeeklyItem;


public class WeeklyFragment extends Fragment {
    private Button nextWeekBtn, previousWeekBtn;

    public static WeeklyFragment newInstance(String param1, String param2) {
        WeeklyFragment fragment = new WeeklyFragment();
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
        List<WeeklyItem> items = generateRandomData();
        WeeklyAdapter adapter = new WeeklyAdapter(items, getContext(), getFragmentManager());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return rootViwe;

    }

    private ArrayList<WeeklyItem> generateRandomData() {
        ArrayList<WeeklyItem> items = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            items.add(new WeeklyItem(getFoodInfo(MealName.BREAKFAST),
                    getFoodInfo(MealName.LUNCH),
                    getFoodInfo(MealName.DINNER)
            ));
        }
        return items;
    }

    private MealInfo getFoodInfo(MealName mealName) {

        Utility utility = Utility.getInstance(getContext().getApplicationContext());
        Random random = new Random();
        int reservedStateRand = random.nextInt(4);
        ReserveState state;
        Date date = new Date(25, 5, 1398);
        MealInfo mealInfo = null;
        FoodInfo firstFood = new FoodInfo("غذای اول، چلو کباب با دلستر و ژله برای تست", 3700, 5814),
                secondFood = new FoodInfo("غذای دوم، پلو مرغ با ماست برای تست", 4200, 4352);
        switch (reservedStateRand) {
            case 0:
                state = ReserveState.EDITABLE_RESERVED;
                mealInfo = new MealInfo(date, mealName, state, firstFood,
                        secondFood, firstFood.getFoodId(), utility.getSelfsInfo().get(3),null);
                break;
            case 1:
                state = ReserveState.NON_EDITABLE_RESERVED;
                mealInfo = new MealInfo(date, mealName, state, firstFood,
                        secondFood, firstFood.getFoodId(), utility.getSelfsInfo().get(7),null);
                break;
            case 2:
                state = ReserveState.UNPLANNED;
                mealInfo = new MealInfo(date, mealName, state);
                break;
            case 3:
                state = ReserveState.NOT_RESERVED;
                mealInfo = new MealInfo(date, mealName, state, firstFood,
                        secondFood);


        }

        return mealInfo;
    }
}
