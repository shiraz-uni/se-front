package ir.ac.shirazu.softwareproject.recycler_view.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ir.ac.shirazu.softwareproject.MealInfo;
import ir.ac.shirazu.softwareproject.R;
import ir.ac.shirazu.softwareproject.recycler_view.weekly.WeeklyItem;
import saman.zamani.persiandate.PersianDate;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {


    //private List<ListItem> mydata;
    private List<MealInfo> mydata;
    PersianDate today = new PersianDate();
    PersianDate tmp = new PersianDate();

    public ListAdapter(List<MealInfo> mydata) {
        this.mydata = mydata;
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);

        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder listViewHolder, final int i) {

        MealInfo data = mydata.get(i);
        if(data.getReservedFoodId() == 1){
            listViewHolder.price.setText(String.valueOf(data.getFirstFood().getFoodPrice()));
            listViewHolder.foodName.setText(data.getFirstFood().getFoodName());
        }

        else if(data.getReservedFoodId() == 2){
            listViewHolder.price.setText(String.valueOf(data.getSecondFood().getFoodPrice()));
            listViewHolder.foodName.setText(data.getSecondFood().getFoodName());

        }

        tmp.setShDay(data.getDate().getDay());
        tmp.setShMonth(data.getDate().getMonth());
        tmp.setShYear(data.getDate().getYear());
        listViewHolder.meal.setText(data.getMealNamePersian());
        listViewHolder.mealType.setText(data.getMealType().toString());

        listViewHolder.dateBought.setText(data.getDate().getDateInString());

        listViewHolder.dayBought.setText(tmp.dayName());
        listViewHolder.reservedPlace.setText(data.getReservedSelf().getSelfName());

        PersianDate coupondDate = new PersianDate();

        coupondDate.addDate(data.getDate().getYear(),data.getDate().getMonth(),data.getDate().getDay());



      ///  Log.d("i :",i+"");

       // Log.d("today",today.toString());

        if (coupondDate.before(today)) listViewHolder.delete.setVisibility(View.GONE);


        listViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo: request to server to  delete a meal

                mydata.remove(i);
                notifyDataSetChanged();
                
            }
        });

        //PersianDate today = new PersianDate();



    }


    @Override
    public int getItemCount() {
        return mydata.size();
    }


}

class ListViewHolder extends RecyclerView.ViewHolder {
    TextView dayBought;
    TextView dateBought;
    ImageView delete;
    TextView foodName;
    TextView mealType;
    TextView meal;
    TextView price;
    TextView reservedPlace;


    public ListViewHolder(@NonNull View itemView) {
        super(itemView);
        dateBought = itemView.findViewById(R.id.date_bought);
        dayBought = itemView.findViewById(R.id.day_bought);
        delete = itemView.findViewById(R.id.delete);
        foodName = itemView.findViewById(R.id.food_name);
        mealType = itemView.findViewById(R.id.meal_type);
        meal = itemView.findViewById(R.id.meal_name);
        price = itemView.findViewById(R.id.meal_price);
        reservedPlace = itemView.findViewById(R.id.reserved_place);
    }
}





