package com.receitas.projetoreceita;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.receitas.projetoreceita.dao.PessoaDao;
import com.receitas.projetoreceita.modelo.Pessoa;

public class adicionarReceita extends AppCompatActivity {

    private FormularioHelper helper;
    private AlertDialog.Builder dialog;
    private Pessoa pessoa;
    private Pessoa pessoaUpdate;
    private PessoaDao pessoaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_receita);
        this.helper = new FormularioHelper(this);
        this.pessoaDao = new PessoaDao(getApplicationContext());

        Intent intent = getIntent();
        pessoaUpdate = (Pessoa) intent.getSerializableExtra("pessoa");
        if (pessoaUpdate != null) {
            helper.preencheFormulario(pessoaUpdate);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario_proseguir, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nextId:
                pessoa = helper.pegaDados();
                if (pessoa.getId() != null){
                    pessoaDao.update(pessoa);
                }else{
                    if (pessoa.getNome().isEmpty() || pessoa.getServico().isEmpty() || pessoa.getCpf().isEmpty() || pessoa.getTelefone().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "PREENCHA TODOS OS CAMPOS", Toast.LENGTH_LONG).show();
                    } else {
                        pessoaDao.insere(pessoa);
                    }
                }
                pessoaDao.close();
                Toast.makeText(getApplicationContext(), "Salvando dados", Toast.LENGTH_SHORT).show();
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


