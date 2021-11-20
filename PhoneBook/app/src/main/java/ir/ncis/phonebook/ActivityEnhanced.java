package ir.ncis.phonebook;

import android.support.v7.app.AppCompatActivity;

public class ActivityEnhanced extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        App.ACTIVITY = this;
    }
}
