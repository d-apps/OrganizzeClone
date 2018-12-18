package br.com.d_apps.organizze.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {

    //static para não precisar instanciar, será sempre o mesmo indenpendente de quantas vezes instanciar
    private static FirebaseAuth autenticacao;

    private static DatabaseReference firebase;

    // retorna a instancia o Firebasedatabase
    public static DatabaseReference getFirebaseDatabase(){

        if(firebase == null){
            firebase = FirebaseDatabase.getInstance().getReference();
        }
        return firebase;

    }

    //retorna a instancia do FirebaseAuth
    public static FirebaseAuth getFirebaseAutenticacao(){

        //Se autenticao for igual nulo, passa uma instancia do firebase
        if(autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao; //retorna autenticacao

    }
}
