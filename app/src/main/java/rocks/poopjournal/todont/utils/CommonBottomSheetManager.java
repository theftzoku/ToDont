package rocks.poopjournal.todont.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import rocks.poopjournal.todont.Helper;
import rocks.poopjournal.todont.R;
import rocks.poopjournal.todont.databinding.UpdateLayoutBottomSheetTestBinding;

/*
    refactor: Extract class
    Created CommonBottomSheetManager class for the bottom sheet which is used in multiple classes named as
    AvoidedLogAdapter, DoneLogAdapter, HabitsAdapter, HabitsLogAdapter to increase code readability and modularization

 */
public class CommonBottomSheetManager {

    private Context context;
    private String taskDetails;
    private String taskCategory;
    private int position;
    private BottomSheetDialog bottomSheetDialog;
    private UpdateLayoutBottomSheetTestBinding bottomSheetView;

    // Constructor to initialize the BottomSheet dialog with required data
    public CommonBottomSheetManager(Context context, String taskDetails, String taskCategory, int position) {
        this.context = context;
        this.taskDetails = taskDetails;
        this.taskCategory = taskCategory;
        this.position = position;

        // Initialize the BottomSheetDialog and view binding
        bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
        bottomSheetView = UpdateLayoutBottomSheetTestBinding.inflate(LayoutInflater.from(context));

        // Set up the views inside the BottomSheet
        setupBottomSheet();
    }

    // Method to set up the BottomSheet content
    private void setupBottomSheet() {
        bottomSheetView.titleText.setText(taskDetails);
        bottomSheetView.details.setText(Helper.habitsdata.get(position)[3]);
        bottomSheetView.tvTag.setText(Helper.habitsdata.get(position)[5]);

        // Set the avoided and done counts
        if (Helper.avoidedData.size() == 0) {
            bottomSheetView.avoidedCount.setText("0");
        } else {
            bottomSheetView.avoidedCount.setText(getAvoidedCount(Helper.habitsdata.get(position)[2]));
        }

        if (Helper.donedata.size() == 0) {
            bottomSheetView.doneCount.setText("0");
        } else {
            bottomSheetView.doneCount.setText(getDoneCount(Helper.habitsdata.get(position)[2]));
        }

        bottomSheetDialog.setContentView(bottomSheetView.getRoot());
        bottomSheetDialog.show();
    }

    // Method to get the avoided count for a specific habit
    private String getAvoidedCount(String habit) {
        int count = 0;
        for (String[] avoided : Helper.avoidedData) {
            if (avoided[2].equals(habit)) {
                count++;
            }
        }
        return String.valueOf(count);
    }

    // Method to get the done count for a specific habit
    private String getDoneCount(String habit) {
        int count = 0;
        for (String[] done : Helper.donedata) {
            if (done[2].equals(habit)) {
                count++;
            }
        }
        return String.valueOf(count);
    }

    // Expose bottomSheetView for external access
    public UpdateLayoutBottomSheetTestBinding getBottomSheetView() {
        return bottomSheetView;
    }

    // Public setter methods to set the button click listeners
    public void setAvoidedPlusButtonClickListener(View.OnClickListener listener) {
        bottomSheetView.avoidedPlusButton.setOnClickListener(listener);
    }

    public void setAvoidedMinusButtonClickListener(View.OnClickListener listener) {
        bottomSheetView.avoidedMinusButton.setOnClickListener(listener);
    }

    public void setDonePlusButtonClickListener(View.OnClickListener listener) {
        bottomSheetView.donePlusButton.setOnClickListener(listener);
    }

    public void setDoneMinusButtonClickListener(View.OnClickListener listener) {
        bottomSheetView.doneMinusButton.setOnClickListener(listener);
    }

    public void setNotificationButtonClickListener(View.OnClickListener listener) {
        bottomSheetView.tvNotification.setOnClickListener(listener);
    }

    // Method to dismiss the bottom sheet if needed
    public void dismissBottomSheet() {
        if (bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }
}
