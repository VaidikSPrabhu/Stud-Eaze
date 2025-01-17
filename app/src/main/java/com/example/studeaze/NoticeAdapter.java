package com.example.studeaze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NoticeAdapter extends AppCompatActivity { //Adapter class for notice
    //user interface elements
    RecyclerView recyclerView;
    DatabaseReference rootref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  //called when activity is started, to perform initialisation
        setContentView(R.layout.activity_notice_adapter);

        //Finds a view that was identified by the android:id XML attribute that was processed in onCreate.
        recyclerView = findViewById(R.id.notice_display);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        rootref = FirebaseDatabase.getInstance().getReference().child("notice");  //Gets a DatabaseReference for the database specified child node.
    }
    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<NoticeActivity> options =
                new FirebaseRecyclerOptions.Builder<NoticeActivity>()
                        .setQuery(rootref, NoticeActivity.class)
                        .build();
        //Firebase adapter to set text into holder
        FirebaseRecyclerAdapter<NoticeActivity, NoticeViewHolder> adapter =
                new FirebaseRecyclerAdapter<NoticeActivity, NoticeViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull NoticeViewHolder holder, final int position, @NonNull final NoticeActivity model) {
                        holder.date.setText("Date: " + model.getDate());
                        holder.time.setText("Time: " + model.getTime());
                        holder.headng.setText("" + model.getNote_head());
                        holder.descrpt.setText("" + model.getNote_desc());

                    }

                    @NonNull
                    @Override
                    //View holder
                    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item, parent, false);
                        return new NoticeViewHolder(view);
                    }
                };
        //setting adapter into recyclerView
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}