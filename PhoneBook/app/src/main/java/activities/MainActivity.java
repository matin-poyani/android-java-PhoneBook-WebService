package activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import Adapters.AdapterPerson;
import Async.AsyncRead;
import Dialogs.DialogAdd;
import Dialogs.DialogConfirm;
import Models.Persons;
import Structures.StructPerson;
import ir.ncis.phonebook.ActivityEnhanced;
import ir.ncis.phonebook.App;
import ir.ncis.phonebook.R;

public class MainActivity extends ActivityEnhanced {
    private AdapterPerson adapter;
    private RecyclerView rvPersons;
    private SwipeRefreshLayout swpReload;
    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPersons = (RecyclerView) findViewById(R.id.rvPersons);
        rvPersons.setHasFixedSize(true);
        rvPersons.setLayoutManager(new LinearLayoutManager(App.ACTIVITY));
        adapter = new AdapterPerson(Persons.all());
        rvPersons.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        swpReload = (SwipeRefreshLayout) findViewById(R.id.swpReload);

        if (Persons.all().size() == 0) {
            new AsyncRead()
                    .setStartAction(new Runnable() {
                        @Override
                        public void run() {
                            swpReload.setRefreshing(true);
                        }
                    })
                    .setOnDataReceivedListener(new AsyncRead.OnDataReceivedListener() {
                        @Override
                        public void OnDataReceived(ArrayList<StructPerson> result) {
                            for (StructPerson person : result) {
                                Persons.save(person);
                            }
                            adapter.persons = Persons.all();
                            adapter.notifyDataSetChanged();
                            swpReload.setRefreshing(false);
                        }
                    })
                    .execute(0);
        }

        swpReload.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new DialogConfirm(App.ACTIVITY)
                        .setTitle(getString(R.string.reload_confirm_title))
                        .setMessage(getString(R.string.reload_confirm_message))
                        .setOnConfirmListener(new DialogConfirm.OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                new AsyncRead()
                                        .setStartAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                swpReload.setRefreshing(true);
                                            }
                                        })
                                        .setOnDataReceivedListener(new AsyncRead.OnDataReceivedListener() {
                                            @Override
                                            public void OnDataReceived(ArrayList<StructPerson> result) {
                                                Persons.clear();
                                                for (StructPerson person : result) {
                                                    Persons.save(person);
                                                }
                                                adapter.persons = Persons.all();
                                                adapter.notifyDataSetChanged();
                                                swpReload.setRefreshing(false);
                                            }
                                        })
                                        .execute(0);
                            }
                        })
                        .show();
            }
        });

        findViewById(R.id.btnNewRecord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogAdd(App.ACTIVITY, null)
                        .setListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                swpReload.setRefreshing(true);
                                adapter.persons = Persons.all();
                                adapter.notifyDataSetChanged();
                                swpReload.setRefreshing(false);
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            System.exit(0);
        } else {
            exit = true;
            App.toast(getString(R.string.confirm_exit));
            App.HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);
        }
    }
}
