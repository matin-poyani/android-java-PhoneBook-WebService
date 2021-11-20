package CustomControls;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import ir.ncis.phonebook.App;
import ir.ncis.phonebook.R;

public class CustomSeekBar extends LinearLayout {
    private CustomTextView txtProgress;
    private SeekBar sbProgress;
    private OnProgressChangedListener onProgressChangedListener;

    public CustomSeekBar(Context context) {
        super(context);
        init();
    }

    public CustomSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public float getScore() {
        return Float.parseFloat(txtProgress.getText().toString());
    }

    public void setScore(float value) {
        sbProgress.setProgress((int) (value * 2));
    }

    private void init() {
        if (!isInEditMode()) {
            View view = App.INFLATER.inflate(R.layout.custom_seek_bar, this, true);
            txtProgress = (CustomTextView) view.findViewById(R.id.txtProgress);
            sbProgress = (SeekBar) view.findViewById(R.id.sbProgress);
            sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    float value = progress / 2.0f;
                    txtProgress.setText(String.valueOf(value));
                    if (onProgressChangedListener != null) {
                        onProgressChangedListener.OnProgressChanged(value);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        }
    }

    public void setOnProgressChangedListener(OnProgressChangedListener onProgressChangedListener) {
        this.onProgressChangedListener = onProgressChangedListener;
    }

    public interface OnProgressChangedListener {
        void OnProgressChanged(float score);
    }
}
