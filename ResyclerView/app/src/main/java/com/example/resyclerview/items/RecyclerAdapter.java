package com.example.resyclerview.items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resyclerview.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter {
    private final List<RowType> data = new ArrayList<>();

    public RecyclerAdapter() {
    }

    @Override
    public int getItemViewType(int position) {
        if(data.get(position) instanceof ImageItem) {
            return RowType.IMAGE_ROW_TYPE;
        }
        else if (data.get(position) instanceof DescriptionItem) {
            return RowType.DESCRIPTION_ROW_TYPE;
        } else {
            return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_view, parent, false);
        if(viewType == RowType.IMAGE_ROW_TYPE){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return new ImageItemHolder(view);
        }
        else return new DescriptionItemHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ImageItemHolder){
            ((ImageItemHolder) holder).bind((ImageItem) data.get(position));
            ((ImageItemHolder) holder).getmView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeData(position);
                }
            });
        }
        else if (holder instanceof DescriptionItemHolder){
            ((DescriptionItemHolder) holder).bind((DescriptionItem) data.get(position));
            ((DescriptionItemHolder)holder).getmView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeData(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public int addData(List<RowType> list){
        data.addAll(list);
        notifyDataSetChanged();
        return data.size()-1;
    }
    public void removeData(int position){
        data.remove(position);
        notifyDataSetChanged();
    }

    public String[] toStringList() {
        List<String> list = new ArrayList<>(getItemCount()*3);
        for (RowType t: data){
            if(t instanceof ImageItem){
                list.add("ImageItem");
                list.add(((ImageItem) t).getImage());
                list.add(((ImageItem) t).getName());
            }
            else if (t instanceof DescriptionItem){
                list.add("TextItem");
                list.add(((DescriptionItem) t).getName());
                list.add(((DescriptionItem) t).getDescription());
            }
        }
        String[] strings = new String[list.size()];
        strings = list.toArray(strings);
        return strings;
    }
    public void fromStringList(List<String> list){
        for(int i=0; i<list.size(); i+=3){
            if(list.get(i).equalsIgnoreCase("ImageItem")){
                data.add(new ImageItem(list.get(i+2), list.get(i+1)));
            }
            else if(list.get(i).equalsIgnoreCase("TextItem")){
                data.add(new DescriptionItem(list.get(i+1), list.get(i+2)));
            }
        }
        notifyDataSetChanged();
    }
}
