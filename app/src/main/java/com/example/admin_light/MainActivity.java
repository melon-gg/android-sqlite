package com.example.admin_light;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.regex.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText redEdit;
    private EditText yellowEdit;
    private EditText greenEdit;
    private SQLiteDatabase sqLiteDatabase;
    private AdminLightDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton closeBtn = (ImageButton)findViewById(R.id.closeBtn);
        Button confirmBtn = (Button)findViewById(R.id.confirmBtn);
        Button cancelBtn = (Button)findViewById(R.id.cancelBtn);
        redEdit = (EditText)findViewById(R.id.redEdit);
        yellowEdit = (EditText)findViewById(R.id.yellowEdit);
        greenEdit = (EditText)findViewById(R.id.greenEdit);

        closeBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        db = new AdminLightDatabase(this.getBaseContext());
        sqLiteDatabase = db.getReadableDatabase();
        initData();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.closeBtn : finish();
            break;
            case R.id.confirmBtn :
                String[] args  = new String[3];
                args[0] = redEdit.getText().toString();
                args[1] = yellowEdit.getText().toString();
                args[2] = greenEdit.getText().toString();
                setData(args);
                break;
            case R.id.cancelBtn : finish();
                break;
        }
    }
    public void initData() {
        String sql = "select * from Admin_LightTable";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{});
        int i = 0;
        while(cursor.moveToNext()) {
            String clos_value = cursor.getString(1);

            switch (i){
                case 0 : redEdit.setText(clos_value);
                case 1 : yellowEdit.setText(clos_value);
                case 2 : greenEdit.setText(clos_value);
            }
            i++;
        }
    }
    public void setData(String id[]) {
        for (int i = 0; i < 3; i++) {

            String pattern = "[1-9][0-9]?";
            boolean isMatch = Pattern.matches(pattern, id[i]);
            int value = -1;
            try {
                value = Integer.parseInt(id[i]);
            } catch (Exception e){
                e.printStackTrace();
            }finally {
            }
            if(!isMatch || value <= 0 || value > 100) {
                Toast toast = Toast.makeText(getApplicationContext(), "输入必须为小于100的正整数", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            Toast toast = Toast.makeText(getApplicationContext(), "设置完成", Toast.LENGTH_SHORT);
            toast.show();
        }
        String sql = "update Admin_LightTable set datetime=? where Light_ID=?";
        sqLiteDatabase = db.getWritableDatabase();
        for (int i = 0; i < 3; i++)
        {
            Object[] bindArgs = new Object[2];
            bindArgs[0] = id[i];
            bindArgs[1] = i ;
            boolean isSuccess = false;
            try {
                sqLiteDatabase.execSQL( sql , bindArgs );
                isSuccess = true;
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                Log.v("TAG:","更新数据库状态：" + isSuccess);
            }
        }
    }
}
