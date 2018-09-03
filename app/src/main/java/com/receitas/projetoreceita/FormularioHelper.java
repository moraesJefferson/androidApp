package com.receitas.projetoreceita;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;

import com.receitas.projetoreceita.modelo.Pessoa;

public class FormularioHelper {

    private EditText nomeId;
    private EditText cpfId;
    private EditText servicoId;
    private EditText foneId;
    private EditText enderecoId;
    private final ImageView campoFoto;
    private Pessoa pessoa;

    public FormularioHelper(adicionarReceita activity) {

        nomeId = activity.findViewById(R.id.nomeId);
        cpfId = activity.findViewById(R.id.cpfId);
        servicoId = activity.findViewById(R.id.servicoId);
        foneId = activity.findViewById(R.id.foneId);
        enderecoId = activity.findViewById(R.id.enderecoId);
        campoFoto = activity.findViewById(R.id.fotoId);
        pessoa = new Pessoa();
    }

    public Pessoa pegaDados() {
        pessoa.setNome(nomeId.getText().toString());
        pessoa.setCpf(cpfId.getText().toString());
        pessoa.setServico(servicoId.getText().toString());
        pessoa.setTelefone(foneId.getText().toString());
        pessoa.setEndereco(enderecoId.getText().toString());
        pessoa.setFoto((String) campoFoto.getTag());
        return pessoa;
    }

    public void preencheFormulario(Pessoa pessoa) {

        nomeId.setText(pessoa.getNome());
        cpfId.setText(pessoa.getCpf());
        servicoId.setText(pessoa.getServico());
        foneId.setText(pessoa.getTelefone());
        enderecoId.setText(pessoa.getEndereco());
        if (pessoa.getFoto() != null) {
            carregaImagem(pessoa.getFoto());
        }
        this.pessoa = pessoa;
    }

    public void carregaImagem(String caminhoFoto) {
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
        }
    }
}
