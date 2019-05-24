package ir.ac.shirazu.softwareproject.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ir.ac.shirazu.softwareproject.FoodInfo;
import ir.ac.shirazu.softwareproject.MealInfo;
import ir.ac.shirazu.softwareproject.R;
import ir.ac.shirazu.softwareproject.ReserveState;
import ir.ac.shirazu.softwareproject.Utility;
import ir.ac.shirazu.softwareproject.recycler_view.weekly.WeeklyAdapter;

public class EditDialogFragment extends DialogFragment {
    private static final String ARG_FOOD_INFORMATION = "day_info";
    private static final String ARG_CLICKED_ITEM_ROW = "clicked_item_row";
    private static final String ARG_STATE_CHANGED_LISTENER = "state_changed_listener";
    private Utility mUtility;

    private MealInfo mMealInfo;
    private int clickedItemRow;
    private WeeklyAdapter.DialogFragmentCallBack mDialogFragmentCallBack;

    private TextView mealName, day, date;
    private RadioButton firstFoodRadioButton, secondFoodRadioButton;
    private RadioGroup foodRadioGroup;
    private Spinner restaurantSpinner;
    private Button okButton, cancelButton, seenButton;
    private ImageView deleteButton;
    private View submitButtonsContainer;


    public EditDialogFragment() {
        // Required empty public constructor
    }


    public static EditDialogFragment newInstance(MealInfo mealInfo, int clickedItemRow, WeeklyAdapter.DialogFragmentCallBack dialogFragmentCallBack) {
        EditDialogFragment fragment = new EditDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FOOD_INFORMATION, mealInfo);
        args.putSerializable(ARG_STATE_CHANGED_LISTENER, dialogFragmentCallBack);
        args.putInt(ARG_CLICKED_ITEM_ROW, clickedItemRow);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mMealInfo = (MealInfo) args.getSerializable(ARG_FOOD_INFORMATION);
            mDialogFragmentCallBack = (WeeklyAdapter.DialogFragmentCallBack) args.getSerializable(ARG_STATE_CHANGED_LISTENER);
            clickedItemRow = args.getInt(ARG_CLICKED_ITEM_ROW);
        }

        mUtility = Utility.getInstance(getContext().getApplicationContext());
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
        restaurantSpinner = rootView.findViewById(R.id.restaurant);
        okButton = rootView.findViewById(R.id.ok);
        cancelButton = rootView.findViewById(R.id.cancel);
        seenButton = rootView.findViewById(R.id.seen_button);
        deleteButton = rootView.findViewById(R.id.delete_button);
        submitButtonsContainer = rootView.findViewById(R.id.submit_buttons_container);

        mealName.setText(mMealInfo.getMealName());
        day.setText(mMealInfo.getDate().getDayOfWeek());
        date.setText(mMealInfo.getDate().getDateInString());
        setRestaurantAdapter();
        if (mMealInfo.getReserveState() != ReserveState.NOT_RESERVED)
            restaurantSpinner.setSelection(mMealInfo.getReservedSelf().getSelfId());

        if (mMealInfo.getReserveState() == ReserveState.EDITABLE_RESERVED)
            okButton.setText("تغییر ژتون");

        FoodInfo food = mMealInfo.getFirstFood();
        firstFoodRadioButton.setText(food.getFoodName() + "  " + food.getFoodPrice());
        food = mMealInfo.getSecondFood();
        secondFoodRadioButton.setText(food.getFoodName() + "  " + food.getFoodPrice());
        ReserveState state = mMealInfo.getReserveState();

        switch (state) {
            case NOT_RESERVED:
                logicOfNotReservedState();
                break;

            case EDITABLE_RESERVED:
                logicOfEditableReservedState();

                break;
            case NON_EDITABLE_RESERVED:
                logicOfNonEditableReservedState();


                break;
        }
        setLogic(state);

        return rootView;
    }

    private void logicOfNotReservedState() {

    }

    private void logicOfEditableReservedState() {

    }

    private void logicOfNonEditableReservedState() {
    }


    private void setLogic(ReserveState state) {
        // Warning: Don't change the order of two following lines!
        // It changes the logic of the system.
        setOnClickListeners();
        updateUI(state);
    }

    private void setOnClickListeners() {

        if (mMealInfo.getReserveState() != ReserveState.NON_EDITABLE_RESERVED) {

            restaurantSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int nowReservedFoodId = foodRadioGroup.getCheckedRadioButtonId()
                            == firstFoodRadioButton.getId() ? mMealInfo.getReservedFoodId()
                            : mMealInfo.getSecondFood().getFoodId();

                    boolean isReservedFoodChanged = false;
                    if (mMealInfo.getReserveState() == ReserveState.NOT_RESERVED
                            && restaurantSpinner.getSelectedItemPosition() != 0) {
                        return;
                    } else {
                        isReservedFoodChanged = nowReservedFoodId != mMealInfo.getReservedFoodId();
                    }

                    if (position != 0) {
                        if (mUtility.getSelf(position).getSelfId() != mMealInfo.getReservedSelf().getSelfId()
                                || isReservedFoodChanged) {


                            deleteButton.setVisibility(View.INVISIBLE);
                            submitButtonsContainer.setVisibility(View.VISIBLE);
                        } else {
                            submitButtonsContainer.setVisibility(View.INVISIBLE);
                            deleteButton.setVisibility(View.VISIBLE);

                        }
                    } else {
                        if (mMealInfo.getReservedSelf() != null) {
                            restaurantSpinner.setSelection(mMealInfo.getReservedSelf().getSelfId());
                        }
                    }
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

        firstFoodRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMealInfo.getReserveState() == ReserveState.NOT_RESERVED) {
                    return;
                }
                int position = restaurantSpinner.getSelectedItemPosition();
                boolean isReservedSelfChanged = mUtility.getSelf(position).getSelfId()
                        != mMealInfo.getReservedSelf().getSelfId();
                if (mMealInfo.getReserveState() != ReserveState.NOT_RESERVED)

                    if (mMealInfo.getReservedFoodId() == mMealInfo.getSecondFood().getFoodId()
                            || isReservedSelfChanged) {
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
                if (mMealInfo.getReserveState() == ReserveState.NOT_RESERVED) {
                    return;
                }
                int position = restaurantSpinner.getSelectedItemPosition();
                boolean isReservedSelfChanged = mUtility.getSelf(position).getSelfId()
                        != mMealInfo.getReservedSelf().getSelfId();

                if (mMealInfo.getReserveState() != ReserveState.NOT_RESERVED)
                    if (mMealInfo.getReservedFoodId() == mMealInfo.getFirstFood().getFoodId()
                            || isReservedSelfChanged) {
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
                // Delete operation
                mMealInfo.setReservedFoodId(-1);
                mMealInfo.setReservedSelf(null);
                mMealInfo.setReserveState(ReserveState.NOT_RESERVED);
                deleteQuery();
                mDialogFragmentCallBack.changeUi(clickedItemRow, mMealInfo.getMealType());
                dismiss();
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
                if (restaurantSpinner.getSelectedItemPosition() == 0) {
                    restaurantSpinner.performClick();
                    return;
                } else if (foodRadioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getContext().getApplicationContext(), "ابتدا یکی از خوراکی ها را انتخاب کنید"
                            , Toast.LENGTH_SHORT).show();
                    return;
                }

                switch (mMealInfo.getReserveState()) {
                    case EDITABLE_RESERVED:
                        // Modify operation
                        int newReservedFoodId = mMealInfo.getReservedFoodId()
                                == mMealInfo.getFirstFood().getFoodId() ? mMealInfo.getFirstFood().getFoodId()
                                : mMealInfo.getSecondFood().getFoodId();
                        mMealInfo.setReservedFoodId(newReservedFoodId);
                        // ToDo: implement a method to get self name from a self id
                        mMealInfo.setReservedSelf(mUtility.getSelf(restaurantSpinner.getSelectedItemPosition()));
                        modifyQuery();
                        break;
                    case NOT_RESERVED:
                        // Buy operation
                        int reservedFoodId;
                        if (foodRadioGroup.getCheckedRadioButtonId() == firstFoodRadioButton.getId())
                            reservedFoodId = mMealInfo.getFirstFood().getFoodId();
                        else
                            reservedFoodId = mMealInfo.getSecondFood().getFoodId();
                        mMealInfo.setReserveState(ReserveState.EDITABLE_RESERVED);
                        mMealInfo.setReservedSelf(mUtility.getSelf(restaurantSpinner.getSelectedItemPosition()));
                        mMealInfo.setReservedFoodId(reservedFoodId);
                        buyQuery();
                        break;
                }
                mDialogFragmentCallBack.changeUi(clickedItemRow, mMealInfo.getMealType());
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void modifyQuery() {
        // ToDo: Perform modifying or buy operation here
    }

    private void buyQuery() {
        // ToDo: Perform modifying or buy operation here
    }

    private void deleteQuery() {
        // ToDo: Perform delete operation here

    }

    private void setRestaurantAdapter() {
        Utility utility = Utility.getInstance(getContext().getApplicationContext());
        List<String> list = utility.getSelfsName();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext().getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        restaurantSpinner.setAdapter(spinnerAdapter);
    }

    private void updateUI(ReserveState state) {
        switch (state) {
            case NON_EDITABLE_RESERVED:
                setReservedData();
                restaurantSpinner.setEnabled(false);
                if (mMealInfo.getReservedFoodId() == mMealInfo.getFirstFood().getFoodId()) {
                    firstFoodRadioButton.setClickable(false);
                    secondFoodRadioButton.setEnabled(false);

                } else {
                    secondFoodRadioButton.setClickable(false);
                    firstFoodRadioButton.setEnabled(false);
                }
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
