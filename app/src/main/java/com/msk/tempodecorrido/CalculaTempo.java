package com.msk.tempodecorrido;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class CalculaTempo {


    public int CalculoDeDias(int di, int mi, int ai, int df, int mf, int af) {

        Calendar dataInicial = Calendar.getInstance();
        dataInicial.set(ai, mi, di, 0, 0, 0);
        Calendar dataFinal = Calendar.getInstance();
        dataFinal.set(af, mf, df, 0, 0, 0);

        int start = (int) TimeUnit.MILLISECONDS.toDays(dataInicial.getTimeInMillis());

        int end = (int) TimeUnit.MILLISECONDS.toDays(dataFinal.getTimeInMillis());

        return (end - start);
    }

    public int[] CalculoDeSemanas(int di, int mi, int ai, int df, int mf, int af) {

        Calendar dataInicial = Calendar.getInstance();
        dataInicial.set(ai, mi, di, 0, 0, 0);
        Calendar dataFinal = Calendar.getInstance();
        dataFinal.set(af, mf, df, 0, 0, 0);

        int start = (int) TimeUnit.MILLISECONDS.toDays(dataInicial.getTimeInMillis());

        int end = (int) TimeUnit.MILLISECONDS.toDays(dataFinal.getTimeInMillis());

        double numero = (double) (end - start) / 7;
        int semana = (int) numero;
        numero = (end - start) - semana * 7;

        return new int[]{(int) numero, semana};
    }

    public int[] CalculoDeMeses(int di, int mi, int ai, int df, int mf, int af) {

        Calendar dataInicial = Calendar.getInstance();
        dataInicial.set(ai, mi, di, 0, 0, 0);
        Calendar dataFinal = Calendar.getInstance();
        dataFinal.set(af, mf, df, 0, 0, 0);

        int start = (int) TimeUnit.MILLISECONDS.toDays(dataInicial.getTimeInMillis());

        int end = (int) TimeUnit.MILLISECONDS.toDays(dataFinal.getTimeInMillis());

        double numero = (double) (end - start) / 30;
        int mes = (int) numero;
        numero = (end - start) - mes * 30;

        return new int[]{(int) numero, mes};
    }

    public int[] CalculoDeAnos(int di, int mi, int ai, int df, int mf, int af) {

        Calendar dataInicial = Calendar.getInstance();
        dataInicial.set(ai, mi, di, 0, 0, 0);
        Calendar dataFinal = Calendar.getInstance();
        dataFinal.set(af, mf, df, 0, 0, 0);

        int start = (int) TimeUnit.MILLISECONDS.toDays(dataInicial.getTimeInMillis());

        int end = (int) TimeUnit.MILLISECONDS.toDays(dataFinal.getTimeInMillis());

        double numero = (double) (end - start) / 365;
        int ano = (int) numero;
        numero = (end - start) - ano * 365;

        return new int[]{(int) numero, ano};
    }

    public int[] CalculoDeTempo(int di, int mi, int ai, int df, int mf, int af) {

        Calendar dataInicial = Calendar.getInstance();
        dataInicial.set(ai, mi, di, 0, 0, 0);
        Calendar dataFinal = Calendar.getInstance();
        dataFinal.set(af, mf, df, 0, 0, 0);

        int start = (int) TimeUnit.MILLISECONDS.toDays(dataInicial.getTimeInMillis());

        int end = (int) TimeUnit.MILLISECONDS.toDays(dataFinal.getTimeInMillis());

        double numero = (double) (end - start) / 365;

        int anos = (int) numero;

        numero = (end - start) - anos * 365;

        int mes = (int) numero / 30;

        int dia = (int) numero - mes * 30;

        return new int[]{dia, mes, anos};
    }

    public int tempo(int di, int mi, int ai, int df, int mf, int af) {

        // Mensagem de erro
        int erro = 0;

        // Ajustes no tempo decorrido
        if (ai == af) { // Ano atual
            if (mi == mf) { // Mes atual
                if (di > df) { // Dia posterior
                    // Mensagem de erro (Data Futura)
                    erro = erro - 1;
                }
            } else if (mi > mf) { // Mes posterior
                // Mensagem erro (Data Futura)
                erro = erro - 1;
            }
        } else if (ai > af){ // Ano posterior
            // Mensagem de erro (Data futura)
            erro = erro - 1;
        }

        if (erro < 0)
            return erro;
        else
            return 0;
    }
}
