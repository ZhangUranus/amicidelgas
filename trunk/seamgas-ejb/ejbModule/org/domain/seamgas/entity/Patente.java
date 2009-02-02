package org.domain.seamgas.entity;

// Generated 1-feb-2009 18.05.54 by Hibernate Tools 3.2.2.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * Patente generated by hbm2java
 */
@Entity
@Table(name = "patente", catalog = "seam")
public class Patente implements java.io.Serializable {

	private Integer idpatente;
	private Utente utente;
	private String tipo;

	public Patente() {
	}

	public Patente(Utente utente, String tipo) {
		this.utente = utente;
		this.tipo = tipo;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "IDPatente", unique = true, nullable = false)
	public Integer getIdpatente() {
		return this.idpatente;
	}

	public void setIdpatente(Integer idpatente) {
		this.idpatente = idpatente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CodiceFiscale", nullable = false)
	@NotNull
	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	@Column(name = "Tipo", nullable = false, length = 2)
	@NotNull
	@Length(max = 2)
	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
