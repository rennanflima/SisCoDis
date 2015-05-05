/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.entidades;

/**
 *
 * @author Rennan Francisco
 */
public class SalaEquipDefeituoso {
    
    private Salas sala = new Salas();
    private Turmas turma = new Turmas();
    private EquipamentoDaSala eqs = new EquipamentoDaSala();

    public Salas getSala() {
        return sala;
    }

    public void setSala(Salas sala) {
        this.sala = sala;
    }

    public Turmas getTurma() {
        return turma;
    }

    public void setTurma(Turmas turma) {
        this.turma = turma;
    }

    public EquipamentoDaSala getEqs() {
        return eqs;
    }

    public void setEqs(EquipamentoDaSala eqs) {
        this.eqs = eqs;
    }
    
}
