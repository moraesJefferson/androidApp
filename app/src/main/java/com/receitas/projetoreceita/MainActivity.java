package com.receitas.projetoreceita;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ImageView add;
    private ListView lista;
    private String[] listaServico = {"Mecânico","Encanador","Jardineiro","Pedreiro","Faxineira","Técnico de informática","Manicure","Motorista","Entregador","Cozinheiro","Segurança","Reboque"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.addId);
        lista = findViewById(R.id.listaId);
        //Bundle extra = new Bundle();
        //String servicos = extra.getString("servicos");
        //listaServico += servicos.;
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,android.R.id.text1,listaServico);
        lista.setAdapter(adaptador);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,adicionarReceita.class));

            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"Informações de contato",Toast.LENGTH_LONG).show();
            }
        });


    }
}
