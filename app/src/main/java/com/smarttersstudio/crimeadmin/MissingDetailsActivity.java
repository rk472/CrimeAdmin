package com.smarttersstudio.crimeadmin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MissingDetailsActivity extends AppCompatActivity {
    private DatabaseReference missingRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_details);
        TextView nameText = findViewById(R.id.missing_details_name);
        TextView ageText = findViewById(R.id.missing_details_age);
        TextView statusText = findViewById(R.id.missing_details_status);
        TextView dateText = findViewById(R.id.missing_details_date);
        TextView pinText = findViewById(R.id.missing_details_pin);
        TextView genderText=findViewById(R.id.missing_details_gender);
        ImageView dp=findViewById(R.id.missing_details_photo);
        String name=getIntent().getStringExtra("name");
        String age=getIntent().getStringExtra("age");
        String status=getIntent().getStringExtra("status");
        String date=getIntent().getStringExtra("date");
        String pin=getIntent().getStringExtra("pin");
        String id=getIntent().getStringExtra("id");
        String gender=getIntent().getStringExtra("gender");
        String url=getIntent().getStringExtra("url");
        missingRef=FirebaseDatabase.getInstance().getReference().child("missing").child(id);
        nameText.setText(name);
        ageText.setText(age);
        statusText.setText(status);
        dateText.setText(date);
        pinText.setText(pin);
        genderText.setText(gender);
        Picasso.with(this).load(url).into(dp);
    }

    public void changeMissing(View view) {
        missingRef.child("status").setValue("found").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MissingDetailsActivity.this, "Status changed successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(MissingDetailsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
