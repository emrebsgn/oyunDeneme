package com.emrebisgun.oyundeneme;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.security.spec.ECField;
import java.util.ArrayList;

public class SkorTablosu extends AppCompatActivity {

    ListView listView;
    ArrayList<String> isimArray; //Kullanıcı adları tutacağım bir arraylist oluşturdum.
    ArrayList<Integer> skorArray; //skorları tutacağım bir arraylist oluşturdum.
    ArrayAdapter arrayAdapter; //ArrayAdapter sayesinde bu değerleri listview'a adapte edeceğim.
    ArrayAdapter arrayAdapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skor_tablosu);

        listView=findViewById(R.id.listview);
        isimArray=new ArrayList<>();
        skorArray=new ArrayList<>();
        //arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,isimArray);
        arrayAdapter1=new ArrayAdapter(this, android.R.layout.simple_list_item_1,skorArray);
        listView.setAdapter(arrayAdapter1);
        tabloGetir();
    }


    //Burada var olan tablomuzu ekrana getiren veritabanı sorgumu oluşturdum.
    public void tabloGetir(){
        try{
            SQLiteDatabase database=this.openOrCreateDatabase("Skor",MODE_PRIVATE,null);

            //veritabanımda rawQuery ile sorgu oluşturup cursor nesnem ile dönen değerleri tek tek imlec şeklinde gezeceğim.
            Cursor cursor=database.rawQuery("SELECT * FROM oyuncular",null);


            int oyuncuIndex=cursor.getColumnIndex("oyuncu_adi");
            int skorIndex=cursor.getColumnIndex("skor");


            while(cursor.moveToNext()){
                isimArray.add(cursor.getString(oyuncuIndex));
                skorArray.add(cursor.getInt(skorIndex));

            }
            //arrayAdapter.notifyDataSetChanged();
            arrayAdapter1.notifyDataSetChanged();//Değişiklikten sonra array adapter guncellenecek.
            cursor.close();


        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
        
    }
}