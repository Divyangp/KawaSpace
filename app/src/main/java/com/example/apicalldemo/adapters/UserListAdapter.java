package com.example.apicalldemo.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apicalldemo.R;
import com.example.apicalldemo.models.ResultsEntity;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserHolder> {
    private List<ResultsEntity> results = new ArrayList<>();
    private int selectedItemPos = 0;
    private final Context context;
    private final OnItemClickListener listener;

    public UserListAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(ResultsEntity item, int pos);
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new UserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        ResultsEntity resultsEntity = results.get(position);
        holder.tvGender.setText(String.format("%s .", resultsEntity.gender));
        holder.tvNat.setText(resultsEntity.nat);
        holder.tvNameTitle.setText(String.format("%s. ", resultsEntity.name.title));
        holder.tvNameFirst.setText(resultsEntity.name.first);
        holder.tvNameLast.setText(resultsEntity.name.last);
        holder.tvEmail.setText(resultsEntity.email);
        if (selectedItemPos == position) {
            holder.clUserItem.setBackgroundColor(context.getResources().getColor(R.color.colorSelectedCard));
            holder.tvGender.setTextColor(context.getResources().getColor(R.color.colorWhite));
            holder.tvNat.setTextColor(context.getResources().getColor(R.color.colorWhite));
            holder.tvNameTitle.setTextColor(context.getResources().getColor(R.color.colorWhite));
            holder.tvNameFirst.setTextColor(context.getResources().getColor(R.color.colorWhite));
            holder.tvNameLast.setTextColor(context.getResources().getColor(R.color.colorWhite));
            holder.tvEmail.setTextColor(context.getResources().getColor(R.color.colorWhite));
        } else {
            holder.clUserItem.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            holder.tvGender.setTextColor(context.getResources().getColor(R.color.colorBlack));
            holder.tvNat.setTextColor(context.getResources().getColor(R.color.colorBlack));
            holder.tvNameTitle.setTextColor(context.getResources().getColor(R.color.colorBlack));
            holder.tvNameFirst.setTextColor(context.getResources().getColor(R.color.colorBlack));
            holder.tvNameLast.setTextColor(context.getResources().getColor(R.color.colorBlack));
            holder.tvEmail.setTextColor(context.getResources().getColor(R.color.colorOrange));
        }
        holder.clUserItem.setOnClickListener(v -> listener.onItemClick(resultsEntity, position));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public ResultsEntity getSelectedUser() {
        return results.get(selectedItemPos);
    }

    public int getSelectedItemPos() {
        return selectedItemPos;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelectedItemPos(int pos) {
        selectedItemPos = pos;
        notifyDataSetChanged();
    }

    public ResultsEntity getItemAtPos(int pos) {
        return results.get(pos);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setResults(List<ResultsEntity> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    static class UserHolder extends RecyclerView.ViewHolder {
        private final TextView tvGender;
        private final TextView tvNat;
        private final TextView tvNameTitle;
        private final TextView tvNameFirst;
        private final TextView tvNameLast;
        private final TextView tvEmail;
        private final ConstraintLayout clUserItem;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            clUserItem = itemView.findViewById(R.id.clUserItem);
            tvGender = itemView.findViewById(R.id.tvGender);
            tvNat = itemView.findViewById(R.id.tvNat);
            tvNameTitle = itemView.findViewById(R.id.tvNameTitle);
            tvNameFirst = itemView.findViewById(R.id.tvNameFirst);
            tvNameLast = itemView.findViewById(R.id.tvNameLast);
            tvEmail = itemView.findViewById(R.id.tvEmail);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
