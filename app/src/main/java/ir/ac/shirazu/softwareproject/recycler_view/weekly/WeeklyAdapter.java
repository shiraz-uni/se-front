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
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ir.ac.shirazu.softwareproject.R;
import ir.ac.shirazu.softwareproject.activity.MainActivity;
import ir.ac.shirazu.softwareproject.fragment.EditDialogFragment;
import ir.ac.shirazu.softwareproject.server_api.Meal.Date;
import ir.ac.shirazu.softwareproject.server_api.Meal.MealInfo;
import ir.ac.shirazu.softwareproject.server_api.Meal.MealName;
import ir.ac.shirazu.softwareproject.server_api.Meal.ReserveState;

public class WeeklyAdapter extends RecyclerView.Adapter<WeeklyViewHolder> {
    private final Context mContext;
    private ArrayList<WeeklyItem> itemsInfo;
    private FragmentManager mFragmentManager;
    private Date date;

    public interface DialogFragmentCallBack extends Serializable {
        public void changeUi(int clickedItemRow, MealName mealName);
    }

    public WeeklyAdapter(List<WeeklyItem> weeklyItems, Context context, FragmentManager fragmentManager) {
        itemsInfo = (ArrayList<WeeklyItem>) weeklyItems;
        this.mContext = context;
        this.date = Date.getToday().increaseDayBy(1);
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
        if (i != 0)
            date.setToNextDay();

        weeklyViewHolder.dayTV.setText(date.getDayOfWeek());
        weeklyViewHolder.dateTV.setText(date.getDateInString());

        // ToDo: pass the reserved food information here.
        setEachMeal(weeklyViewHolder, i);
        WeeklyItem item = itemsInfo.get(i);
        setClickListener(item.getBreakfastInfo(), weeklyViewHolder.breakfastLayout, i);
        setClickListener(item.getLunchInfo(), weeklyViewHolder.lunchLayout, i);
        setClickListener(item.getDinnerInfo(), weeklyViewHolder.dinnerLayout, i);


    }

    private void setClickListener(final MealInfo mealInfo, final View layoutToClick, final int index) {
        if (mealInfo == null) {
            layoutToClick.setClickable(false);
        } else if (mealInfo.getReserveState() == ReserveState.UNPLANNED) {
            layoutToClick.setClickable(false);
        } else {
            layoutToClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragmentCallBack s = new DialogFragmentCallBack() {
                        @Override
                        public void changeUi(int clickedItemRow, MealName mealType) {
                            WeeklyItem changedItem = itemsInfo.get(clickedItemRow);
                            TextView textView = null;
                            MealInfo changedMealInfo = null;
                            switch (mealType) {
                                case BREAKFAST:
                                    changedMealInfo = changedItem.getBreakfastInfo();
                                    textView = layoutToClick.findViewById(R.id.breakfast_text);
                                    break;
                                case LUNCH:
                                    changedMealInfo = changedItem.getLunchInfo();
                                    textView = layoutToClick.findViewById(R.id.lunch_text);
                                    break;
                                case DINNER:
                                    changedMealInfo = changedItem.getDinnerInfo();
                                    textView = layoutToClick.findViewById(R.id.dinner_text);
                                    break;
                            }
                            setTextAndBackgroundColor(textView, layoutToClick, changedMealInfo);
                        }
                    };
                    if (mealInfo.getReserveState() != ReserveState.NON_EDITABLE_RESERVED) {
                        validateTheStateOfMealInfo(mealInfo);
                    }
                    if (mealInfo.getReserveState() != ReserveState.UNPLANNED) {
                        EditDialogFragment dialogFragment =
                                EditDialogFragment.newInstance(mealInfo, index, s);
                        dialogFragment.show(mFragmentManager, null);
                    } else {
                        notifyDataSetChanged();
                        Toast.makeText(mContext.getApplicationContext(), "مهلت رزرو تمام شده", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
            });
        }

    }

    private void validateTheStateOfMealInfo(MealInfo mealInfo) {
        // ToDo: Check if it's necessary to change the reserve state of mealInfo
        ReserveState state = mealInfo.getReserveState();

//        if (state == ReserveState.EDITABLE_RESERVED) {
//            if (remainingTime(mealInfo.getDate()) > someThing){
//                state = ReserveState.NON_EDITABLE_RESERVED;
//            }
//        } else if (state == ReserveState.NOT_RESERVED) {
//            if (remainingTime(mealInfo.getDate()) > someThing)
//                state = ReserveState.UNPLANNED;
//        }
        mealInfo.setReserveState(state);


    }

    private void setEachMeal(WeeklyViewHolder weeklyViewHolder, int index) {

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
        if (mealInfo == null) {
            textView.setText(mContext.getString(R.string.unplanned));
            setBackgroundColor(drawable, R.color.colorSecondaryDarker);
        } else {
            switch (mealInfo.getReserveState()) {

                case UNPLANNED:
                    textView.setText(mContext.getString(R.string.unplanned));
                    setBackgroundColor(drawable, R.color.colorSecondaryDarker);
                    break;
                case NON_EDITABLE_RESERVED:
                case EDITABLE_RESERVED:
                    String foodName = mealInfo.getReservedFoodInfo().getFoodName();
                    textView.setText(foodName);
                    setBackgroundColor(drawable, R.color.colorAccent);
                    break;
                case NOT_RESERVED:
                    textView.setText(mContext.getString(R.string.card_buying));
                    setBackgroundColor(drawable, R.color.colorAccentLight);
                    break;
                default:
                    break;
            }
        }
    }

    private void setBackgroundColor(GradientDrawable drawable, int color) {
        drawable.setColor(ContextCompat.getColor(mContext, color));
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
