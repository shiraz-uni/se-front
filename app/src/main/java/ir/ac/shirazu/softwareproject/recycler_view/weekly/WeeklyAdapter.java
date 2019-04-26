package ir.ac.shirazu.softwareproject.recycler_view.weekly;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.ac.shirazu.softwareproject.MeaInformation;
import ir.ac.shirazu.softwareproject.R;
import ir.ac.shirazu.softwareproject.activity.MainActivity;

public class WeeklyAdapter extends RecyclerView.Adapter<WeeklyViewHolder> {
    private final Context mContext;
    private ArrayList<WeeklyItem> itemsInfo;

    public WeeklyAdapter(List<WeeklyItem> weeklyItems, Context context) {
        itemsInfo = (ArrayList<WeeklyItem>) weeklyItems;
        this.mContext = context;
    }

    @NonNull
    @Override
    public WeeklyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.item_weekly, viewGroup, false);
        return new WeeklyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyViewHolder weeklyViewHolder, int i) {
        weeklyViewHolder.dayTV.setText(itemsInfo.get(i).getDay());
        weeklyViewHolder.dateTV.setText(itemsInfo.get(i).getDate());
        setEachMeal(weeklyViewHolder, itemsInfo.get(i).getBrekfastInformation().getReservedFood(),
                itemsInfo.get(i).getLunchInformation().getReservedFood(),
                itemsInfo.get(i).getDinnerInformation().getReservedFood(),
                i);
        weeklyViewHolder.onBind(itemsInfo, i);
    }

    private void setEachMeal(WeeklyViewHolder weeklyViewHolder, String breakfast, String lunch, String dinner, int index) {
        String[] temp = breakfast.split(" ");
        breakfast = temp[0].concat(" ").concat(temp[1]);
        temp = dinner.split(" ");
        dinner = temp[0].concat(" ").concat(temp[1]);
        temp = lunch.split(" ");
        lunch = temp[0].concat(" ").concat(temp[1]);

        if (MainActivity.isInDormitory()) {
            MeaInformation meaInformation = itemsInfo.get(index).getBrekfastInformation();
            setTextAndBackgroundColor(weeklyViewHolder.breakfastTV, weeklyViewHolder.breakfatLayout, meaInformation, breakfast);
            meaInformation = itemsInfo.get(index).getLunchInformation();
            setTextAndBackgroundColor(weeklyViewHolder.lunchTV, weeklyViewHolder.lunchLayout, meaInformation, lunch);
            meaInformation = itemsInfo.get(index).getDinnerInformation();
            setTextAndBackgroundColor(weeklyViewHolder.dinnerTV, weeklyViewHolder.dinnerLayout, meaInformation, dinner);
        } else {
            weeklyViewHolder.breakfatLayout.setVisibility(View.GONE);
            weeklyViewHolder.dinnerLayout.setVisibility(View.GONE);
            MeaInformation lunchInformation = itemsInfo.get(index).getLunchInformation();
            setTextAndBackgroundColor(weeklyViewHolder.lunchTV, weeklyViewHolder.lunchLayout, lunchInformation, lunch);

        }


    }

    private void setTextAndBackgroundColor(TextView textView, View layout, MeaInformation meaInformation, String text) {
        Drawable background = layout.getBackground();
        GradientDrawable drawable = (GradientDrawable) background;
        switch (meaInformation.getReserveState()) {
            case UNPLANNED:
                textView.setText(mContext.getString(R.string.unplanned));
                setBackgroundColor(background, R.color.colorSecondaryDarker);
                break;
            case NOT_RESERVED:
                textView.setText(mContext.getString(R.string.card_buying));
                setBackgroundColor(background, R.color.colorAccentLight);
                break;
            default:
                // case EDITABLE_RESERVED and NON_EDITABLE_RESERVED
                textView.setText(text);
                setBackgroundColor(background, R.color.colorAccent);
                break;
        }
    }

    private void setBackgroundColor(Drawable background, int color) {
        GradientDrawable gradientDrawable = (GradientDrawable) background;
        gradientDrawable.setColor(ContextCompat.getColor(mContext, color));
    }


    @Override
    public int getItemCount() {
        return 7;
    }


}

class WeeklyViewHolder extends RecyclerView.ViewHolder {
    TextView dayTV, dateTV, breakfastTV, lunchTV, dinnerTV;
    View breakfatLayout, lunchLayout, dinnerLayout;

    WeeklyViewHolder(@NonNull View itemView) {
        super(itemView);
        dayTV = itemView.findViewById(R.id.day_name);
        dateTV = itemView.findViewById(R.id.date);
        breakfastTV = itemView.findViewById(R.id.breakfast_text);
        lunchTV = itemView.findViewById(R.id.lunch_text);
        dinnerTV = itemView.findViewById(R.id.dinner_text);
        breakfatLayout = itemView.findViewById(R.id.breakfast_layout);
        lunchLayout = itemView.findViewById(R.id.lunch_layout);
        dinnerLayout = itemView.findViewById(R.id.dinner_layout);
    }

    void onBind(ArrayList<WeeklyItem> itemsInfo, int i) {
        WeeklyItem item = itemsInfo.get(i);
        setClickListener(item.getBrekfastInformation(), breakfatLayout);
        setClickListener(item.getLunchInformation(), lunchLayout);
        setClickListener(item.getDinnerInformation(), dinnerLayout);
    }

    private void setClickListener(MeaInformation meaInformation, View layoutToClick) {
        switch (meaInformation.getReserveState()) {
            case EDITABLE_RESERVED:
                layoutToClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("MyLogTest", "Show the food edit dialog");
                    }
                });
                break;
            case NON_EDITABLE_RESERVED:
                layoutToClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("MyLogTest", "Show the food information");
                    }
                });
                break;
            case UNPLANNED:
//                layoutToClick.setClickable(false);
                layoutToClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("MyLogTest", "setClickable(false)");
                    }
                });
                break;
            case NOT_RESERVED:
                layoutToClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("MyLogTest", "Show the food edit dialog");
                    }
                });
                break;
        }
    }
}
