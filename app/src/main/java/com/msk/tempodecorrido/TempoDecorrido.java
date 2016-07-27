package com.msk.tempodecorrido;

import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;


public class TempoDecorrido extends AppCompatActivity {

    private DatePicker dataInicial, dataFinal;
    private Calendar cInicial = Calendar.getInstance();
    private Calendar cFinal = Calendar.getInstance();
    private TextView resultado;
    private PackageInfo info;
    private Resources r = null;
    private String nrVersao, dmaEntreDatas, semanasEntreDatas, diasEntreDatas, mesesEntreDatas, anosEntreDatas;
    private int dInicial, mInicial, aInicial, dFinal, mFinal, aFinal, tempo, selecao, teste;
    CalculaTempo novoTempo = new CalculaTempo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tempo_decorrido);

        r = getResources();

        tempo = 1;
        selecao = teste = 0;
        resultado = (TextView) findViewById(R.id.text_resultado);
        dataInicial = (DatePicker) findViewById(R.id.datePicker);
        dataFinal = (DatePicker) findViewById(R.id.datePicker1);

        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        nrVersao = info.versionName;

        dInicial = cInicial.get(Calendar.DAY_OF_MONTH);
        mInicial = cInicial.get(Calendar.MONTH);
        aInicial = cInicial.get(Calendar.YEAR);

        dFinal = cFinal.get(Calendar.DAY_OF_MONTH);
        mFinal = cFinal.get(Calendar.MONTH);
        aFinal = cFinal.get(Calendar.YEAR);

        dataInicial.init(cInicial.get(Calendar.YEAR), cInicial.get(Calendar.MONTH),
                cInicial.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int ano,
                                              int mes, int dia) {

                        // Define a data inicial
                        dInicial = dia;
                        mInicial = mes;
                        aInicial = ano;

                        CalculoEntreDatas();
                    }
                });

        dataFinal.init(cFinal.get(Calendar.YEAR), cFinal.get(Calendar.MONTH),
                cFinal.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int ano,
                                              int mes, int dia) {

                        // Define a data final
                        dFinal = dia;
                        mFinal = mes;
                        aFinal = ano;

                        CalculoEntreDatas();
                    }
                });

        ImageView ivMenu = (ImageView) findViewById(R.id.ivMenu);

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogoOpcoes();
            }
        });
    }

    public void CalculoEntreDatas() {

        // Confere data inicial e futura
        tempo = novoTempo.tempo(dInicial, mInicial, aInicial, dFinal, mFinal, aFinal);

        // Calcula o tempo decorrido
        int dias = novoTempo.CalculoDeDias(dInicial, mInicial, aInicial, dFinal, mFinal, aFinal);
        int[] semanas = novoTempo.CalculoDeSemanas(dInicial, mInicial, aInicial, dFinal, mFinal, aFinal);
        int meses[] = novoTempo.CalculoDeMeses(dInicial, mInicial, aInicial, dFinal, mFinal, aFinal);
        int anos[] = novoTempo.CalculoDeAnos(dInicial, mInicial, aInicial, dFinal, mFinal, aFinal);
        int[] tempo2 = novoTempo.CalculoDeTempo(dInicial, mInicial, aInicial, dFinal, mFinal, aFinal);

        // Resultado do calculo do tempo decorrido
        dmaEntreDatas = r.getQuantityString(R.plurals.ano, tempo2[2], tempo2[2])
                + " " + r.getQuantityString(R.plurals.mes, tempo2[1], tempo2[1])
                + " " + r.getQuantityString(R.plurals.dia, tempo2[0], tempo2[0]);

        diasEntreDatas = r.getQuantityString(R.plurals.dia, dias, dias);

        semanasEntreDatas = r.getQuantityString(R.plurals.semana, semanas[1], semanas[1])
                + " " + r.getQuantityString(R.plurals.dia, semanas[0], semanas[0]);

        mesesEntreDatas = r.getQuantityString(R.plurals.mes, meses[1], meses[1])
                + " " + r.getQuantityString(R.plurals.dia, meses[0], meses[0]);

        anosEntreDatas = r.getQuantityString(R.plurals.ano0, anos[1], anos[1])
                + " " + r.getQuantityString(R.plurals.dia, anos[0], anos[0]);

        if (tempo < 0) {
            resultado.setText("Data final anterior à data inicial.");
        } else if (tempo == 0){
            MostraResultados(selecao);
        } else {
            resultado.setText("Defina as datas para calcular");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tempo_decorrido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_sobre:
                DialogoSobre();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void DialogoSobre() {
        new AlertDialog.Builder(this)
                .setTitle("Sobre o aplicativo")
                .setMessage(r.getString(R.string.sobre, nrVersao))
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface pDialogo,
                                                int pInt) {
                                pDialogo.dismiss();
                            }
                        }).show();

    }

    private void DialogoOpcoes() {

        new AlertDialog.Builder(this)
                .setTitle("Opções de tempo decorrido")
                .setSingleChoiceItems(R.array.menu_opcoes, selecao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        teste = selecao;
                        selecao = id;
                    }
                })
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface pDialogo,
                                                int pInt) {
                                teste = selecao;
                                MostraResultados(selecao);
                                pDialogo.dismiss();
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface pDialogo,
                                                int pInt) {
                                selecao = teste;
                                MostraResultados(selecao);
                                pDialogo.dismiss();
                            }
                        })
                .show();

    }

    private void MostraResultados(int id){
        switch (id) {
            case 0:
                resultado.setText(dmaEntreDatas);
                break;
            case 1:
                resultado.setText(diasEntreDatas);
                break;
            case 2:
                resultado.setText(semanasEntreDatas);
                break;
            case 3:
                resultado.setText(mesesEntreDatas);
                break;
            case 4:
                resultado.setText(anosEntreDatas);
                break;
        }

        if (tempo == 1){
            resultado.setText("Defina as datas para calcular");
        }
    }

}
