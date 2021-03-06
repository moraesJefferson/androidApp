package com.receitas.projetoreceita;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.receitas.projetoreceita.dao.PessoaDao;
import com.receitas.projetoreceita.modelo.Pessoa;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAdd;
    private ListView lista;
    private ArrayAdapter<Pessoa> Servico;
    private List<Pessoa> pessoa;
    private PessoaDao pessoaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = findViewById(R.id.listaId);
        fabAdd = findViewById(R.id.fabAddId);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        MenuItem msg = menu.add("Mandar mensagem");
        Intent intentWhat = new Intent(Intent.ACTION_SENDTO);
        String num = pessoa.getTelefone();
        if (num.startsWith("9") && num.length() == 9) {
            int numStr = num.length();
            num = num.substring(1, numStr);
        }
        intentWhat.setData(Uri.parse("smsto:" + num));
        intentWhat.setPackage("com.whatsapp");
        msg.setIntent(Intent.createChooser(intentWhat, ""));

        MenuItem fone = menu.add("Ligar");
        fone.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123);
                } else {
                    Intent intentFone = new Intent(Intent.ACTION_CALL);
                    intentFone.setData(Uri.parse("tel:" + pessoa.getTelefone()));
                    startActivity(intentFone);
                }
                return false;
            }
        });
    }
}
