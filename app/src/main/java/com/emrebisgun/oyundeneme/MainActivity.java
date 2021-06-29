package com.emrebisgun.oyundeneme;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.ListPreference;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import javax.net.ssl.SNIHostName;

public class MainActivity extends AppCompatActivity {

    TextView timeText;
    TextView scoreText;
    TextView kadi;
    int skor;
    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView imageView10;
    ImageView imageView11;
    ImageView imageView12;
    ImageView[] imageArray;
    Handler handler; //runble kullanabilmek için gerekli sınıf.
    Runnable runnable;
    SharedPreferences sharedPreferences;
    String seviyeAl; //Giris aktivitesinde intent ile seviye değeri göndermiştik buradada onu karşılayıp seviyeAl değişkeninde tutacağım.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeText=findViewById(R.id.timeText);
        scoreText=findViewById(R.id.scoreText);
        kadi=findViewById(R.id.kadi);

        //Intent nesnesi olusturup bu nesne ile giris aktivitesinden gönderilen kullanıcı adını karsıladım ve string değişkenime atadım.
        Intent intent=getIntent();
        String kullaniciAdi=intent.getStringExtra("adi");
        kadi.setText(kullaniciAdi);  //String değerine atamış olduğum bu değeri yani ismi kadi ismindeki textview'ımda gösterdim.
        seviyeAl=intent.getStringExtra("level");



        imageView=findViewById(R.id.imageView);
        imageView2=findViewById(R.id.imageView2);
        imageView3=findViewById(R.id.imageView3);
        imageView4=findViewById(R.id.imageView4);
        imageView5=findViewById(R.id.imageView5);
        imageView6=findViewById(R.id.imageView6);
        imageView7=findViewById(R.id.imageView7);
        imageView8=findViewById(R.id.imageView8);
        imageView9=findViewById(R.id.imageView9);
        imageView10=findViewById(R.id.imageView10);
        imageView11=findViewById(R.id.imageView11);
        imageView12=findViewById(R.id.imageView12);

        //Görsellerin adeti kadar bir dizi oluşturdum bu dizinin içine bu gorsellerimi ekledim.
        imageArray=new ImageView[]{imageView,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9,imageView10,imageView11,imageView12};

        //Giris aktivitesinde butona göre(zor,orta,kolay), Intent seviye değeri göndermişti bu değeri kontrol edip sonuca göre zorluk derecesini -
        //belirten metodumu çağırdım.
        if(seviyeAl.equals("zor")){
            hideImages();
        }else if(seviyeAl.equals("orta")){
            hideImages1();
        }else{
            hideImages2();
        }




        //Zamanlayımıcı oluşturdum.Kaçtan başlayıp kaç aralıkla sayacağını belirttim.
        new CountDownTimer(15000, 1000) {
            @Override //her bir saniyede ne yapayım
            public void onTick(long millisUntilFinished) {
                timeText.setText("Time: "+millisUntilFinished/1000); //belirttiğim değere göre sayma islemini an ve an timeText de görüntüleyecek.
            }

            @Override //Sure Bitince ne olacak.
            public void onFinish() {
                timeText.setText("Time off");
                handler.removeCallbacks(runnable);
                for(ImageView image: imageArray){ //Oyun bitince tekrardan bütün gorselleri gizledim.
                    image.setVisibility(View.INVISIBLE);
                }
                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Oyun");
                alert.setMessage("Tekrar oynamak istiyor musunuz ?");
                alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Oyundan Çıkılıyor", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),Giris.class);
                        kaydet();
                        startActivity(intent);
                    }
                });
                alert.show();

            }
        }.start();

        skor=0;
    }

    //Yuvarlak şeklimiz 12 adet bulunmakta bu 12 şeklede aynı onclick verdim cunku hepsı için aynı işlem yapılacaktır.
    public void dokundu(View view){
        skor=skor+10;
        scoreText.setText("Skor: "+skor);
    }

    //Veri tabanına kaydeetme işlemi yapacak.
    public void kaydet(){
        try{
            SQLiteDatabase database=this.openOrCreateDatabase("Skor",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS oyuncular(id INTEGER PRIMARY KEY,oyuncu_adi VARCHAR,skor INT)");
            String sorgu="INSERT INTO oyuncular(oyuncu_adi,skor) VALUES(?,?)";
            SQLiteStatement sqLiteStatement=database.compileStatement(sorgu);
            sqLiteStatement.bindString(1,kadi.getText().toString());
            sqLiteStatement.bindString(2,Integer.toString(skor));
            sqLiteStatement.execute();





        }catch (Exception e){
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Veritabanından bir sorgu oluşturup dönen değerler arasında imlec ile gezeeceğim.
            /*Cursor cursor=database.rawQuery("Select * from oyuncular",null);

            int oyuncuIndex=cursor.getColumnIndex("oyuncu_adi");
            int skorIndex=cursor.getColumnIndex("skor");

            while(cursor.moveToNext()){
                System.out.println("Oyuncu adı: "+cursor.getString(oyuncuIndex));
                System.out.println("Skoru: "+cursor.getString(skorIndex));

            }
            cursor.close();*/

    //Burada zorluk derecesine göre resimleri gizleme ve saniye hızlarını belirttiğim bir metod oluşturdum.
    public void hideImages(){
        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                for(ImageView image: imageArray){ //Döngü ile dizim içindeki gorselleri tek tek gizliyorum.
                    image.setVisibility(View.INVISIBLE);
                }
                Random random=new Random();
                int i=random.nextInt(11);
                imageArray[i].setVisibility(View.VISIBLE); //Rastgele atanan değeri aldım ve bu değere karşılık gelen image dizisinin indeksindeki gorseli gösterdim.
                //this ile bulunduğumuz yeri yani runnable'ı çalıştıracağımı belirttim.Saniye başı yap
                handler.postDelayed(this,350);


            }
        };

        handler.post(runnable); //Runnable'ımı başlattım.

    }
    public void hideImages1(){
        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                for(ImageView image: imageArray){ //Döngü ile dizim içindeki gorselleri tek tek gizliyorum.
                    image.setVisibility(View.INVISIBLE);
                }
                Random random=new Random();
                int i=random.nextInt(11);
                imageArray[i].setVisibility(View.VISIBLE); //Rastgele atanan değeri aldım ve bu değere karşılık gelen image dizisinin indeksindeki gorseli gösterdim.
                //this ile bulunduğumuz yeri yani runnable'ı çalıştıracağımı belirttim.Saniye başı yap
                handler.postDelayed(this,700);


            }
        };

        handler.post(runnable); //Runnable'ımı başlattım.
    }
    public void hideImages2(){
        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                for(ImageView image: imageArray){ //Döngü ile dizim içindeki gorselleri tek tek gizliyorum.
                    image.setVisibility(View.INVISIBLE);
                }
                Random random=new Random();
                int i=random.nextInt(11);
                imageArray[i].setVisibility(View.VISIBLE); //Rastgele atanan değeri aldım ve bu değere karşılık gelen image dizisinin indeksindeki gorseli gösterdim.
                //this ile bulunduğumuz yeri yani runnable'ı çalıştıracağımı belirttim.Saniye başı yap
                handler.postDelayed(this,1000);


            }
        };

        handler.post(runnable); //Runnable'ımı başlattım.
    }


}