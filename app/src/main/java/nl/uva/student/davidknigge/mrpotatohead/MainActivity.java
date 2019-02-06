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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout container = findViewById(R.id.imageContainer);

        if (savedInstanceState != null) {
            short[] visibilityArray = savedInstanceState.getShortArray("visibilityArray");

            for (int i = 0; i < container.getChildCount(); i++) {
                View child = container.getChildAt(i);
                if (child instanceof ImageView) {
                    ImageView image = (ImageView) child;
                    image.setVisibility((int) visibilityArray[i]);
                }
            }
        }

    }

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

    public void checkClicked(View v) {
        CheckBox checkbox = (CheckBox) v;

        String item = checkbox.getText().toString();

        int rID = getResources().getIdentifier("imageView" + item, "id", getPackageName());
        ImageView image = (ImageView) findViewById(rID);

        if (checkbox.isChecked()) {
            image.setVisibility(View.VISIBLE);
        } else {
            image.setVisibility(View.INVISIBLE);
        }
    }

    public void randomizeClicked(View v) {
        cycleBoxes(v, true);
    }

    public void resetClicked(View v) {
        cycleBoxes(v, false);
    }

    public void cycleBoxes(View v, boolean assignRandom) {
        ViewGroup checkBoxContainer = (ViewGroup) v.getParent();
        CheckBox checkbox = null;

        for (int pos = 0; pos < checkBoxContainer.getChildCount(); pos++) {

            View view = checkBoxContainer.getChildAt(pos);

            if (view instanceof CheckBox) {
                checkbox = (CheckBox) view;
                checkbox.setChecked(false);

                if (assignRandom) {
                    if (Math.random() < 0.5) {
                        checkbox.setChecked(true);
                    }
                }
                checkClicked(view);
            }
        }
    }
}
