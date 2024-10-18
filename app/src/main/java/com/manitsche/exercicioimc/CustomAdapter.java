package com.manitsche.exercicioimc;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CustomAdapter extends CursorAdapter {

    public CustomAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textNome = view.findViewById(R.id.textNome);
        TextView textIMC = view.findViewById(R.id.textIMC);
        TextView textInterpretacao = view.findViewById(R.id.textInterpretacao);

        String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
        double imc = cursor.getDouble(cursor.getColumnIndexOrThrow("imc"));
        String interpretacao = cursor.getString(cursor.getColumnIndexOrThrow("interpretacao"));

        textNome.setText("Nome: " + nome);
        textIMC.setText("IMC: " + String.format("%.2f", imc));
        textInterpretacao.setText("Interpretação: " + interpretacao);
    }
}