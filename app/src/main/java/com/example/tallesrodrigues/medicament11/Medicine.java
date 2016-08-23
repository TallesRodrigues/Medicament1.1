package com.example.tallesrodrigues.medicament11;

/**
 * Created by TallesRodrigues on 8/13/2016.
 */

/**
 * Created by TallesRodrigues on 8/13/2016.
 */
public class Medicine {
    private String medicamento, concentracao;
    private String duracao_tipo, status, obs;
    private int dosagem, dosagem_tipo, turno_matutino, turno_vespertino, turno_noturno;
    private int periodo, duracao;
    private int periodo_tipo;

    public int getId_image() {
        return id_image;
    }

    public void setId_image(int id_image) {
        this.id_image = id_image;
    }

    private int id_image;

    public Medicine(String medicamento, String concentracao, int duracao, int dosagem, int period, int id_image) {
        this.medicamento = medicamento;
        this.concentracao = concentracao;
        this.duracao_tipo = "";
        this.dosagem = dosagem;
        this.periodo = period;
        periodo_tipo = dosagem_tipo = turno_matutino = turno_vespertino = turno_noturno = 0;
        this.id_image = id_image;

    }

    public Medicine() {
        this.medicamento = "";
        this.concentracao = "";
        this.duracao_tipo = "";
        this.dosagem = 0;
        this.periodo = 0;
        periodo_tipo = dosagem_tipo = turno_matutino = turno_vespertino = turno_noturno = 0;
        id_image = R.mipmap.pills;
    }


    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getConcentracao() {
        return concentracao;
    }

    public void setConcentracao(String concentracao) {
        this.concentracao = concentracao;
    }

    public String getDuracao_tipo() {
        return duracao_tipo;
    }

    public void setDuracao_tipo(String duracao_tipo) {
        this.duracao_tipo = duracao_tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public int getDosagem() {
        return dosagem;
    }

    public void setDosagem(int dosagem) {
        this.dosagem = dosagem;
    }

    public int getDosagem_tipo() {
        return dosagem_tipo;
    }

    public void setDosagem_tipo(int dosagem_tipo) {
        this.dosagem_tipo = dosagem_tipo;
    }

    public int getTurno_matutino() {
        return turno_matutino;
    }

    public void setTurno_matutino(int turno_matutino) {
        this.turno_matutino = turno_matutino;
    }

    public int getTurno_vespertino() {
        return turno_vespertino;
    }

    public void setTurno_vespertino(int turno_vespertino) {
        this.turno_vespertino = turno_vespertino;
    }

    public int getTurno_noturno() {
        return turno_noturno;
    }

    public void setTurno_noturno(int turno_noturno) {
        this.turno_noturno = turno_noturno;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public int getPeriodo_tipo() {
        return periodo_tipo;
    }

    public void setPeriodo_tipo(int periodo_tipo) {
        this.periodo_tipo = periodo_tipo;
    }


}


