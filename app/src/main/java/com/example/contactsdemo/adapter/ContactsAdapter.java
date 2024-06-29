package com.example.contactsdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.contactsdemo.models.ContactClass;
import com.example.contactsdemo.utils.ToolUtils;

import java.util.List;

/**
 * @author Phobos
 */
public class ContactsAdapter extends BaseAdapter {

    private List<ContactClass> itemList;
    private LayoutInflater inflater;
    private ToolUtils toolUtils=new ToolUtils();

    public ContactsAdapter(List<ContactClass> itemList, LayoutInflater inflater) {
        this.itemList = itemList;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        convertView = inflater.inflate(com.example.contactsdemo.R.layout.contact_list_adapter
                , parent, false);
        TextView tvName=convertView.findViewById(com.example.contactsdemo.R.id.contactName);
        TextView tvCap=convertView.findViewById(com.example.contactsdemo.R.id.contactCap);
        tvName.setText(itemList.get(position).getName());
        tvCap.setText(toolUtils.getCap(itemList.get(position).getName()));
        if (position!=0){
        if (toolUtils.getCap(itemList.get(position).getName()).equals(toolUtils.getCap(itemList.get(position - 1).getName()))){
            tvCap.setHeight(0);
        }
        }

        return convertView;

    }
}
