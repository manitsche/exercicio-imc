package com.manitsche.exercicioimc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    ListView listViewPessoas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewPessoas = findViewById(R.id.listViewPessoas);
        Button botaoCadastrar = findViewById(R.id.botaoCadastrar);

        // Criação ou abertura do banco de dados
        db = openOrCreateDatabase("app_imc", MODE_PRIVATE, null);

        // Criação da tabela de pessoas
        db.execSQL("CREATE TABLE IF NOT EXISTS PESSOA (" +
                "idpessoa INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome VARCHAR, " +
                "peso REAL, " +
                "altura REAL, " +
                "imc REAL, " +
                "interpretacao VARCHAR)");

        // Listar os dados na ListView
        listarDados();

        // Ação do botão de cadastro para abrir a Activity de cadastro
        botaoCadastrar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });
    }

    private void listarDados() {
        Cursor cursor = db.rawQuery("SELECT idpessoa AS _id, nome, imc, interpretacao FROM PESSOA", null);

        CustomAdapter adapter = new CustomAdapter(this, cursor, 0);
        listViewPessoas.setAdapter(adapter);

        // Desabilitar cliques nos itens da lista
        listViewPessoas.setOnItemClickListener(null);
        listViewPessoas.setOnItemLongClickListener(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Atualizar a listagem quando voltar da tela de cadastro
        listarDados();
    }
}
