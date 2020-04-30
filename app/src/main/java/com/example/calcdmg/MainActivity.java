package com.example.calcdmg;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private Spinner spnAtk,spnDef;
    private EditText ptAtk,ptDef,ptHp;
    private TextView txtResultado,txtPow,txtAbsorb,txtBonus;
    private CheckBox check_abs;
    String filename="basePotency.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setObjetViews();
    }

    //Método para mostrar y ocultar menu overflow
    public boolean onCreateOptionsMenu(Menu m)
    {
        getMenuInflater().inflate(R.menu.overflow,m);
        return  true;
    }

    public  boolean onOptionsItemSelected(MenuItem item)
    {
        int itemId=item.getItemId();

        if ( R.id.itm1==itemId) {
            Intent i = new Intent( MainActivity.this, PotencyTable.class);
            i.putExtra("dato", "Valores Efectividad");
            i.putExtra("file", filename);
            startActivity(i);
            return false;
        }
        if( R.id.itm2==itemId)
        {
            Intent i = new Intent( MainActivity.this,CardActivity.class);
            i.putExtra("dato", "AZAR");
            startActivity(i);
            return false;
        }
        if( R.id.itm3==itemId)
        {
            Intent i = new Intent( MainActivity.this,WildCardActivity.class);
            i.putExtra("dato", "AZAR");
            startActivity(i);
            return false;
        }
         return  super.onOptionsItemSelected(item);
}

    private void setObjetViews()
    {
        String [] tipos ={"Piedra","Papel","Tijera"};

        ArrayAdapter<String> adaptador= new ArrayAdapter<String>(
                this, R.layout.spinner_item_type, tipos);

        txtResultado= findViewById(R.id.txv_result);
        txtPow= findViewById(R.id.txv_pow);
        txtAbsorb= findViewById(R.id.txvAbs);
        check_abs= findViewById(R.id.chk_abs);
        ptAtk= findViewById(R.id.txt_atk);
        ptHp= findViewById(R.id.txt_hp);
        ptDef= findViewById(R.id.txt_def);
        txtBonus=findViewById(R.id.txv_Bonus);
        spnAtk= findViewById(R.id.spn_atk);
        spnDef= findViewById(R.id.spn_def);
        spnAtk.setAdapter(adaptador);
        spnDef.setAdapter(adaptador);
        ptAtk.requestFocus();


    }
    private int  calcAbsorcion(int hpMax,int hp)
    {
        int  quota= hpMax/4;
        int  result=Math.round(hp+quota );
        String msg="1/4 de " + hpMax+" es: "+quota;

        if(hp>0)
            txtAbsorb.setText(msg);
        else
            txtAbsorb.setText("");

        return (result > hpMax)?0:quota;
    }

    public void calcular(View v)
    {
        String valAtk= ptAtk.getText().toString();
        String valHp= ptHp.getText().toString();
        String valDef= ptDef.getText().toString();
        String atkType= spnAtk.getSelectedItem().toString();
        String defType= spnDef.getSelectedItem().toString();
        String msgFinal="",msgBonus;
        boolean check=check_abs.isChecked();

        if(valAtk.length()>0 && valHp.length()>0 && valDef.length()>0)
        {
            int atk= Integer.parseInt(valAtk);
            int hp= Integer.parseInt(valHp);
            int def= Integer.parseInt(valDef);
            int potency= powAtack(atkType,defType);
            int result= 0, bonus=0;

            if(atk==0 || def==0 || hp==0)
                Toast.makeText(this,"Ningún Stat debe estar en cero",Toast.LENGTH_SHORT).show();
            else
            {
                result= Math.round(hp-((atk*potency)/def));

                if(check)
                    result+=calcAbsorcion(hp,result);
                else
                    txtAbsorb.setText("");

                if(result==0) {
                    bonus=-result;
                    msgFinal = "¡¡¡Rival debilitado!!!";
                }
                else {
                    bonus=hp-result;
                    msgFinal = "Salud del rival final es: " + result;
                }

                msgBonus=(bonus==0)?"Sin Bono a ataque ":"Bono de Ataque es "+bonus;

                txtResultado.setText(msgFinal);
                txtBonus.setText(msgBonus);
            }
        }
        else
            Toast.makeText(this,"Debe ingresar todos los Stats",Toast.LENGTH_SHORT).show();
    }

    private  int powAtack(String atkType,String defType)
    {
        String[] arrayValues;
        String force="Eficaz";
        int superEficaz=0;
        int eficaz=0;
        int pocoEficaz=0;
        int power=0;

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
            arrayValues = stringValues.split("\\|");

            if(arrayValues.length>0)
            {
                superEficaz=  Integer.parseInt(arrayValues[0]);
                eficaz= Integer.parseInt(arrayValues[1]);
                pocoEficaz= Integer.parseInt(arrayValues[2]);
            }
        }
        catch (IOException e){}

        power=eficaz;

        switch (atkType)
        {
            case "Piedra":
                    if(defType.equals("Papel")) {
                        force = "Poco Eficaz";
                        power = pocoEficaz;
                    }
                    if(defType.equals("Tijera")) {
                        power = superEficaz;
                        force = "Super Eficaz";
                    }
                break;
            case "Papel":
                    if(defType.equals("Tijera")) {
                        force = "Poco Eficaz";
                        power = pocoEficaz;
                    }
                    if(defType.equals("Piedra")) {
                        force = "Super Eficaz";
                        power = superEficaz;
                    }
                break;
            case "Tijera":
                    if(defType.equals("Piedra")) {
                        force = "Poco Eficaz";
                        power = pocoEficaz;
                    }
                    if(defType.equals("Papel")) {
                        force = "Super Eficaz";
                        power = superEficaz;
                    }
                break;
        }
        txtPow.setText("Es "+force +" con un valor de "+power);
        return  power;
    }

    public void clear(View v)
    {
        String txtOriginal= getResources().getString(R.string.txv_form);
        txtResultado.setText(txtOriginal);
        txtBonus.setText("");
        ptAtk.setText("");
        ptHp.setText("");
        ptDef.setText("");
        txtPow.setText("");
        txtAbsorb.setText("");
        spnAtk.setSelection(0);
        spnDef.setSelection(0);
        check_abs.setChecked(false);
    }

    public void invert(View v)
    {
        int ind1= spnAtk.getSelectedItemPosition();
        int ind2= spnDef.getSelectedItemPosition();
        clear(v);
        spnAtk.setSelection(ind2);
        spnDef.setSelection(ind1);
        ptAtk.requestFocus();
    }
}
