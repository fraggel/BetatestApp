package es.fraggel.betatestapp.betatest;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * Created by u028952 on 10/07/2014.
 */
public class image_view extends Activity implements View.OnClickListener {
    ArrayList listaImagenes=null;
    int imgS=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_image_view);
        listaImagenes=getIntent().getStringArrayListExtra("listaImagenes");
        LinearLayout layout = (LinearLayout)findViewById(R.id.layoutView);
        TextView tv=new TextView(getApplicationContext());
        tv.setText("Pulsa la imagen para borrarla");
        layout.addView(tv);
        if(listaImagenes!=null && listaImagenes.size()>0) {
            for (int i = 0; i < listaImagenes.size(); i++) {
                try {
                    ImageView image = new ImageView(this);
                    image.setOnClickListener(this);
                    image.setImageURI(Uri.parse((String) listaImagenes.get(i)));
                    image.setId(i);
                    layout.addView(image);
                    image.getLayoutParams().width = 400;
                    image.getLayoutParams().height = 400;
                    image.setAdjustViewBounds(true);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        Intent returnIntent = getIntent();
        returnIntent.putExtra("result",listaImagenes);
        setResult(RESULT_OK,returnIntent);
    }


    @Override
    public void onClick(View view) {
        if (listaImagenes.size() > 0) {
            imgS=view.getId();
               /* ImageView image = new ImageView(this);
                image.setImageURI(Uri.parse((String) listaImagenes.get(view.getId())));*/
            new AlertDialog.Builder(this)
                    .setTitle("Borrar imagen")
                    .setMessage("¿Borrar imagen seleccionada?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            listaImagenes.remove(imgS);
                            finish();
                            startActivity(getIntent());
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

            }
        }
}
