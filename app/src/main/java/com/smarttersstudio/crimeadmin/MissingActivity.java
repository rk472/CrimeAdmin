package com.smarttersstudio.crimeadmin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.smarttersstudio.crimeadmin.pojo.Missing;
import com.smarttersstudio.crimeadmin.viewholder.MyMissingViewHolder;

public class MissingActivity extends AppCompatActivity {

    private RecyclerView list;
    private FirebaseRecyclerAdapter<Missing, MyMissingViewHolder> f;
    private DatabaseReference childRef;
    private EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing);
        list = findViewById(R.id.missing_list);
        searchText = findViewById(R.id.missing_search);
        childRef = FirebaseDatabase.getInstance().getReference();
        list.setAdapter(getAdapter(""));
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    FirebaseRecyclerAdapter<Missing, MyMissingViewHolder> getAdapter(String s) {
        Query q;
        if (TextUtils.isEmpty(s))
            q = childRef.child("missing");
        else
            q = childRef.child("missing").startAt(s).orderByChild("pin").endAt(s + "\uf8ff");
        FirebaseRecyclerOptions<Missing> options = new FirebaseRecyclerOptions.Builder<Missing>().setQuery(q, Missing.class).build();
        f = new FirebaseRecyclerAdapter<Missing, MyMissingViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MyMissingViewHolder holder, final int position, @NonNull final Missing model) {
                holder.setDate(model.getDate());
                holder.setPin(model.getPin());
                holder.setStatus(model.getStatus());
                holder.setAge(model.getAge());
                holder.setGender(model.getGender());
                holder.setImage(model.getImage(), getApplicationContext());
                holder.setName(model.getName());
                holder.callButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_CALL);
                        i.setData(Uri.parse("tel:" + model.getPhone()));
                        if (ActivityCompat.checkSelfPermission(MissingActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(i);
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MissingActivity.this, MissingDetailsActivity.class);
                        i.putExtra("url", model.getImage());
                        i.putExtra("name", model.getName());
                        i.putExtra("age", model.getAge());
                        i.putExtra("gender", model.getGender());
                        i.putExtra("status", model.getStatus());
                        i.putExtra("pin", model.getPin());
                        i.putExtra("date", model.getDate());
                        i.putExtra("id", getRef(position).getKey());
                        startActivity(i);
                    }
                });
            }
            @NonNull
            @Override
            public MyMissingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new MyMissingViewHolder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.my_missing_row,parent,false));
            }
        };
        f.startListening();
        return f;
    }
    

    public void searchMissing(View view) {
        list.setAdapter(getAdapter(searchText.getText().toString()));
    }
}
