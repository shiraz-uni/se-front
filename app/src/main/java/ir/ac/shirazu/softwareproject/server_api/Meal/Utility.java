package ir.ac.shirazu.softwareproject.server_api.Meal;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ir.ac.shirazu.softwareproject.R;

public class Utility {

    private static Utility instance = null;

    private Context context;
    private List<Self> selfs;
    private List<String> selfsName;

    private Utility(Context context) {
        this.context = context;
        init();
    }

    public static Utility getInstance(Context context) {
        if (instance == null) {
            instance = new Utility(context);
        }
        return instance;

    }

    private void init() {
        selfs = getSelfsInfo();
    }

    public List<Self> getSelfsInfo() {
        // ToDo: implement the query to get Selfs info from database
        // For now we get the selfs info statically from the strings.xml file
        // We supposed that the id of each self is the index in for-loop
        if (this.selfs == null) {
            List<String> selfsName = getSelfsName();
            List<Self> gainedSelfs = new ArrayList<>();
            for (int i = 0; i < selfsName.size(); i++) {
                gainedSelfs.add(new Self(i, selfsName.get(i)));
                this.selfs = gainedSelfs;
            }
        }
        return this.selfs;
    }

    public Self getSelf(int index) {
        return selfs.get(index);
    }

    public List<String> getSelfsName() {
        if (this.selfsName == null) {
            String[] gainedSelfsName =
                    context.getApplicationContext().getResources().getStringArray(R.array.restaurants);
            this.selfsName = new ArrayList<>();
            for (int i = 0; i < gainedSelfsName.length; i++) {
                this.selfsName.add(gainedSelfsName[i]);
            }
        }
        return this.selfsName;
    }
}