package br.com.d_apps.organizze.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.com.d_apps.organizze.R;
import br.com.d_apps.organizze.config.ConfiguracaoFirebase;
import br.com.d_apps.organizze.helper.Base64Custom;
import br.com.d_apps.organizze.helper.DateCustom;
import br.com.d_apps.organizze.model.Movimentacao;
import br.com.d_apps.organizze.model.Usuario;

public class ReceitasActivity extends AppCompatActivity {

    private TextInputEditText campoData, campoCategoria, campoDescricao;
    private EditText campoValor;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private Double receitaTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);

        campoData = findViewById(R.id.editData);
        campoCategoria  = findViewById(R.id.editCategoria);
        campoDescricao = findViewById(R.id.editDescricao);
        campoValor = findViewById(R.id.editValor);

        //Preenche o campo data com o date atual
        campoData.setText(DateCustom.dataAtual());

        recuperarReceitaTotal();

    }

    public void salvarReceita(View view){

        if(validarCamposReceita()){

            movimentacao = new Movimentacao();
            String data = campoData.getText().toString();
            Double valorRecuperado = Double.parseDouble(campoValor.getText().toString());

            movimentacao.setValor(valorRecuperado);
            movimentacao.setCategoria(campoCategoria.getText().toString());
            movimentacao.setDescricao(campoDescricao.getText().toString());
            movimentacao.setData(data);
            movimentacao.setTipo("r");

            Double receitaAtualizada = receitaTotal + valorRecuperado;

            atualizarReceita(receitaAtualizada);

            movimentacao.salvar(data);
            finish();

        }

    }

    public Boolean validarCamposReceita() {

        String textoValor = campoValor.getText().toString();
        String textoData = campoData.getText().toString();
        String textoCategoria = campoCategoria.getText().toString();
        String textoDescricao = campoDescricao.getText().toString();

        if (!textoValor.isEmpty()) {

            if (!textoData.isEmpty()) {

                if (!textoCategoria.isEmpty()) {

                    if (!textoDescricao.isEmpty()) {

                        return true;
                    }else {

                        Toast.makeText(ReceitasActivity.this, "Preencha a descrição!", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                } else {

                    Toast.makeText(ReceitasActivity.this, "Preencha a Categoria!", Toast.LENGTH_SHORT).show();
                    return false;
                }

            } else {

                Toast.makeText(ReceitasActivity.this, "Preencha a Data!", Toast.LENGTH_SHORT).show();
                return false;
            }

        } else {

            Toast.makeText(ReceitasActivity.this, "Preencha o valor!", Toast.LENGTH_SHORT).show();
            return false;

        }


    }

    public void recuperarReceitaTotal() {

        String email = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(email);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                receitaTotal = usuario.getReceitaTotal();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

        public void atualizarReceita (Double receita){

            String email = autenticacao.getCurrentUser().getEmail();
            String idUsuario = Base64Custom.codificarBase64(email);
            DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

            usuarioRef.child("receitaTotal").setValue(receita);

        }

}
