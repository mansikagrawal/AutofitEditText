package com.example.autofitedittext;

import androidx.appcompat.app.AppCompatActivity;
;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toolbar;



public class MainActivity extends Activity {
   private   AutoResizeEditText mAutoResizeEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

mAutoResizeEditText=(AutoResizeEditText)findViewById(R.id.rET);
        mAutoResizeEditText.setEnabled(true);
        mAutoResizeEditText.setFocusableInTouchMode(true);
        mAutoResizeEditText.setFocusable(true);
        mAutoResizeEditText.setEnableSizeCache(false);
       // mAutoResizeEditText.setMovementMethod(null);
        // can be added after layout inflation; it doesn't have to be fixed
        // value
        mAutoResizeEditText.setMaxHeight(330);



    }


    public void setupUI(View view, final AutoResizeEditText aText) {

        // if the view is not instance of AutoResizeEditText
        // i.e. if the user taps outside of the box
        if (!(view instanceof AutoResizeEditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();

                    Log.d("TXTS",
                            "Text Size = "
                                    + aText.getTextSize());
                    if (aText.getTextSize() < 50f) {
                        // you can define your minSize, in this case is 50f
                        // trim all the new lines and set the text as it was
                        // before
                        aText.setText(aText.getText().toString().replaceAll("(?m)^[ \t]*\r?\n", ""));
                    }

                    return false;
                }
            });
        }

        // If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, aText);
            }
        }
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (this.getCurrentFocus() != null
                && this.getCurrentFocus().getWindowToken() != null)
            inputMethodManager.hideSoftInputFromWindow(this
                    .getCurrentFocus().getWindowToken(), 0);
    }






    @Override
    protected void onResume() {
        super.onResume();
        setupUI(this.<View>findViewById(R.id.rlRoot), mAutoResizeEditText);

    }


}
