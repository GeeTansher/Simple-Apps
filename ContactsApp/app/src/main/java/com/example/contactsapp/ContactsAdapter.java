package com.example.contactsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ContactsAdapter extends ArrayAdapter<Contact> {
    private final Context context;
    private final List<Contact> contacts;

    public ContactsAdapter(Context context,List<Contact> list)
    {
        super(context,R.layout.row_layout,list);
        this.context = context;
        this.contacts = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_layout,parent,false);

        TextView tvchar = convertView.findViewById(R.id.tvchar);
        TextView tvname = convertView.findViewById(R.id.tvname);
        TextView tvnumber = convertView.findViewById(R.id.tvnumber);

        tvchar.setText(contacts.get(position).getName().toUpperCase().charAt(0) + "");
        tvname.setText(contacts.get(position).getName());
        tvnumber.setText(contacts.get(position).getNumber());

        return convertView;
    }
}

