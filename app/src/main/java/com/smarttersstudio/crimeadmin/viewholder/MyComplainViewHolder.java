package com.smarttersstudio.crimeadmin.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smarttersstudio.crimeadmin.R;

public class MyComplainViewHolder extends RecyclerView.ViewHolder {
    private TextView titleText,statusText,dateText,descText,pinText;
    private View v;
    public Button callButton;
    public MyComplainViewHolder(View itemView) {
        super(itemView);
        titleText=itemView.findViewById(R.id.my_complain_row_title);
        statusText=itemView.findViewById(R.id.my_complain_row_status);
        dateText=itemView.findViewById(R.id.my_complain_row_date);
        descText=itemView.findViewById(R.id.my_complain_row_desc);
        pinText=itemView.findViewById(R.id.my_complain_row_pin);
        callButton=itemView.findViewById(R.id.my_complain_row_call);
        v=itemView;
    }
    public void setInvisible(){
        v.setLayoutParams(new LinearLayout.LayoutParams(0,0));
    }
    public void setTitle(String title){
        titleText.setText(title);
    }
    public void setStatus(String status){
        statusText.setText(status);
    }
    public void setDate(String date){
        dateText.setText(date);
    }
    public void setDesc(String desc){ descText.setText(desc);
    }
    public void setPin(String pin){
        pinText.setText(pin);
    }

}
