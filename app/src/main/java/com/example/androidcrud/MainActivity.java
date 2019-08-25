package com.example.androidcrud;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseSiswa mydb;

    EditText ed_name, ed_age, ed_id;
    Button addData, view_data, updateData, deleteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DatabaseSiswa(this);

        ed_name    = findViewById(R.id.i_name);
        ed_age     = findViewById(R.id.i_age);
        ed_id      = findViewById(R.id.i_id);
        addData    = findViewById(R.id.btn_add);
        view_data  = findViewById(R.id.data_view);
        updateData = findViewById(R.id.btn_update);
        deleteData = findViewById(R.id.btn_delete);

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean createData = mydb.insertData(ed_name.getText().toString(),ed_age.getText().toString());
                if (createData == true){
                    Toast.makeText(MainActivity.this, "1 Data Inserted", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(MainActivity.this, "Failed to Insert Data ", Toast.LENGTH_LONG).show();
                }

            }
        });

        view_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getAllData();
                if(res.getCount() == 0){
                    showMessage("ERROR", "No Data Found!");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("ID : "+res.getString(0)+"\n" );
                    buffer.append("Name : "+res.getString(1)+"\n" );
                    buffer.append("Age : "+res.getString(2)+"\n \n" );
                }

                showMessage("Data", buffer.toString());
            }
        });

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean dataUpdated = mydb.updateData(ed_id.getText().toString(), ed_name.getText().toString(), ed_age.getText().toString());

                if (dataUpdated == true){
                    Toast.makeText(MainActivity.this, "1 Data Updated", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Failed to Update", Toast.LENGTH_LONG).show();
                }

            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer dataDelete = mydb.deleteData(ed_id.getText().toString());
                if (dataDelete > 0){
                    Toast.makeText(MainActivity.this, "1 Data Deleted", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Failed to Delete", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void showMessage(String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();


    }



}
