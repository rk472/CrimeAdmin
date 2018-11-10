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
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.smarttersstudio.crimeadmin.pojo.Crime;
import com.smarttersstudio.crimeadmin.pojo.complain;
import com.smarttersstudio.crimeadmin.viewholder.MyComplainViewHolder;
import com.smarttersstudio.crimeadmin.viewholder.MyCrimeViewHolder;

public class ComplainActivity extends AppCompatActivity {
    private RecyclerView list;
    private FirebaseRecyclerAdapter<complain,MyComplainViewHolder> f;
    private DatabaseReference childRef;
    private EditText searchText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
        list=findViewById(R.id.my_complain_list);
        childRef= FirebaseDatabase.getInstance().getReference();
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        list.setAdapter(refresh(""));
        searchText=findViewById(R.id.complain_search);
    }
    FirebaseRecyclerAdapter<complain,MyComplainViewHolder> refresh(String s){
        Query q;
        if(TextUtils.isEmpty(s))
            q=childRef.child("complain");
        else
            q=childRef.child("complain").startAt(s).orderByChild("pin").endAt(s+"\uf8ff");
        FirebaseRecyclerOptions<complain> options=new FirebaseRecyclerOptions.Builder<complain>().setQuery(q,complain.class).build();
        f=new FirebaseRecyclerAdapter<complain, MyComplainViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MyComplainViewHolder holder, final int position, @NonNull final complain model) {
                if(model.getStatus().equalsIgnoreCase("solved")){
                    holder.setInvisible();
                }else {
                    holder.setDate(model.getDate());
                    holder.setStatus(model.getStatus());
                    holder.setTitle(model.getTitle());
                    holder.setDesc(model.getDesc());
                    holder.setPin(model.getPin());
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(model.getUid());
                    holder.callButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_CALL);
                            i.setData(Uri.parse("tel:" + model.getPhone()));
                            if (ActivityCompat.checkSelfPermission(ComplainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            startActivity(i);
                        }
                    });
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i=new Intent(ComplainActivity.this,ComplainDetailsActivity.class);
                            i.putExtra("date",model.getDate());
                            i.putExtra("id",getRef(position).getKey());
                            i.putExtra("desc",model.getDesc());
                            i.putExtra("title",model.getTitle());
                            i.putExtra("status",model.getStatus());
                            i.putExtra("pin",model.getPin());
                            startActivity(i);
                        }
                    });
                }
            }

            @NonNull
            @Override
            public MyComplainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new MyComplainViewHolder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.my_complain_row,parent,false));
            }
        };
        f.startListening();
        return f;
    }

    @Override
    protected void onStart() {
        super.onStart();
        f.startListening();
    }

    public void searchComplain(View view) {
        list.setAdapter(refresh(searchText.getText().toString()));
    }
}
