package nl.uva.student.davidknigge.mrpotatohead;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /*
     * Is called when this activity is loaded. Restores previously saved instance state by checking
     * if Bundle is set and contains ImageView visibility values. Sets ImageView visibility values
     * for all images in imageContainer.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            short[] visibilityArray = savedInstanceState.getShortArray("visibilityArray");

            if (visibilityArray != null) {
                FrameLayout container = findViewById(R.id.imageContainer);

                for (int i = 0; i < container.getChildCount(); i++) {
                    View child = container.getChildAt(i);
                    if (child instanceof ImageView) {
                        ImageView image = (ImageView) child;
                        image.setVisibility((int) visibilityArray[i]);
                    }
                }
            }
        }
    }

    /*
     * Is called when activity instance is destroyed. Creates array containing visiblity values for
     * all ImageViews contained in the imageContainer layout.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        FrameLayout container = findViewById(R.id.imageContainer);

        short[] visibilityArray = new short[container.getChildCount()];

        for (int i = 0; i < container.getChildCount(); i++) {

            View child = container.getChildAt(i);
            if (child instanceof ImageView) {
                ImageView image = (ImageView) child;
                int visibility = image.getVisibility();
                visibilityArray[i] = (short) visibility;
            }
        }
        outState.putShortArray("visibilityArray", visibilityArray);
    }

    /*
     * Called when any checkbox is clicked. Finds ImageView corresponding to this checkbox and sets
     * visibility value based on whether or not the checkbox is checked.
     */
    public void checkClicked(View v) {
        CheckBox checkbox = (CheckBox) v;

        String item = checkbox.getTag().toString();

        int rID = getResources().getIdentifier(
                "imageView" + item,
                "id",
                getPackageName()
        );
        ImageView image = findViewById(rID);

        if (checkbox.isChecked()) {
            image.setVisibility(View.VISIBLE);
        } else {
            image.setVisibility(View.INVISIBLE);
        }
    }

    /*
     * Called when randomize button is clicked. Calls setBoxes to randomly assign values to the
     * checkboxes.
     */
    public void randomizeClicked(View v) {
        setBoxes(v, true);
    }

    /*
     * Called when reset button is clicked. Calls setBoxes to clear all checkboxes.
     */
    public void resetClicked(View v) {
        setBoxes(v, false);
    }

    /*
     * Loops over all child Views in the checkbox container. Resets all checkboxes to unchecked, or
     * randomly assigns a value based on assignRandom. Updates ImageView visibility corresponding to
     * every checkbox.
     */
    public void setBoxes(View v, boolean assignRandom) {
        ViewGroup checkBoxContainer = (ViewGroup) v.getParent();

        for (int pos = 0; pos < checkBoxContainer.getChildCount(); pos++) {

            View view = checkBoxContainer.getChildAt(pos);

            if (view instanceof CheckBox) {
                CheckBox checkbox = (CheckBox) view;

                if (assignRandom && Math.random() < 0.5) {
                    checkbox.setChecked(true);
                } else {
                    checkbox.setChecked(false);
                }
                checkClicked(checkbox);
            }
        }
    }
}
