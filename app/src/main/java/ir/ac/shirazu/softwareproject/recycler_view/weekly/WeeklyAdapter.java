package ir.ac.shirazu.softwareproject.recycler_view.weekly;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.ac.shirazu.softwareproject.MealInfo;
import ir.ac.shirazu.softwareproject.R;
import ir.ac.shirazu.softwareproject.ReserveState;
import ir.ac.shirazu.softwareproject.activity.MainActivity;
import ir.ac.shirazu.softwareproject.fragment.EditDialogFragment;

public class WeeklyAdapter extends RecyclerView.Adapter<WeeklyViewHolder> {
    private final Context mContext;
    private ArrayList<WeeklyItem> itemsInfo;
    private FragmentManager mFragmentManager;

    public WeeklyAdapter(List<WeeklyItem> weeklyItems, Context context, FragmentManager fragmentManager) {
        itemsInfo = (ArrayList<WeeklyItem>) weeklyItems;
        this.mContext = context;
        mFragmentManager = fragmentManager;
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
        weeklyViewHolder.dayTV.setText(itemsInfo.get(i).getBreakfastInfo().getDayOfWeek());
        weeklyViewHolder.dateTV.setText(itemsInfo.get(i).getBreakfastInfo().getDate());


        // ToDo: pass the reserved food information here.
        setEachMeal(weeklyViewHolder, i);
        WeeklyItem item = itemsInfo.get(i);
        setClickListener(item.getBreakfastInfo(), weeklyViewHolder.breakfastLayout);
        setClickListener(item.getLunchInfo(), weeklyViewHolder.lunchLayout);
        setClickListener(item.getDinnerInfo(), weeklyViewHolder.dinnerLayout);


    }

    private void setClickListener(final MealInfo mealInfo, View layoutToClick) {
        if (mealInfo.getReserveState() == ReserveState.UNPLANNED) {
            layoutToClick.setClickable(false);
        } else {
            layoutToClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditDialogFragment dialogFragment =
                            EditDialogFragment.newInstance(mealInfo);
                    dialogFragment.show(mFragmentManager, null);
                }
            });
        }

    }

    private void setEachMeal(WeeklyViewHolder weeklyViewHolder, int index) {
//        String[] temp = breakfast.split(" ");
//        breakfast = temp[0].concat(" ").concat(temp[1]);
//        temp = dinner.split(" ");
//        dinner = temp[0].concat(" ").concat(temp[1]);
//        temp = lunch.split(" ");
//        lunch = temp[0].concat(" ").concat(temp[1]);

        if (MainActivity.isInDormitory()) {
            MealInfo mealInfo = itemsInfo.get(index).getBreakfastInfo();
            setTextAndBackgroundColor(weeklyViewHolder.breakfastTV, weeklyViewHolder.breakfastLayout, mealInfo);
            mealInfo = itemsInfo.get(index).getLunchInfo();
            setTextAndBackgroundColor(weeklyViewHolder.lunchTV, weeklyViewHolder.lunchLayout, mealInfo);
            mealInfo = itemsInfo.get(index).getDinnerInfo();
            setTextAndBackgroundColor(weeklyViewHolder.dinnerTV, weeklyViewHolder.dinnerLayout, mealInfo);
        } else {
            weeklyViewHolder.breakfastLayout.setVisibility(View.GONE);
            weeklyViewHolder.dinnerLayout.setVisibility(View.GONE);
            MealInfo lunchInformation = itemsInfo.get(index).getLunchInfo();
            setTextAndBackgroundColor(weeklyViewHolder.lunchTV, weeklyViewHolder.lunchLayout, lunchInformation);

        }


    }

    private void setTextAndBackgroundColor(TextView textView, View layout, MealInfo
            mealInfo) {
        Drawable background = layout.getBackground();
        GradientDrawable drawable = (GradientDrawable) background;
        switch (mealInfo.getReserveState()) {
            case UNPLANNED:
                textView.setText(mContext.getString(R.string.unplanned));
                setBackgroundColor(background, R.color.colorSecondaryDarker);
                break;
            case NON_EDITABLE_RESERVED:
            case EDITABLE_RESERVED:
                String foodName = mealInfo.getReservedFoodInfo().getFoodName();
                textView.setText(foodName);
                setBackgroundColor(background, R.color.colorAccent);
                break;
            case NOT_RESERVED:
                textView.setText(mContext.getString(R.string.card_buying));
                setBackgroundColor(background, R.color.colorAccentLight);
                break;
            default:
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
    View breakfastLayout, lunchLayout, dinnerLayout;

    WeeklyViewHolder(@NonNull View itemView) {
        super(itemView);
        dayTV = itemView.findViewById(R.id.day_name);
        dateTV = itemView.findViewById(R.id.date);
        breakfastTV = itemView.findViewById(R.id.breakfast_text);
        lunchTV = itemView.findViewById(R.id.lunch_text);
        dinnerTV = itemView.findViewById(R.id.dinner_text);
        breakfastLayout = itemView.findViewById(R.id.breakfast_layout);
        lunchLayout = itemView.findViewById(R.id.lunch_layout);
        dinnerLayout = itemView.findViewById(R.id.dinner_layout);
    }
}
