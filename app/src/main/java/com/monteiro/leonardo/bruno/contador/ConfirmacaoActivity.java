package com.monteiro.leonardo.bruno.contador;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by bruno on 17/11/2016.
 */

public class ConfirmacaoActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao);

        Intent intent = getIntent();
        String valor = intent.getStringExtra("valor");
        String data = intent.getStringExtra("data");
        String evento = intent.getStringExtra("evento");
        String local = intent.getStringExtra("local");

        TextView txtView_contagem = (TextView) findViewById(R.id.txtView_contagem);
        TextView txtView_data = (TextView) findViewById(R.id.txt_data_conf);
        TextView txtView_evento = (TextView) findViewById(R.id.txt_evento_conf);
        TextView txtView_local = (TextView) findViewById(R.id.txt_local_conf);
        Button btn_contar_novamente = (Button) findViewById(R.id.btn_contar_novamente);

        txtView_contagem.setText(valor);
        txtView_data.setText(data);
        txtView_evento.setText(evento);
        txtView_local.setText(local);

        btn_contar_novamente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recomecar();
            }
        });
    }

    public void recomecar(){
        Intent contagemActivity = new Intent(this, ContagemActivity.class);
        startActivity(contagemActivity);
    }
}