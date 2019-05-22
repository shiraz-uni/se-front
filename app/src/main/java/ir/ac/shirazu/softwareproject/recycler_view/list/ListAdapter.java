package ir.ac.shirazu.softwareproject.recycler_view.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ir.ac.shirazu.softwareproject.R;

public class ListAdapter extends RecyclerView.Adapter<MyViewHolder> {


    private List<ListItem> mydata;

    public ListAdapter(List<ListItem> mydata) {
        this.mydata = mydata;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        ListItem data = mydata.get(i);

        myViewHolder.price.setText(data.getPrice());
        myViewHolder.meal.setText(data.getMeal());
        myViewHolder.mealType.setText(data.getType());
        myViewHolder.foodName.setText(data.getFood());
        myViewHolder.dateBought.setText(data.getDate());
        myViewHolder.dayBought.setText(data.getDay());
        myViewHolder.reservedPlace.setText(data.getReservedPlace());
    }


    @Override
    public int getItemCount() {
        return mydata.size();
    }


}

class MyViewHolder extends RecyclerView.ViewHolder {
    TextView dayBought;
    TextView dateBought;
    ImageView delete;
    TextView foodName;
    TextView mealType;
    TextView meal;
    TextView price;
    TextView reservedPlace;


    public MyViewHolder(@NonNull View itemView) {
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





