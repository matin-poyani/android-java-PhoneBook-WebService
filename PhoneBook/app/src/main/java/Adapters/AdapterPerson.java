package Adapters;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;

import Async.AsyncDelete;
import Async.AsyncRead;
import Async.AsyncUpdate;
import CustomControls.CustomButtonBold;
import CustomControls.CustomTextView;
import Dialogs.DialogAdd;
import Dialogs.DialogConfirm;
import Models.Persons;
import Structures.StructPerson;
import ir.ncis.phonebook.App;
import ir.ncis.phonebook.R;

public class AdapterPerson extends RecyclerView.Adapter<AdapterPerson.ViewHolder> {
    public ArrayList<StructPerson> persons;

    public AdapterPerson(ArrayList<StructPerson> persons) {
        this.persons = persons;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = App.INFLATER.inflate(R.layout.adapter_person, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final StructPerson person = persons.get(position);
        holder.imgAvatar.setImageResource(person.male ? R.drawable.male : R.drawable.female);
        holder.imgStatus.setColorFilter(App.getColorCompact(person.stored ? R.color.colorGreen : R.color.colorRed));
        holder.txtName.setText(person.name);
        holder.txtMobile.setText(person.mobile);
        holder.imgFriend.setImageResource(person.friend ? android.R.drawable.checkbox_on_background : android.R.drawable.checkbox_off_background);
        holder.rtbScore.setRating(person.score);
        holder.btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.toast(person.address, Toast.LENGTH_LONG);
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogAdd(App.ACTIVITY, person)
                        .setListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                notifyDataSetChanged();
                            }
                        })
                        .show();
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogConfirm(App.ACTIVITY)
                        .setTitle(App.CONTEXT.getString(R.string.confirm_delete_title))
                        .setMessage(App.CONTEXT.getString(R.string.confirm_delete_message))
                        .setOnConfirmListener(new DialogConfirm.OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                new AsyncDelete()
                                        .setOnDataReceivedListener(new AsyncDelete.OnDataReceivedListener() {
                                            @Override
                                            public void OnDataReceived(boolean result) {
                                                if (result) {
                                                    if (Persons.delete(person.id)) {
                                                        App.toast(App.CONTEXT.getString(R.string.delete_ok));
                                                        persons = Persons.all();
                                                        notifyDataSetChanged();
                                                    } else {
                                                        App.toast(App.CONTEXT.getString(R.string.delete_error));
                                                    }
                                                } else {
                                                    App.toast(App.CONTEXT.getString(R.string.delete_error));
                                                }
                                            }
                                        })
                                        .execute(person.id);
                            }
                        })
                        .show();
            }
        });
        holder.imgDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogConfirm(App.ACTIVITY)
                        .setTitle(App.CONTEXT.getString(R.string.download_confirm_title))
                        .setMessage(App.CONTEXT.getString(R.string.download_confirm_message))
                        .setOnConfirmListener(new DialogConfirm.OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                new AsyncRead()
                                        .setOnDataReceivedListener(new AsyncRead.OnDataReceivedListener() {
                                            @Override
                                            public void OnDataReceived(ArrayList<StructPerson> result) {
                                                if (result != null && result.size() > 0) {
                                                    StructPerson structPerson = result.get(0);
                                                    structPerson.stored = true;
                                                    person.load(structPerson);
                                                    Persons.save(person);
                                                    notifyDataSetChanged();
                                                    App.toast(App.CONTEXT.getString(R.string.download_success_message));
                                                } else {
                                                    App.toast(App.CONTEXT.getString(R.string.download_error_message));
                                                }
                                            }
                                        })
                                        .execute(person.id);
                            }
                        })
                        .show();
            }
        });
        holder.imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogConfirm(App.ACTIVITY)
                        .setTitle(App.CONTEXT.getString(R.string.upload_confirm_title))
                        .setMessage(App.CONTEXT.getString(R.string.upload_confirm_message))
                        .setOnConfirmListener(new DialogConfirm.OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                new AsyncUpdate()
                                        .setOnDataReceivedListener(new AsyncUpdate.OnDataReceivedListener() {
                                            @Override
                                            public void OnDataReceived(boolean result) {
                                                if (result) {
                                                    person.stored = true;
                                                    Persons.save(person);
                                                    notifyDataSetChanged();
                                                    App.toast(App.CONTEXT.getString(R.string.save_success));
                                                } else {
                                                    App.toast(App.CONTEXT.getString(R.string.save_error));
                                                }
                                            }
                                        })
                                        .execute(person);
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CustomButtonBold btnAddress;
        public CustomButtonBold btnEdit;
        public CustomTextView txtMobile;
        public CustomTextView txtName;
        public ImageView imgAvatar;
        public ImageView imgDelete;
        public ImageView imgDownload;
        public ImageView imgFriend;
        public ImageView imgStatus;
        public ImageView imgUpload;
        public RatingBar rtbScore;

        public ViewHolder(View itemView) {
            super(itemView);
            btnAddress = (CustomButtonBold) itemView.findViewById(R.id.btnAddress);
            btnEdit = (CustomButtonBold) itemView.findViewById(R.id.btnEdit);
            txtMobile = (CustomTextView) itemView.findViewById(R.id.txtMobile);
            txtName = (CustomTextView) itemView.findViewById(R.id.txtName);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgDownload = (ImageView) itemView.findViewById(R.id.imgDownload);
            imgFriend = (ImageView) itemView.findViewById(R.id.imgFriend);
            imgStatus = (ImageView) itemView.findViewById(R.id.imgStatus);
            imgUpload = (ImageView) itemView.findViewById(R.id.imgUpload);
            rtbScore = (RatingBar) itemView.findViewById(R.id.rtbScore);
        }
    }
}
