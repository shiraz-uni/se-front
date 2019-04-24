package ir.ac.shirazu.softwareproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ir.ac.shirazu.softwareproject.FoodInformation;
import ir.ac.shirazu.softwareproject.R;
import ir.ac.shirazu.softwareproject.ReserveState;
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
        WeeklyAdapter adapter = new WeeklyAdapter(items, getContext());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return rootViwe;

    }

    private ArrayList<WeeklyItem> generateRandomData() {
        ArrayList<WeeklyItem> items = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            items.add(new WeeklyItem("شنبه", "1398/02/02",
                    getFoodInfo(),
                    getFoodInfo(),
                    getFoodInfo()
            ));
        }
        return items;
    }

    private FoodInformation getFoodInfo() {
        Random random = new Random();
        int reservedStateRand = random.nextInt(4);
        ReserveState state = null;
        switch (reservedStateRand) {
            case 0:
                state = ReserveState.EDITABLE_RESERVED;
                Log.d("MYTESTING", "Random number:" + reservedStateRand);
                break;
            case 1:
                state = ReserveState.NON_EDITABLE_RESERVED;
                Log.d("MYTESTING", "Random number:" + reservedStateRand);
                break;
            case 2:
                state = ReserveState.UNPLANNED;
                Log.d("MYTESTING", "Random number:" + reservedStateRand);
                break;
            case 3:
                state = ReserveState.NOT_RESERVED;
                Log.d("MYTESTING", "Random number:" + reservedStateRand);

        }
        return new FoodInformation("چلو کباب", "سلف مهندسی نفت و گاز", state);
    }
}
