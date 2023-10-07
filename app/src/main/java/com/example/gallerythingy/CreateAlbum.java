package com.example.gallerythingy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class CreateAlbum extends AppCompatActivity {
    Button ThemButton,SuaButton;
    ArrayList<String> arrayList;
    ArrayList<String> theList;
    ListView lstView;
    EditText editText;
    DatabaseHelper myDB;
    int vitri =-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_album);
        DatabaseHelper databaseHelper=new DatabaseHelper(this);
        ThemButton=findViewById(R.id.themButton);
        SuaButton=findViewById(R.id.suaButton);

        editText=findViewById(R.id.editTextAlbumName);
        arrayList=new ArrayList<>();
        myDB=databaseHelper;
        Cursor data=myDB.getListContents();

        ThemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                String tenalbum=editText.getText().toString();
                arrayList.add(tenalbum);
                adapter.notifyDataSetChanged();
                 */
                String newEntry = editText.getText().toString();
                if(editText.length()!=0){
                    AddData(newEntry);
                    editText.setText("");
                }else{
                    Toast.makeText(CreateAlbum.this, "Bạn phải nhập gì đó", Toast.LENGTH_SHORT).show();
                }
            }
        });

        SuaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(CreateAlbum.this,ViewListContents.class);
               startActivity(intent);
            }
        });
    }
    private void AddData(String newEntry) {
        boolean insertData=myDB.addData(newEntry);
        if(insertData==true){
            Toast.makeText(CreateAlbum.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(CreateAlbum.this, "Có gì đó sai", Toast.LENGTH_SHORT).show();
        }
    }
}