package com.example.calcdmg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class CardActivity extends AppCompatActivity {

    private TextView txtType,txtPs,txtAtk,txtDef,txtSpd,txtSkill;
    private     Random r = new Random();
    String [] arrayTypes ={"Piedra","Papel","Tijera"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        txtType= findViewById(R.id.txvType);
        txtPs= findViewById(R.id.txvPS);
        txtAtk= findViewById(R.id.txvAtk);
        txtDef= findViewById(R.id.txvDef);
        txtSpd= findViewById(R.id.txvSpd);
        txtSkill=findViewById(R.id.txvSkill);
        startRamdom();
    }

    private void startRamdom()
    {
        stats basicStat = getStats();
        int rand1 = r.nextInt(4 - 1) + 1;

        txtType.setText("Tipo:"+arrayTypes[--rand1]);
        txtPs.setText("PS:"+basicStat.getPS());
        txtAtk.setText("ATK:"+basicStat.getATK());
        txtDef.setText("DEF:"+basicStat.getDEF());
        txtSpd.setText("SPD:"+basicStat.getSPD());
        txtSkill.setText(getSkill());
    }
    public void setRamdomValues(View v)
    {
        startRamdom();
    }

    public void backHome(View v)
    {
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public stats getStats()
    {
        stats obj= new stats();

        String[][] values={
                            {"9","9","7","7"},
                            {"10","6","9","7"},
                            {"11","6","6","9"},
                            {"12","8","8","4"},

                            {"9","9","7","7"},
                            {"6","10","9","7"},
                            {"6","11","6","9"},
                            {"8","12","4","8"},

                            {"7","7","9","9"},
                            {"7","9","10","6"},
                            {"9","6","11","6"},
                            {"8","4","12","8"},

                            {"7","7","9","9"},
                            {"7","9","6","10"},
                            {"9","6","6","11"},
                            {"4","8","8","12"},
        };
        int val = r.nextInt( 16- 1) + 1;

        obj.setPS(values[val][0]);
        obj.setATK(values[val][1]);
        obj.setDEF(values[val][2]);
        obj.setSPD(values[val][3]);

        return obj;
    }

    private String getSkill()
    {
        String [] arraySkills ={"Siempre evita el primer ataque",
                                "Si es eliminado puede quedar con PS=1 solo una vez",
                                "Puede atacar por segunda si esta en desventaja numérica",
                                "Devuelve los efectos de habilidades",
                                "Evita que le bajen sus características",
                                "Si es afectado por la habilidad de un rival sube +2 el Ataque",
                                "Invoca clima por tres turnos que sube velocidad + 1 a todos los tipo Piedra",
                                "Invoca clima por tres turnos que sube velocidad + 1 a todos los tipo Papel",
                                "Invoca clima por tres turnos que sube velocidad + 1 a todos los tipo Tijera",
                                "Restaura 1/4 de los PS maximos si recibe daño tipo Piedra",
                                "Restaura 1/4 de los PS maximos si recibe daño tipo Papel",
                                "Restaura 1/4 de los PS maximos si recibe daño tipo Tijera",
                                "Atrae ataques de tipo Piedra",
                                "Atrae ataques de tipo Papel",
                                "Atrae ataques de tipo Tijera",
                                "Resta velocidad -1 al rival que lo ataca",
                                "Resta Ataque -1 al rival que lo ataca",
                                "Resta Defensa -1 al rival que lo ataca",
                                "Inmune frente a los ataques de tipo Piedra" ,
                                "Inmune frente a los ataques de tipo Papel" ,
                                "Inmune frente a los ataques de tipo Tijera " ,
                                "Sube la velocidad +1 al hacer un daño Supereficaz",
                                "Sube el Ataque +1 al recibir daño de tipo Piedra.",
                                "Sube el Ataque +1 al recibir daño de tipo Papel.",
                                "Sube el Ataque +1 al recibir daño de tipo Tijera.",
                                "Sube la Defensa +1 al hacer daño a tipo Piedra",
                                "Sube la Defensa +1 al hacer daño a tipo Papel",
                                "Sube la Defensa +1 al hacer daño a tipo Tijera",
                                "Al ingresar al juego Baja la Defensa -1 a todos oponentes tipo Piedra.",
                                "Al ingresar al juego Baja la Defensa -1 a todos oponentes tipo Papel.",
                                "Al ingresar al juego Baja la Defensa -1 a todos oponentes tipo Tijera.",
                                "Cambia al tipo del último ataque recibido"};

        int rand2= r.nextInt((arraySkills.length)- 1) + 1;
        return arraySkills[rand2];
    }
}
