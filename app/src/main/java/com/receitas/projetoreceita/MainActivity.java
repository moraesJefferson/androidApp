package com.receitas.projetoreceita;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
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
        lista = findViewById(R.id.listaId);
        add = findViewById(R.id.addId);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, adicionarReceita.class));
            }
        });

        registerForContextMenu(lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                Pessoa pessoa = (Pessoa) lista.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, adicionarReceita.class);
                intent.putExtra("pessoa", pessoa);
                startActivity(intent);
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
        Servico = new ArrayAdapter<Pessoa>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, pessoa);
        lista.setAdapter(Servico);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Pessoa pessoa = (Pessoa) lista.getItemAtPosition(info.position);

        MenuItem delete = menu.add("Delete");
        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                PessoaDao pessoaDao = new PessoaDao(getApplicationContext());
                pessoaDao.deletar(pessoa);
                pessoaDao.close();
                listar();
                Toast.makeText(getApplicationContext(), "Serviço Deletado", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        MenuItem map = menu.add("Ver Endereço");
        Intent intentMap = new Intent(Intent.ACTION_VIEW);
        intentMap.setData(Uri.parse("geo:0,0?q=" + pessoa.getEndereco()));
        map.setIntent(intentMap);
    }
}
