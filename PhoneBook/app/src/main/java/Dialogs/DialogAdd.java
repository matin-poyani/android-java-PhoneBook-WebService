package Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;

import Async.AsyncCreate;
import CustomControls.CustomButtonBold;
import CustomControls.CustomEditText;
import CustomControls.CustomSeekBar;
import CustomControls.CustomTextViewBold;
import Models.Persons;
import Structures.StructPerson;
import ir.ncis.phonebook.App;
import ir.ncis.phonebook.R;

public class DialogAdd extends Dialog {
    private StructPerson person;
    private OnDismissListener listener;

    private AppCompatCheckBox chkFriend;
    private CustomButtonBold btnSave;
    private CustomEditText edtName;
    private CustomEditText edtMobile;
    private CustomEditText edtAddress;
    private CustomSeekBar sbScore;
    private CustomTextViewBold txtFemale;
    private CustomTextViewBold txtMale;
    private ImageView imgFemale;
    private ImageView imgMale;
    private ViewGroup lytFemale;
    private ViewGroup lytMale;

    private int colorAccent;
    private int colorLightest;

    public DialogAdd(Context context, StructPerson person) {
        super(context);
        this.person = person;
        init();
    }

    public DialogAdd(Context context, int themeResId, StructPerson person) {
        super(context, themeResId);
        this.person = person;
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add);

        getItems();

        chkFriend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                person.friend = isChecked;
            }
        });

        lytFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                person.male = false;
                txtFemale.setTextColor(colorAccent);
                imgFemale.setColorFilter(colorAccent);
                txtMale.setTextColor(colorLightest);
                imgMale.setColorFilter(colorLightest);
            }
        });

        lytMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                person.male = true;
                txtFemale.setTextColor(colorLightest);
                imgFemale.setColorFilter(colorLightest);
                txtMale.setTextColor(colorAccent);
                imgMale.setColorFilter(colorAccent);
            }
        });

        sbScore.setOnProgressChangedListener(new CustomSeekBar.OnProgressChangedListener() {
            @Override
            public void OnProgressChanged(float score) {
                person.score = score;
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                person.name = edtName.getText().toString();
                person.mobile = edtMobile.getText().toString();
                person.address = edtAddress.getText().toString();
                person.stored = true;

                if (person.id == 0) {
                    new AsyncCreate()
                            .setOnDataReceivedListener(new AsyncCreate.OnDataReceivedListener() {
                                @Override
                                public void OnDataReceived(int result) {
                                    if (result > 0) {
                                        person.id = result;
                                        if (Persons.save(person)) {
                                            App.toast(App.CONTEXT.getString(R.string.save_success));
                                            dismiss();
                                        } else {
                                            App.toast(App.CONTEXT.getString(R.string.save_error));
                                        }
                                    } else {
                                        App.toast(App.CONTEXT.getString(R.string.save_error));
                                    }
                                }
                            })
                            .execute(person);
                } else {
                    person.stored = false;
                    if (Persons.save(person)) {
                        App.toast(App.CONTEXT.getString(R.string.save_success));
                        dismiss();
                    } else {
                        App.toast(App.CONTEXT.getString(R.string.save_error));
                    }
                }
            }
        });
    }

    private void getItems() {
        colorAccent = ContextCompat.getColor(App.CONTEXT, R.color.colorAccent);
        colorLightest = ContextCompat.getColor(App.CONTEXT, R.color.colorLightest);

        if (person == null) {
            person = new StructPerson();
            person.male = true;
        }

        chkFriend = (AppCompatCheckBox) findViewById(R.id.chkFriend);
        chkFriend.setChecked(person.friend);

        btnSave = (CustomButtonBold) findViewById(R.id.btnSave);

        edtName = (CustomEditText) findViewById(R.id.edtName);
        edtName.setText(person.name);

        edtMobile = (CustomEditText) findViewById(R.id.edtMobile);
        edtMobile.setText(person.mobile);

        edtAddress = (CustomEditText) findViewById(R.id.edtAddress);
        edtAddress.setText(person.address);

        txtFemale = (CustomTextViewBold) findViewById(R.id.txtFemale);
        txtMale = (CustomTextViewBold) findViewById(R.id.txtMale);
        imgFemale = (ImageView) findViewById(R.id.imgFemale);
        imgMale = (ImageView) findViewById(R.id.imgMale);

        if (person.male) {
            txtFemale.setTextColor(colorLightest);
            imgFemale.setColorFilter(colorLightest);
            txtMale.setTextColor(colorAccent);
            imgMale.setColorFilter(colorAccent);
        } else {
            txtFemale.setTextColor(colorAccent);
            imgFemale.setColorFilter(colorAccent);
            txtMale.setTextColor(colorLightest);
            imgMale.setColorFilter(colorLightest);
        }

        sbScore = (CustomSeekBar) findViewById(R.id.sbScore);
        sbScore.setScore(person.score);

        lytFemale = (ViewGroup) findViewById(R.id.lytFemale);
        lytMale = (ViewGroup) findViewById(R.id.lytMale);

    }

    public DialogAdd setListener(OnDismissListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (listener != null) {
            listener.onDismiss(this);
        }
    }
}
