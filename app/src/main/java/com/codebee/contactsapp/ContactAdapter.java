package com.codebee.contactsapp;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolderClass> implements Filterable {

    private ArrayList<Contact> myArr;
    private Context context;
    private ArrayList<Contact> myArrFull;

    public ContactAdapter(ArrayList<Contact> myArr) {
        this.myArr = myArr;
        myArrFull = new ArrayList<>(myArr);
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        context = parent.getContext();
        return new ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
        holder.name_txt.setText(myArr.get(position).getFname() + " " + myArr.get(position).getLName());
    }

    @Override
    public int getItemCount() {
        return myArr.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Contact> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(myArrFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Contact contact : myArrFull) {
                    String name = contact.getFname() + " " + contact.getLName();
                    if (name.toLowerCase().contains(filterPattern)) {
                        filteredList.add(contact);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            myArr.clear();
            myArr.addAll((Collection<? extends Contact>) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolderClass extends RecyclerView.ViewHolder {
        public TextView name_txt;
        public ImageView call_img;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);

            name_txt = itemView.findViewById(R.id.item_contact_name_text);
            call_img = itemView.findViewById(R.id.item_contact_call_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ContactActivity.class);
                    intent.putExtra("contact", myArr.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });

            call_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) context).callContact(myArr.get(getAdapterPosition()).getMobile());
                }
            });
        }
    }
}
