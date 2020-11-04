package com.example.apprunner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.Holder> {

    private List<UserListResponse> mDataSet;
    private Context context;

    public MyRecyclerAdapter(Context context, List<UserListResponse> mDataSet){
        this.context = context;
        this.mDataSet = mDataSet;
    }

    @NonNull
    @Override
    public MyRecyclerAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_event, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter.Holder holder, int position) {
        final Intent intent = ((Activity) context).getIntent();
        final String type = intent.getExtras().getString("type");
        final UserListResponse userListResponse = mDataSet.get(position);
        holder.tv_event_name.setText(userListResponse.getName_event());
        holder.tv_producer_name.setText(userListResponse.getName_producer());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(context, DetailEventUserActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("name_event", userListResponse.getName_event());
                    intent.putExtra("date_reg_start", userListResponse.date_reg_start());
                    intent.putExtra("date_reg_end", userListResponse.date_reg_end());
                    intent.putExtra("detail", userListResponse.getDetail());
                    context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_event_name,tv_producer_name;
        ConstraintLayout mainLayout;
        CardView card_view;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_event_name = itemView.findViewById(R.id.tv_event_name);
            tv_producer_name = itemView.findViewById(R.id.tv_producer_name);
            mainLayout = itemView.findViewById(R.id.recycler_view);
            mainLayout = itemView.findViewById(R.id.recycler_view);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }
}
