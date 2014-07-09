package es.fraggel.betatestapp.betatest;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    ArrayList listaImagenes=null;
    private static final int SELECT_PHOTO = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSend = (Button) findViewById(R.id.btnSend);
        Button btn1 = (Button) findViewById(R.id.button1);
        Button btn2 = (Button) findViewById(R.id.button2);
        Button btn3 = (Button) findViewById(R.id.button3);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //obtenemos los datos para el envío del correo
                String etEmail = "jiayuteamforce6@gmail.com";
                String etSubject = Build.DISPLAY;
                EditText etBody = (EditText) findViewById(R.id.etBody);

                //es necesario un intent que levante la actividad deseada
                Intent itSend = new Intent(android.content.Intent.ACTION_SEND);

                //vamos a enviar texto plano a menos que el checkbox esté marcado
                itSend.setType("plain/text");

                //colocamos los datos para el envío
                itSend.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ etEmail.toString()});
                itSend.putExtra(android.content.Intent.EXTRA_SUBJECT, etSubject.toString());
                itSend.putExtra(android.content.Intent.EXTRA_TEXT, etBody.getText());

                //revisamos si el checkbox está marcado enviamos el ícono de la aplicación como adjunto

                    //colocamos el adjunto en el stream
                if(listaImagenes!=null && listaImagenes.size()>0){
                    String tmp="";
                    for (int x=0;x<listaImagenes.size();x++){
                        itSend.putExtra(Intent.EXTRA_STREAM, Uri.parse((String)listaImagenes.get(x)));
                        itSend.setType("image/png");
                    }

                }


                    //indicamos el tipo de dato



                //iniciamos la actividad
                startActivity(itSend);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);


            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listaImagenes!=null && listaImagenes.size()>0){
                    String tmp="";
                    for (int x=0;x<listaImagenes.size();x++){
                        tmp=tmp+"\n"+listaImagenes.get(x);
                    }
                    Toast.makeText(getApplicationContext(),tmp,Toast.LENGTH_LONG).show();
                }

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaImagenes=null;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    if(listaImagenes!=null && listaImagenes.size()>0){
                        listaImagenes.add(selectedImage.getPath());
                    }else{
                        listaImagenes=new ArrayList();
                        listaImagenes.add(selectedImage.getPath());
                    }

                }
        }
    }
}
