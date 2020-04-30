package com.example.calcdmg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class WildCardActivity extends AppCompatActivity {

    private TextView txtWildCard;
    private Random r = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wild_card);
        txtWildCard= findViewById(R.id.txv_wildcard);
        startRamdom();
    }
    private void startRamdom()
    {
        txtWildCard.setText( getSkill());
    }
    public void setRamdomValues(View v) {
        txtWildCard.setText( getSkill());
    }

    private String getSkill() {
        String[] arraySkills = {
                "Intercambiar una Tarjeta con un rival",
                "Saca Otra Tarjeta tipo Piedra",
                "Saca Otra Tarjeta tipo Papel",
                "Saca Otra Tarjeta tipo Tijera",
                "Recicla una Tarjeta Eliminada",
                "Cambia una tarjeta de un rival a Tipo Piedra",
                "Cambia una tarjeta de un rival a Tipo Papal",
                "Cambia una tarjeta de un rival a Tipo Tijera",
                "Elimina Clima",
                "Clona con una tarjeta del juego",
                "Transforma todos los stats de un rival a 1",
                "Invierte sentido del jugadores",
                "Intercambio de habilidad con un rival",
                "Intercambio de habilidad con un aliado",
                "Repite la ultima accion de un aliado",
                "Bloquea turno de rival"};

        int rand2 = r.nextInt((arraySkills.length) - 1) + 1;
        return arraySkills[rand2];
    }
    public void backHome(View v)
    {
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);
    }

}
