package com.receitas.projetoreceita;

import android.Manifest;
import android.app.Activity;
import android.arch.core.BuildConfig;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.receitas.projetoreceita.dao.PessoaDao;
import com.receitas.projetoreceita.modelo.Pessoa;

import java.io.File;
import java.security.Permission;

public class adicionarReceita extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 222;
    private FormularioHelper helper;
    private FloatingActionButton fabCamera;
    private Pessoa pessoa;
    private Pessoa pessoaUpdate;
    private PessoaDao pessoaDao;
    private String caminhoFoto;

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
        fabCamera = findViewById(R.id.formulario_botao_camera);
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(adicionarReceita.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(adicionarReceita.this, new String[]{Manifest.permission.CAMERA}, 142);
                } else {
                    Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                    File arquivoFoto = new File(caminhoFoto);
                    Uri uri = FileProvider.getUriForFile(adicionarReceita.this, "com.receitas.projetoreceita.provider",arquivoFoto);
                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intentCamera, CODIGO_CAMERA);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( resultCode == Activity.RESULT_OK){
            if (requestCode == CODIGO_CAMERA){
               helper.carregaImagem(caminhoFoto);
            }
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
                if (pessoa.getId() != null) {
                    pessoaDao.update(pessoa);
                    pessoaDao.close();
                    Toast.makeText(getApplicationContext(), "Salvando dados", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    if (pessoa.getNome().isEmpty() || pessoa.getServico().isEmpty() || pessoa.getCpf().isEmpty() || pessoa.getTelefone().isEmpty() || pessoa.getEndereco().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "PREENCHA TODOS OS CAMPOS", Toast.LENGTH_LONG).show();
                    } else {
                        pessoaDao.insere(pessoa);
                        pessoaDao.close();
                        Toast.makeText(getApplicationContext(), "Salvando dados", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


