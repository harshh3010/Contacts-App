package com.codebee.contactsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolderClass> {

    private ArrayList<Contact> myArr;
    private Context context;

    public ContactAdapter(ArrayList<Contact> myArr) {
        this.myArr = myArr;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact,parent,false);
        context = parent.getContext();
        return  new ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
        holder.name_txt.setText(myArr.get(position).getFname() + " "  + myArr.get(position).getLName());
    }

    @Override
    public int getItemCount() {
        return myArr.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder {
        public TextView name_txt;
        public ImageView call_img;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);

            name_txt = itemView.findViewById(R.id.item_contact_name_text);
            call_img = itemView.findViewById(R.id.item_contact_call_image);
        }
    }

}
