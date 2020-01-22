package com.example.calcdmg;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.widget.Toast;

public class PotencyTable extends AppCompatActivity {
    private EditText  pow3, pow2,pow1;
    private TextView  txv_title;
    String filename="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potency_table);
        setObjetViews();
    }

    private void setDataFile(String dataValue) {
        try {

            OutputStreamWriter doc= new OutputStreamWriter(openFileOutput(filename, Activity.MODE_PRIVATE));
            doc.write(dataValue);
            doc.flush();
            doc.close();
        }
        catch (IOException e){}
        Toast.makeText(this,"Valores Guardados",Toast.LENGTH_SHORT).show();

    }

    public void backHome(View v)
    {
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);
    }
    public  void saveValues(View v)
    {
        String supEfec= pow3.getText().toString();
        String normal= pow2.getText().toString();
        String pocoEfec = pow1.getText().toString();

        if(supEfec.length()>0 && normal.length()>0 && pocoEfec.length()>0)
        {
            int x3= Integer.parseInt(supEfec);
            int x2= Integer.parseInt(normal);
            int x1= Integer.parseInt(pocoEfec);

            if(x3>x2)
            {
                if( x2>x1)
                {
                    setDataFile(supEfec+"|"+normal+"|"+pocoEfec);
                }
                else
                    Toast.makeText(this,"Eficaz debe ser mayor que el Poco Eficaz",Toast.LENGTH_SHORT).show();
            }
            else
            Toast.makeText(this,"Super Eficaz debe ser mayor que el Eficaz",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"Debe ingresar todos los Stats",Toast.LENGTH_SHORT).show();
    }

    private void setObjetViews()
    {
        String  dato=getIntent().getStringExtra("dato");
        filename =getIntent().getStringExtra("file");

        pow3= findViewById(R.id.edt_numse);
        pow2= findViewById(R.id.edt_nume);
        pow1= findViewById(R.id.edt_numpe);
        txv_title= findViewById(R.id.txv_Title);
        txv_title.setText(dato);

        try {

            InputStreamReader archivo = new InputStreamReader(openFileInput(filename));
            BufferedReader br = new BufferedReader(archivo);
            String  linea= br.readLine();
            String stringValues="";

            while(linea!=null)
            {
                stringValues=stringValues+linea;
                linea= br.readLine();
            }
            br.close();
            archivo.close();
            String[] arrayValues = stringValues.split("\\|");

            pow3.setText(arrayValues[0]);
            pow2.setText(arrayValues[1]);
            pow1.setText(arrayValues[2]);
        }
        catch (IOException e){}
    }
}
