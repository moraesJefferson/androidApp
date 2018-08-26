package com.receitas.projetoreceita;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import com.receitas.projetoreceita.dao.PessoaDao;
import com.receitas.projetoreceita.modelo.Pessoa;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView add;
    private ListView lista;
    private ArrayAdapter<Pessoa> Servico;
    private List<Pessoa> pessoa;
    private PessoaDao pessoaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.addId);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, adicionarReceita.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listar();
    }

    private void listar() {
        pessoaDao = new PessoaDao(getApplicationContext());
        pessoa = pessoaDao.buscar();
        pessoaDao.close();
        lista = findViewById(R.id.listaId);
        Servico = new ArrayAdapter<Pessoa>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, pessoa);
        lista.setAdapter(Servico);
    }
}
