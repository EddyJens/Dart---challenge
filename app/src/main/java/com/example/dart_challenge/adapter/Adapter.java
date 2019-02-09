package com.example.dart_challenge.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.dart_challenge.R;
import com.example.dart_challenge.model.Data;

import java.util.ArrayList;
import java.util.List;

/*
adapter to each element in the list
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> implements Filterable {

    private List<Data> datas, datasFiltered;

    public Adapter(List<Data> dataList) {
        this.datas = dataList;
        this.datasFiltered = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Data data = datasFiltered.get(position);
        holder.textName.setText("Nome: " + data.getName());
        holder.textDescription.setText("Descrição: " + data.getDescription());
        holder.textUrl.setText("URL: " + data.getUrl());
    }

    @Override
    public int getItemCount() {
        return datasFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    datasFiltered = datas;
                } else {
                    List<Data> filteredList = new ArrayList<>();
                    for (Data row : datas) {

                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    datasFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = datasFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                datasFiltered = (ArrayList<Data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textName, textDescription, textUrl;

        public MyViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textDescription = itemView.findViewById(R.id.textDescription);
            textUrl = itemView.findViewById(R.id.textUrl);
        }
    }
}