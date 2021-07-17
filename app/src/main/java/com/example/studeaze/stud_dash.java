package com.example.studeaze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class stud_dash extends AppCompatActivity {
    private TextView student_name;
    private Button view_attendance, timetable, notice, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_dash);

        student_name = findViewById(R.id.S_text_name);
        view_attendance = findViewById(R.id.view_attendance);
        timetable = findViewById(R.id.S_view_class);
        notice = findViewById(R.id.S_notice);
        logout = findViewById(R.id.S_log_out);

        Paper.init(this);

        String UserUsnKey = Paper.book().read("usn");
        String UserPasswordKey = Paper.book().read("password");

        if(UserUsnKey != "" && UserPasswordKey != ""){
            if(!TextUtils.isEmpty(UserUsnKey) && !TextUtils.isEmpty(UserPasswordKey)){
                studentNameDisplay(UserUsnKey, UserPasswordKey);
            }
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                Intent intent = new Intent(stud_dash.this, home.class);
                startActivity(intent);
            }
        });
    }

    private void studentNameDisplay(String usn, String s_pass) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("students").child(usn).exists()){
                    students studentsData = dataSnapshot.child("students").child(usn).getValue(students.class);

                    if(studentsData.getUsn().equals(usn)){
                        if(studentsData.getPassword().equals(s_pass)){
                            String s_name = studentsData.getName();
                            student_name.setText(s_name);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(stud_dash.this,home.class);
        startActivity(intent);
    }
}