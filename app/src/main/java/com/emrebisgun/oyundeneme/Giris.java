package com.emrebisgun.oyundeneme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Giris extends AppCompatActivity {

    EditText editTextKullanici;
    String kulAdi,seviye;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        editTextKullanici = findViewById(R.id.editTextKullanici);
        sharedPreferences = this.getSharedPreferences("com.emrebisgun.oyundeneme", Context.MODE_PRIVATE);

        //SharedPreferences ile tutulan bir kullanıcı adı varsa onu string değişkeninde tutup editTextde görüntüledim.
        String sharedKadi;
        sharedKadi = sharedPreferences.getString("sharedKadi", "yok");


        if (sharedKadi == "yok") {
            editTextKullanici.setText("Yok");
        } else {
            editTextKullanici.setText(sharedKadi);
        }
    }

    //Hangi menuyu kullanacağımı belirteceğim method.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();// Menu yayınlayıcımı oluşturdum.
        menuInflater.inflate(R.menu.menu,menu); //R dosyasından yolu belirterek hangi menuyu dahil edeceğimi belirttim.

        return super.onCreateOptionsMenu(menu);
    }

    //Eklediğim menu elemanlarında seçim yapıldığında yapılacak işlemleri belirteceğim metod.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent=new Intent(Giris.this,SkorTablosu.class); //Intent nesnesi oluşturdum if bloğu icerisine girerse MaintActivity'nin classına gidecek.
        if(item.getItemId()==R.id.tablo){ //menu elemanlarından id'si tablo olan eleman seçildiyse if blok'u içerisine girecektir.
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    public void zor(View view) {
        seviye="zor"; //zor metoduna girdiğinde değikenime atadım ve bunu intent ile diğer sayfaya göndereceğim orada if ile kontrol edip oyun seviyesini ayarlayacağım.
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        kulAdi = editTextKullanici.getText().toString();
        intent.putExtra("adi", kulAdi); //kullanıcı adını "adi" değerinde tutup intent ile diğer aktiviteye gönderdim.
        intent.putExtra("level",seviye); //level değerini "level" değerinde tutup intent ile diğer aktiviteye gönderdim.


        //Eğer editTextKullanici da değer boş değil mi diye sordum. Değer girildiyse if blok'una girecek.
        if (!editTextKullanici.getText().toString().matches("")) {

            //ve burada sharedpreferences ile string turunde kullanici adını tutacak.
            sharedPreferences.edit().putString("sharedKadi", kulAdi).apply();
            startActivity(intent);
        }

    }
    public void orta(View view){
        seviye="orta";
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        kulAdi = editTextKullanici.getText().toString();
        intent.putExtra("adi", kulAdi);
        intent.putExtra("level",seviye);
        if (!editTextKullanici.getText().toString().matches("")) {
            sharedPreferences.edit().putString("sharedKadi", kulAdi).apply();
            startActivity(intent);
        }
    }
    public void kolay(View view){
        seviye="kolay";
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        kulAdi = editTextKullanici.getText().toString();
        intent.putExtra("adi", kulAdi);
        intent.putExtra("level",seviye);
        if (!editTextKullanici.getText().toString().matches("")) {
            sharedPreferences.edit().putString("sharedKadi", kulAdi).apply();
            startActivity(intent);
        }
    }
}