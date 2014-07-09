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
                    itSend.putExtra(Intent.EXTRA_STREAM, Uri.parse(""));

                    //indicamos el tipo de dato
                    itSend.setType("image/png");


                //iniciamos la actividad
                startActivity(itSend);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listaImagenes!=null && listaImagenes.size()>0){
                    listaImagenes.add("Imagen");
                }else{
                    listaImagenes=new ArrayList();
                    listaImagenes.add("Imagen");
                }

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listaImagenes!=null && listaImagenes.size()>0){
                    String tmp="";
                    for (int x=0;x<listaImagenes.size();x++){
                        tmp=tmp+"\n"+listaImagenes.get(x+1);
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

}
