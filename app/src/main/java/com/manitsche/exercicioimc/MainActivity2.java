package com.manitsche.exercicioimc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity {

    private SQLiteDatabase db;
    private EditText campoNome, campoPeso, campoAltura;
    private Button botaoSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        campoNome = findViewById(R.id.campoNome);
        campoPeso = findViewById(R.id.campoPeso);
        campoAltura = findViewById(R.id.campoAltura);
        botaoSalvar = findViewById(R.id.botaoSalvar);

        // Abrir ou criar o banco de dados
        db = openOrCreateDatabase("app_imc", MODE_PRIVATE, null);

        botaoSalvar.setOnClickListener(v -> salvarDados());
    }

    private void salvarDados() {
        String nome = campoNome.getText().toString();
        double peso;
        double altura;

        try {
            peso = Double.parseDouble(campoPeso.getText().toString());
            altura = Double.parseDouble(campoAltura.getText().toString());
        } catch (NumberFormatException e) {
            exibeDialogo("Peso e altura devem ser números");
            return;
        }

        double imc = calcularIMC(peso, altura);
        String interpretacao = interpretarIMC(imc);

        // Exibir o valor do IMC e a interpretação
        new AlertDialog.Builder(this)
                .setTitle("Mensagem")
                .setMessage("IMC: " + String.format("%.2f", imc) + "\n\n" + interpretacao)
                .setPositiveButton("OK", (dialog, which) -> {
                    salvarNoBanco(nome, peso, altura, imc, interpretacao);
                    finish(); // Fechar a Activity e voltar à listagem
                })
                .show();
    }

    private double calcularIMC(double peso, double altura) {
        return peso / (altura * altura);
    }

    private String interpretarIMC(double imc) {
        if (imc < 18.5) {
            return "Abaixo do peso";
        } else if (imc < 24.9) {
            return "Peso normal";
        } else if (imc < 29.9) {
            return "Sobrepeso";
        } else {
            return "Obesidade";
        }
    }

    private void salvarNoBanco(String nome, double peso, double altura, double imc, String interpretacao) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", nome);
        contentValues.put("peso", peso);
        contentValues.put("altura", altura);
        contentValues.put("imc", imc);
        contentValues.put("interpretacao", interpretacao);

        long result = db.insert("PESSOA", null, contentValues);

        if (result != -1) {
            exibeDialogo("Dados salvos com sucesso!");
        } else {
            exibeDialogo("Erro ao salvar os dados");
        }
    }

    private void exibeDialogo(String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mensagem:");
        builder.setMessage(mensagem);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Fecha o diálogo
            }
        });

        AlertDialog dialogo = builder.create();
        dialogo.show();
    }
}