package ir.ac.shirazu.softwareproject.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import ir.ac.shirazu.softwareproject.FoodInfo;
import ir.ac.shirazu.softwareproject.MealInfo;
import ir.ac.shirazu.softwareproject.R;
import ir.ac.shirazu.softwareproject.ReserveState;

public class EditDialogFragment extends DialogFragment {
    private static final String ARG_FOOD_INFORMATION = "DayInfo";
    private MealInfo mMealInfo;

    private TextView mealName, day, date;
    private RadioButton firstFoodRadioButton, secondFoodRadioButton;
    private RadioGroup foodRadioGroup;
    private Spinner resturant;
    private Button okButton, cancelButton, seenButton;
    private ImageView deleteButton;
    private View submitButtonsContainer;


    public EditDialogFragment() {
        // Required empty public constructor
    }


    public static EditDialogFragment newInstance(MealInfo mealInfo) {
        EditDialogFragment fragment = new EditDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FOOD_INFORMATION, mealInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mMealInfo = (MealInfo) args.getSerializable(ARG_FOOD_INFORMATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_dialog, container, false);
        mealName = rootView.findViewById(R.id.meal_name);
        day = rootView.findViewById(R.id.dayOfWeek);
        date = rootView.findViewById(R.id.date);
        firstFoodRadioButton = rootView.findViewById(R.id.first_food);
        secondFoodRadioButton = rootView.findViewById(R.id.second_food);
        foodRadioGroup = rootView.findViewById(R.id.food_radio_group);
        resturant = rootView.findViewById(R.id.resturant);
        okButton = rootView.findViewById(R.id.ok);
        cancelButton = rootView.findViewById(R.id.cancel);
        seenButton = rootView.findViewById(R.id.seen_button);
        deleteButton = rootView.findViewById(R.id.delete_button);
        submitButtonsContainer = rootView.findViewById(R.id.submit_buttons_container);

        mealName.setText(mMealInfo.getMealName());
        day.setText(mMealInfo.getDayOfWeek());
        date.setText(mMealInfo.getDate());

        FoodInfo food = mMealInfo.getFirstFood();
        firstFoodRadioButton.setText(food.getFoodName() + "  " + food.getFoodPrice());
        food = mMealInfo.getSecondFood();
        secondFoodRadioButton.setText(food.getFoodName() + "  " + food.getFoodPrice());

        ReserveState state = mMealInfo.getReserveState();

        setLogic(state);



        return rootView;
    }

    private void setLogic(ReserveState state) {
        // Warning: Don't change the order of two following lines!
        // It changes the logic of the system.
        setOnClickListeners();
        updateUI(state);
    }

    private void setOnClickListeners() {
        firstFoodRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMealInfo.getReservedFoodId() == mMealInfo.getSecondFood().getFoodId()) {
                    deleteButton.setVisibility(View.INVISIBLE);
                    submitButtonsContainer.setVisibility(View.VISIBLE);
                } else {
                    submitButtonsContainer.setVisibility(View.INVISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);
                }


            }
        });
        secondFoodRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMealInfo.getReservedFoodId() == mMealInfo.getFirstFood().getFoodId()) {
                    deleteButton.setVisibility(View.INVISIBLE);
                    submitButtonsContainer.setVisibility(View.VISIBLE);
                } else {
                    submitButtonsContainer.setVisibility(View.INVISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ToDo: Perform delete operation here
            }
        });

        seenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void updateUI(ReserveState state) {
        switch (state) {
            case NON_EDITABLE_RESERVED:
                setReservedData();
                foodRadioGroup.setClickable(false);
                seenButton.setVisibility(View.VISIBLE);
                break;

            case EDITABLE_RESERVED:
                setReservedData();
                deleteButton.setVisibility(View.VISIBLE);
                break;
            case NOT_RESERVED:
                submitButtonsContainer.setVisibility(View.VISIBLE);
            default:
                break;

        }
    }

    private void setReservedData() {
        if (mMealInfo.getReservedFoodId() == mMealInfo.getFirstFood().getFoodId())
            firstFoodRadioButton.setChecked(true);
        else
            secondFoodRadioButton.setChecked(true);
    }

}
