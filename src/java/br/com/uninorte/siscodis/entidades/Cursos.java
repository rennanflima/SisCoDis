/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Rennan Francisco
 */
@Entity
public class Cursos implements Serializable {

    @Id
    @GeneratedValue
    private Integer idCursos;
    @Column(nullable = false, unique = true)
    private Integer codigo;
    @Column(nullable = false, length = 50)
    private String nome;
    private Integer qtdPeriodos;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Instituicoes instituicao = new Instituicoes();

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getIdCursos() {
        return idCursos;
    }

    public void setIdCursos(Integer idCursos) {
        this.idCursos = idCursos;
    }

    public Instituicoes getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicoes instituicao) {
        this.instituicao = instituicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQtdPeriodos() {
        return qtdPeriodos;
    }

    public void setQtdPeriodos(Integer qtdPeriodos) {
        this.qtdPeriodos = qtdPeriodos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.idCursos != null ? this.idCursos.hashCode() : 0);
        hash = 31 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
        hash = 31 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 31 * hash + (this.qtdPeriodos != null ? this.qtdPeriodos.hashCode() : 0);
        hash = 31 * hash + (this.instituicao != null ? this.instituicao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cursos other = (Cursos) obj;
        if (this.idCursos != other.idCursos && (this.idCursos == null || !this.idCursos.equals(other.idCursos))) {
            return false;
        }
        if (this.codigo != other.codigo && (this.codigo == null || !this.codigo.equals(other.codigo))) {
            return false;
        }
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if (this.qtdPeriodos != other.qtdPeriodos && (this.qtdPeriodos == null || !this.qtdPeriodos.equals(other.qtdPeriodos))) {
            return false;
        }
        if (this.instituicao != other.instituicao && (this.instituicao == null || !this.instituicao.equals(other.instituicao))) {
            return false;
        }
        return true;
    }
}
