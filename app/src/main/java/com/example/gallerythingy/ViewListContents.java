package com.example.gallerythingy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewListContents extends AppCompatActivity {

    DatabaseHelper myDB;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);
        ListView listView=findViewById(R.id.listView);
        myDB=new DatabaseHelper(this);
        ArrayList<String> theList =new ArrayList<>();
        Cursor data= myDB.getListContents();
        if (data.getCount()==0){
            Toast.makeText(ViewListContents.this, "Database rỗng", Toast.LENGTH_SHORT).show();
        }else{
            while (data.moveToNext()){
                theList.add(data.getString(1));
                ListAdapter listAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(listAdapter);
            }
            ListAdapter listAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,theList);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedName = parent.getItemAtPosition(position).toString();
                    // Chuyển sang activity khác
                    Intent intent = new Intent(ViewListContents.this, MainActivity.class);
                    intent.putExtra("name", selectedName);
                    startActivity(intent);
                }
            });
        }
    }

}

