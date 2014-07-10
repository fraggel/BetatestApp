package es.fraggel.betatestapp.betatest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener {
    ArrayList<String> listaImagenes=null;
    Editable msg=null;
    private static final int SELECT_PHOTO = 100;
    SharedPreferences ajustes=null;
    SharedPreferences.Editor editorAjustes=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ajustes=getSharedPreferences("betatestApp", Context.MODE_PRIVATE);
        EditText edT=(EditText)findViewById(R.id.editText);
        edT.setOnClickListener(this);
        edT.setText(ajustes.getString("nombre","Escribe tu nombre"));
        Button btnSend = (Button) findViewById(R.id.btnSend);
        Button btn1 = (Button) findViewById(R.id.button1);
        Button btn2 = (Button) findViewById(R.id.button2);
        Button btn3 = (Button) findViewById(R.id.button3);
        EditText etBody = (EditText) findViewById(R.id.etBody);
        etBody.setText(msg);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //obtenemos los datos para el envío del correo
                EditText etBody = (EditText) findViewById(R.id.etBody);
                msg=etBody.getText();
                String etEmail = "jiayuteamforce6@gmail.com";
                String etSubject = Build.DISPLAY;

                Intent i = new Intent(Intent.ACTION_SEND_MULTIPLE);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{etEmail});
                String subject="";
                if(!"".equals(ajustes.getString("nombre","Escribe tu nombre").toString().trim()) && !"Escribe tu nombre".equals(ajustes.getString("nombre","Escribe tu nombre").toString().trim())){
                    subject=ajustes.getString("nombre","Escribe tu nombre")+": "+etSubject;
                }else{
                    subject=etSubject;
                }

                i.putExtra(Intent.EXTRA_SUBJECT, subject);
                i.putExtra(Intent.EXTRA_TEXT, msg);


                    //colocamos el adjunto en el stream
                if(listaImagenes!=null && listaImagenes.size()>0){
                    ArrayList tmp=new ArrayList();
                    for (int x=0;x<listaImagenes.size();x++){
                        tmp.add(Uri.parse(listaImagenes.get(x)));
                    }
                    i.putParcelableArrayListExtra(Intent.EXTRA_STREAM,tmp);

                }
                try {

                    startActivity(Intent.createChooser(i,"Enviar"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Error al enviar email", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etBody = (EditText) findViewById(R.id.etBody);
                msg=etBody.getText();
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);


            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etBody = (EditText) findViewById(R.id.etBody);
                msg=etBody.getText();
                Intent itt= new Intent(getApplicationContext(),image_view.class);
                itt.putExtra("listaImagenes",listaImagenes);
                startActivityForResult(itt,1);

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
                    //Toast.makeText(getApplicationContext(),imageReturnedIntent.getDataString(),Toast.LENGTH_LONG).show();
                    //Uri selectedImage = imageReturnedIntent.getData();
                    if(listaImagenes!=null && listaImagenes.size()>0){
                        listaImagenes.add(imageReturnedIntent.getDataString());
                    }else{
                        listaImagenes=new ArrayList();
                        listaImagenes.add(imageReturnedIntent.getDataString());
                    }

                }
                break;
            case 1:
                if(resultCode == RESULT_OK) {
                    listaImagenes = (ArrayList) imageReturnedIntent.getSerializableExtra("result");
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        EditText edT=(EditText)findViewById(R.id.editText);
        if(!"".equals(edT.getText().toString().trim()) && !"Escribe tu nombre".equals(edT.getText().toString().trim())){
            editorAjustes=ajustes.edit();
            editorAjustes.putString("nombre",edT.getText().toString().trim());
            editorAjustes.commit();
            EditText etBody = (EditText) findViewById(R.id.etBody);
            etBody.requestFocus();
        }else {
            edT.setText("");
        }
    }
}
