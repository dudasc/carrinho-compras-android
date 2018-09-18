package com.esc.compras;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.esc.compras.R;
import com.esc.compras.dao.ItemDAO;
import com.esc.compras.model.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public Button btSalvarItem;
    public EditText textItem;
    public EditText textSearch;
    public ListView listItems;

    private List<Item> items;
    private Handler handlerExcluir;
    private ItemDAO itemDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btSalvarItem = (Button)findViewById(R.id.btSalvarItem);
        btSalvarItem.setEnabled(false);

        textItem = (EditText)findViewById(R.id.textItem);
        textSearch = (EditText)findViewById(R.id.textSearch);
        listItems = (ListView)findViewById(R.id.listItems);

        items = new ArrayList<>();

        handlerExcluir = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                MainActivity.this.excluirItem(inputMessage.getData().getInt("position"), (Item)inputMessage.getData().getSerializable("item"));
            }
        };

        textItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btSalvarItem.setEnabled(!(""+s).isEmpty());
            }
        });

        textSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.this.buscarItem(String.valueOf(s));
            }
        });

        itemDAO = new ItemDAO(this);

        items = itemDAO.findAll();
        refreshListView();
    }

    public void buscarItem(String q) {
        items = q.isEmpty() ? itemDAO.findAll() : itemDAO.findByName(q);
        refreshListView();
    }

    public void salvarItem(View v) {
        String name = textItem.getText().toString();
        textItem.setText("");

        Item n = new Item(name);
        itemDAO.saveItem(n);

        items = itemDAO.findAll();
        refreshListView();
    }

    public void excluirItem(int position, Item item) {
        itemDAO.deleteItem(item);

        items.remove(position);
        refreshListView();
    }

    private void refreshListView() {
        ItemRowAdapter adapter = new ItemRowAdapter(this, items, handlerExcluir);
        listItems.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        itemDAO.close();
        itemDAO = null;
    }

}
