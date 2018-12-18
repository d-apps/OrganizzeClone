package br.com.d_apps.organizze.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import br.com.d_apps.organizze.config.ConfiguracaoFirebase;

public class Usuario {

    private String idUsuario;
    private String nome;
    private String email;
    private String senha;
    private Double receitaTotal = 0.00;
    private Double despesaTotal = 0.00;

    @Exclude // Não salvar o id do usuário no banco
    public String getIdUsuario() {
        return idUsuario;
    }


    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario() {
    }

    public void salvar(){

        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
        firebase.child("usuarios")
                .child(this.idUsuario) // id do usuário
                .setValue(this); // Salva o objeto usuário

    }

    public Double getReceitaTotal() {
        return receitaTotal;
    }

    public void setReceitaTotal(Double receitaTotal) {
        this.receitaTotal = receitaTotal;
    }

    public Double getDespesaTotal() {
        return despesaTotal;
    }

    public void setDespesaTotal(Double despesaTotal) {
        this.despesaTotal = despesaTotal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
