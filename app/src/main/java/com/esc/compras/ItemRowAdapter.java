package com.esc.compras;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.esc.compras.R;
import com.esc.compras.model.Item;

import java.util.List;

public class ItemRowAdapter extends ArrayAdapter {

    private final Activity context;

    private List<Item> objects;
    private android.os.Handler handlerExcluir = null;

    public ItemRowAdapter(Activity context, List objects, Handler handlerExcluir) {
        super(context, R.layout.list_row, objects);

        this.context = context;
        this.objects = objects;
        this.handlerExcluir = handlerExcluir;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(R.layout.list_row, parent, false);

        final Item item = objects.get(position);

        ((TextView)row.findViewById(R.id.textLabelItem)).setText(item.getName());
        ((Button)row.findViewById(R.id.btExcluir)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (handlerExcluir != null) {
                    Bundle b = new Bundle();
                    b.putInt("position", position);
                    b.putSerializable("item", item);

                    Message m = new Message();
                    m.setData(b);

                    handlerExcluir.sendMessage(m);
                }
            }
        });

        return row;
    }

}
