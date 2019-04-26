package ir.ac.shirazu.softwareproject.recycler_view.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import ir.ac.shirazu.softwareproject.R;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {



    private List<ListItem> mydata;

    public ListAdapter(List<ListItem> mydata) {
        this.mydata = mydata;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView dayBought;
        public TextView dateBought;
        public Button delete;
        public TextView foodName;
        public TextView mealType;
        public TextView meal;
        public TextView price;




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dateBought = (TextView) itemView.findViewById(R.id.date_bought);
            dayBought = (TextView) itemView.findViewById(R.id.day_bought);
            delete =  (Button) itemView.findViewById(R.id.delete);
            foodName = (TextView) itemView.findViewById(R.id.food_name);
            mealType = (TextView) itemView.findViewById(R.id.meal_type);
            meal = (TextView) itemView.findViewById(R.id.meal_name);
            price=(TextView) itemView.findViewById(R.id.meal_price);
            delete = (Button) itemView.findViewById(R.id.delete);


        }
    }


    @NonNull
    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

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



    }



    @Override
    public int getItemCount() {
        return mydata.size();
    }

}
