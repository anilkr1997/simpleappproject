package com.nic.simpleappproject.Adopter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nic.simpleappproject.Model.RealDbdatabase;
import com.nic.simpleappproject.databinding.TasklistdataBinding;

import io.realm.RealmResults;

public class TaskAdopterList extends RecyclerView.Adapter<TaskAdopterList.MyViewHolder> {
    private RealmResults<RealDbdatabase> realDbdatabases;
    private Context context;
    private Itemonclick itemonclick;

    @Override
    public void onViewRecycled(@NonNull MyViewHolder holder) {
        super.onViewRecycled(holder);

    }

    public TaskAdopterList(Context applicationContext, RealmResults<RealDbdatabase> realDbdatabases, Itemonclick itemonclick) {
        this.context = applicationContext;
        this.realDbdatabases = realDbdatabases;
        this.itemonclick=itemonclick;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskAdopterList.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(TasklistdataBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(TaskAdopterList.MyViewHolder holder, int position) {
        holder.binding.tvName.setText(realDbdatabases.get(position).getSchoolname().toString());
        holder.binding.tvPnumber.setText(realDbdatabases.get(position).getPhonnumber());
        holder.binding.tvTitle.setText(realDbdatabases.get(position).getSchcatgory().toString());
        holder.binding.tvDescrepthin.setText(realDbdatabases.get(position).getSchooltype().toString());
        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemonclick.OnDeleteItem(realDbdatabases.get(position));


                notifyItemRemoved(position);
            }
        });
        holder.binding.btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemonclick.OnViewMapItem(realDbdatabases.get(position));
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return realDbdatabases.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TasklistdataBinding binding;

        public MyViewHolder(TasklistdataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    public interface Itemonclick {
        void OnDeleteItem(RealDbdatabase realDbdatabase);
        void OnViewMapItem(RealDbdatabase realDbdatabase);
    }
}
