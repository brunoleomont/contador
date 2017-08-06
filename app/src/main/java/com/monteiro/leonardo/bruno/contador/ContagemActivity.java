package com.monteiro.leonardo.bruno.contador;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by bruno on 11/10/2016.
 */

public class ContagemActivity extends Activity{

    public TextView txtContagem;
    public int contador;
    public String email;
    public String campus;
    public String idUsuario;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contagem);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        campus = intent.getStringExtra("campus");
        idUsuario = intent.getStringExtra("idUsuario");
        Log.d("campus ", "Campus " + campus);
        Log.d("idUser ", "ID User " + idUsuario);

        txtContagem = (TextView) findViewById(R.id.txtView_contagem);
        Button SalvarButton = (Button) findViewById(R.id.btn_salvar);
        SalvarButton.setOnClickListener(new OnClickListener() {
        public void onClick(View view) {
            attemptLogin();
        }
    });

        final Button contarButton = (Button) findViewById(R.id.btn_contar);
        contarButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                contador = somador(txtContagem);
                txtContagem.setText(""+contador);
            }
        });
    }

    private void attemptLogin() {
        Intent enviarActivity = new Intent(this, EnviarActivity.class);
        enviarActivity.putExtra("valor", txtContagem.getText().toString());
        enviarActivity.putExtra("email", email);
        enviarActivity.putExtra("campus", campus);
        enviarActivity.putExtra("idUsuario", idUsuario);
        startActivity(enviarActivity);
    }

    private int somador(TextView txtContagem){
        String txt;
        int valor;
        txt = txtContagem.getText().toString();
        valor = Integer.parseInt(txt);
        valor++;
        return valor;
    }

    private int subtraidor(TextView txtContagem){
        String txt;
        int valor;
        txt = txtContagem.getText().toString();
        valor = Integer.parseInt(txt);
        if (valor > 0){
            valor--;
        } else {
            valor = 0;
        }
        return valor;
    }

    /*public boolean onKeyUp(int keyCode, KeyEvent event){
        switch (keyCode){
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                contador = subtraidor(txtContagem);
                txtContagem.setText(""+contador);
                Log.d("menos um", "-1");
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                contador = somador(txtContagem);
                txtContagem.setText(""+contador);
                Log.d("mais 1", "+1");
                return true;
            default:
                return false;
        }
    }*/

    public boolean dispatchKeyEvent(KeyEvent event){
        int action = event.getAction();
        int keycode = event.getKeyCode();

        //super.dispatchKeyEvent(event);

        switch (keycode){
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_UP){
                    contador = somador(txtContagem);
                    txtContagem.setText(""+contador);
                    Log.d("mais 1", "+1");
            } return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN){
                    contador = subtraidor(txtContagem);
                    txtContagem.setText(""+contador);
                    Log.d("menos um", "-1");
                } return true;
            default: return false;

        }
    }
}