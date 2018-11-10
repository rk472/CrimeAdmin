package com.smarttersstudio.crimeadmin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.smarttersstudio.crimeadmin.viewholder.MyCrimeViewHolder;

public class CrimeActivity extends AppCompatActivity {
    private RecyclerView list;
    private FirebaseRecyclerAdapter<Crime,MyCrimeViewHolder> f;
    private DatabaseReference childRef;
    private EditText searchText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);
        list=findViewById(R.id.crime_list);
        childRef= FirebaseDatabase.getInstance().getReference();
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        list.setAdapter(refresh(""));
        searchText=findViewById(R.id.crime_search);
    }
    FirebaseRecyclerAdapter<Crime,MyCrimeViewHolder> refresh(String s){
        Query q;
        if(TextUtils.isEmpty(s))
            q=childRef.child("crime");
        else
            q=childRef.child("crime").startAt(s).orderByChild("pin").endAt(s+"\uf8ff");
        FirebaseRecyclerOptions<Crime> options=new FirebaseRecyclerOptions.Builder<Crime>().setQuery(q,Crime.class).build();
        f=new FirebaseRecyclerAdapter<Crime, MyCrimeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MyCrimeViewHolder holder, int position, @NonNull Crime model) {
                if(model.getStatus().equalsIgnoreCase("solved")){
                    holder.setInvisible();
                }else {
                    holder.setDate(model.getDate());
                    holder.setStatus(model.getStatus());
                    holder.setTitle(model.getTitle());
                    holder.setDesc(model.getDesc());
                    holder.setPin(model.getPin());
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(model.getUid());

                }
            }

            @NonNull
            @Override
            public MyCrimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new MyCrimeViewHolder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.my_crime_row,parent,false));
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

    public void searchCrime(View view) {
        list.setAdapter(refresh(searchText.getText().toString()));
    }
}
