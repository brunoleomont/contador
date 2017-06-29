package com.monteiro.leonardo.bruno.contador;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by bruno on 25/10/2016.
 */

public class EnviarActivity extends Activity{

    private Spinner spn1;
    private List<String> lugares = new ArrayList<String>();
    private String lugar;

    private Spinner spn2;
    private List<String> eventos = new ArrayList<String>();
    private String evento;

    public TextView edtTxt_contagem;
    public TextView txt_local;
    public TextView txt_evento;
    public TextView txt_data;

    String local;
    String events;
    public String email;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar);

        txt_data = (TextView) findViewById(R.id.edTxtData);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        String valor = intent.getStringExtra("valor");

        Calendar calendario = Calendar.getInstance();
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH)+1;
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        txt_data.setText(dia + "/" + mes + "/" + ano);

        spn1 = (Spinner) findViewById(R.id.spinner_local);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listarLugares()){
            @Override
            public boolean isEnabled(int position){
                if (position == 0){
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView =(TextView) view;
                if (position == 0){
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spn1.setAdapter(spinnerArrayAdapter);

        //Método do Spinner para capturar o item selecionado
        spn1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                if (posicao != 0) {
                    local = parent.getItemAtPosition(posicao).toString();
                    //imprime um Toast na tela com o nome que foi selecionado
                    //Toast.makeText(ExemploSpinner.this, "Nome Selecionado: " + nome, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn2 = (Spinner) findViewById(R.id.spinner_evento);
        ArrayAdapter<String> arrayEvento = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listarEventos()){
            @Override
            public boolean isEnabled(int position){
                if (position == 0){
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView =(TextView) view;
                if (position == 0){
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        ArrayAdapter<String> spinnerEvento = arrayEvento;
        spinnerEvento.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spn2.setAdapter(spinnerEvento);

        spn2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                String selectedItemText = (String) parent.getItemAtPosition(posicao);
                if (posicao > 0) {
                    events = selectedItemText;
                    //imprime um Toast na tela com o nome que foi selecionado
                    //Toast.makeText(ExemploSpinner.this, "Nome Selecionado: " + nome, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edtTxt_contagem = (TextView) findViewById(R.id.edtTxt_contagem);
        edtTxt_contagem.setText(valor);

        Button btnEnviar = (Button) findViewById(R.id.btn_enviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarConexao() && verificarDados()) {
                    enviarDados();
                    enviarDadosParse();
                }
            }
        });
    }

    private List<String> listarLugares() {
        if (verificarConexao()) {
            ParseQuery query = ParseQuery.getQuery("Local");
            //query.whereEqualTo("eventoNome", "Dan Stemkoski");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> localList, ParseException e) {
                    if (e == null) {
                        Log.d("Locais", "Recebidos " + localList.size() + " locais");
                        for (ParseObject eventoObj : localList) {
                            lugar = eventoObj.getString("nome");
                            lugares.add("" + lugar);
                        }
                    } else {
                        Log.d("Local", "Erro: " + e.getMessage());
                    }
                }
            });
            lugares.add("Local");
            return lugares;
        } else {
            lugares.add("Local");
            lugares.add("São José dos Campos");
            lugares.add("Jacareí");
            lugares.add("Taubaté");
            return lugares;
        }
    }

    private List<String> listarEventos() {
        if (verificarConexao()) {
            ParseQuery query = ParseQuery.getQuery("Evento");
            //query.whereEqualTo("eventoNome", "Dan Stemkoski");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> eventList, ParseException e) {
                    if (e == null) {
                        Log.d("evento", "Recebidos " + eventList.size() + " eventos");
                        for (ParseObject eventoObj : eventList) {
                            evento = eventoObj.getString("eventoNome");
                            eventos.add("" + evento);
                        }
                    } else {
                        Log.d("evento", "Erro: " + e.getMessage());
                    }
                }
            });
            eventos.add("Evento");
            return eventos;
        } else {
            eventos.add("Evento");
            eventos.add("Celebração Domingo");
            eventos.add("Eleve Livre");
            eventos.add("Eleve Xtreme");
            eventos.add("Virada Dia 01");
            return eventos;
        }
    }

    public void enviarDados(){
            Intent confirmacaoActivity = new Intent(this, ConfirmacaoActivity.class);
            confirmacaoActivity.putExtra("valor", edtTxt_contagem.getText().toString());
            confirmacaoActivity.putExtra("data", txt_data.getText().toString());
            confirmacaoActivity.putExtra("evento", events);
            confirmacaoActivity.putExtra("local", local);
            startActivity(confirmacaoActivity);
    }

    public void enviarDadosParse(){
        ParseObject contagem = new ParseObject("Contagem");
        contagem.put("publico", edtTxt_contagem.getText().toString());
        contagem.put("evento", events);
        contagem.put("local", local);
        contagem.put("data", txt_data.getText().toString());
        contagem.saveInBackground();
    }

    public boolean verificarConexao(){
        boolean conectado;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isAvailable() && connectivityManager.getActiveNetworkInfo().isConnected()){
            conectado = true;
        } else {
            conectado = false;
            Toast.makeText(this, "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
        }
        return conectado;
    }

    public boolean verificarDados(){
        String e = events;
        if (events == "Evento" || events == null) {
            Toast.makeText(this, "Selecione um Evento", Toast.LENGTH_SHORT).show();
            return false;
        } else if (local == "Local" || local == null){
            Toast.makeText(this, "Selecione um Local", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
