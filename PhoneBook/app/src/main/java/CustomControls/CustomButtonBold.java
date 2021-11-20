package CustomControls;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import ir.ncis.phonebook.App;

public class CustomButtonBold extends AppCompatButton {
    public CustomButtonBold(Context context) {
        super(context);
        init();
    }

    public CustomButtonBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomButtonBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTypeface(App.FONT_BOLD, Typeface.BOLD);
    }
}
