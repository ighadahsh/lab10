package com.example.lab10;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class delTsk extends AppCompatActivity {

    private EditText etID;
    private TextView naTxt, phTxt;
    private MyDBHandler dbHandler;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_tsk);

        etID = (EditText) findViewById(R.id.idInp);
        naTxt = (TextView) findViewById(R.id.naTxtView);
        phTxt = (TextView) findViewById(R.id.phTxtView);
        dbHandler = new MyDBHandler(getApplicationContext());
        db = dbHandler.getWritableDatabase();
    }



    public void shwIDInfo(View v){
        String id = etID.getText().toString();

        if ( id.isEmpty()  )
        {
            Toast.makeText(getApplicationContext(), "PLease Fill missing data ", Toast.LENGTH_LONG).show();
            return;
        }

        String sqlStmt = "SELECT * FROM "+ dbHandler.TABLE_NAME
                + " where " + dbHandler.COLUMN_RECID + " = ?";

        Cursor c = db.rawQuery(sqlStmt, new String[] {id});

        if(!c.moveToFirst())
        {
            Toast.makeText(getApplicationContext(), "No ID has matched ", Toast.LENGTH_LONG).show();
            return;
        }

        naTxt.setText(c.getString(1));
        phTxt.setText(c.getString(2));
        c.close();

    }

    public void delTsk(View v){

        String id = etID.getText().toString();

        if ( id.isEmpty()  )
        {
            Toast.makeText(getApplicationContext(), "PLease Fill missing data ", Toast.LENGTH_LONG).show();
            return;
        }


        String nameStr = naTxt.getText().toString();
        String phoneStr = phTxt.getText().toString();

        String sqlStmt = "SELECT * FROM "+ dbHandler.TABLE_NAME
                + " where " + dbHandler.COLUMN_RECID + " = ?";

        Cursor c = db.rawQuery(sqlStmt, new String[] {id});
        if (c.moveToFirst()) {

            db.execSQL("DELETE FROM " + dbHandler.TABLE_NAME +  " where " + dbHandler.COLUMN_RECID + " = "+id+";");


            String tstMsg = "Name " + nameStr + ", Phone " + phoneStr + " is inserted";
            Toast.makeText(getApplicationContext(), tstMsg, Toast.LENGTH_LONG).show();


        } else {

            Toast.makeText(getApplicationContext(), "No recored deleted ", Toast.LENGTH_LONG).show();
        }

        etID.setText("");
        naTxt.setText("");
        phTxt.setText("");
    }

    public void backTo(View v){

        Intent i = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(i);
        dbHandler.close();
        finish();

    }
}