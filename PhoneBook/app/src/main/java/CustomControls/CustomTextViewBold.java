package CustomControls;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import ir.ncis.phonebook.App;

public class CustomTextViewBold extends AppCompatTextView {
    public CustomTextViewBold(Context context) {
        super(context);
        init();
    }

    public CustomTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextViewBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTypeface(App.FONT_BOLD, Typeface.BOLD);
    }
}
