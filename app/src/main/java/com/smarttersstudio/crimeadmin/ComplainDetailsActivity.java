package com.smarttersstudio.crimeadmin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ComplainDetailsActivity extends AppCompatActivity {

    private String changedStatus="";
    private DatabaseReference complainRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_details);
        TextView titleText = findViewById(R.id.complain_details_title);
        TextView descText = findViewById(R.id.complain_details_desc);
        TextView statusText = findViewById(R.id.complain_details_status);
        TextView dateText = findViewById(R.id.complain_details_date);
        TextView pinText = findViewById(R.id.complain_details_pin);
        Button changeButton = findViewById(R.id.change_complain_button);
        String title=getIntent().getStringExtra("title");
        String desc=getIntent().getStringExtra("desc");
        String status=getIntent().getStringExtra("status");
        String date=getIntent().getStringExtra("date");
        String pin=getIntent().getStringExtra("pin");
        String id=getIntent().getStringExtra("id");
        complainRef=FirebaseDatabase.getInstance().getReference().child("complain").child(id);
        titleText.setText(title);
        descText.setText(desc);
        statusText.setText(status);
        dateText.setText(date);
        pinText.setText(pin);
        if(status.equals("submitted")){
            changeButton.setText("Start Enquiry");
            changedStatus="processing";
        }else if(status.equals("processing")){
            changeButton.setText("End Enquiry");
            changedStatus="solved";
        }
    }

    public void changeComplain(View view) {
        complainRef.child("status").setValue(changedStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ComplainDetailsActivity.this, "Status of the crime changed successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(ComplainDetailsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
