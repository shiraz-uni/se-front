package ir.ac.shirazu.softwareproject.recycler_view.list;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ir.ac.shirazu.softwareproject.activity.MainActivity;
import ir.ac.shirazu.softwareproject.fragment.ListFragment;
import ir.ac.shirazu.softwareproject.server_api.Meal.Date;
import ir.ac.shirazu.softwareproject.server_api.Meal.MealInfo;
import ir.ac.shirazu.softwareproject.R;
import ir.ac.shirazu.softwareproject.server_api.Meal.MyKit;
import ir.ac.shirazu.softwareproject.server_api.Meal.Utility;
import saman.zamani.persiandate.PersianDate;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {



    private List<MealInfo> mydata;
    PersianDate today = new PersianDate();
   // PersianDate coupondDate = new PersianDate();
    PersianDate tmp = new PersianDate();
    MainActivity activity;
    List<String> slfnames;

    public ListAdapter(List<MealInfo> mydata, Activity activity) {
        this.mydata = mydata;
        this.activity =(MainActivity) activity;

    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);

        Utility utility = Utility.getInstance(activity.getApplicationContext());
        slfnames  = utility.getSelfsName();

        return new ListViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder listViewHolder, final int i) {

        final MealInfo data = mydata.get(i);
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

        listViewHolder.reservedPlace.setText(slfnames.get(1+ data.getReservedSelf().getSelfId()));



       /// coupondDate.addDate(data.getDate().getYear(),data.getDate().getMonth(),data.getDate().getDay());





        listViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PersianDate a = new PersianDate();

                a.setShDay(data.getDate().getDay());
                a.setShMonth(data.getDate().getMonth());
                a.setShYear(data.getDate().getYear());


                if (a.after(today) || ( today.getShDay()==a.getShDay() && today.getShMonth()==a.getShMonth() && today.getShYear()== a.getShYear())) Toast.makeText(activity.getApplicationContext(),"زمان حذف این ژتون گذشته است!",Toast.LENGTH_LONG).show();

                else {
                    if (mydata.get(i).getReservedFoodId() == 1 ){
                       int  ab =  MyKit.student.getCredit() ;
                       ab += mydata.get(i).getFirstFood().getFoodPrice();
                        MyKit.student.setCredit(ab);
                    }
                    else{
                        int  ab =  MyKit.student.getCredit() ;
                        ab += mydata.get(i).getSecondFood().getFoodPrice();
                        MyKit.student.setCredit(ab);
                    }
                    activity.showCredit();

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                MyKit.deleteCoupon(MyKit.student,mydata.get(i).getCouponId(),MyKit.student.getUser_token());

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    thread.start();
                    ListFragment.deleteFromAllStudentInfo(mydata.get(i).getCouponId());
                    mydata.remove(i);
                    notifyDataSetChanged();
                    //Todo: request to server to  delete a meal
                }
            }
        });

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





