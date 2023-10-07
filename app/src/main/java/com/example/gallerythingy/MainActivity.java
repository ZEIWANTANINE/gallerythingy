package com.example.gallerythingy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecycleAdapter.itemClickListener{

    RecyclerView recyclerView;
    TextView textView;
    Button chon ,display;


    ArrayList<Uri> uri = new ArrayList<>();
    RecycleAdapter adapter;

    private static final int Read_Permission = 101;
    private static final int PICK_IMAGE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.soanh);
        recyclerView = findViewById(R.id.rcvanh);
        chon = findViewById(R.id.btnchon);
        display=findViewById(R.id.btndisplay);

        adapter = new RecycleAdapter(uri, this);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
        recyclerView.setAdapter(adapter);
        chon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Read_Permission);
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR2){
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                }
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE);
            }
        });
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ImageViewSlider.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void itemclick(int position) {

        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.custom_dialog_zoom);

        TextView textView1 = dialog.findViewById(R.id.txt_name);
        ImageView imageView = dialog.findViewById(R.id.imageView);
        Button btn_close = dialog.findViewById(R.id.btn_close);
        Button btn_share = dialog.findViewById(R.id.btn_share);

        textView1.setText("Ảnh "+ position);
        imageView.setImageURI(uri.get(position));
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("image/jpeg");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, uri.get(position));
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK&& null !=data){
            if(data.getClipData() != null){
                int countOfImages = data.getClipData().getItemCount();
                for(int i = 0 ; i<countOfImages; ++i){
                    Uri imageuri=data.getClipData().getItemAt(i).getUri();
                    uri.add(imageuri);
                }
                adapter.notifyDataSetChanged();
                textView.setText("Photo ("+uri.size()+")");
            }else {
                Uri imageuri=data.getData();
                uri.add(imageuri);
            }
            adapter.notifyDataSetChanged();
            textView.setText("Photo ("+uri.size()+")");
        }else{
            Toast.makeText(this, "Bạn chưa chọn ảnh nào", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void clicked(int getSize) {
        textView.setText("Photo ("+uri.size()+")");
    }
}