package org.domain.SeamAmiciDelGas.entity;

// Generated 7-feb-2009 13.24.15 by Hibernate Tools 3.2.2.GA

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * Articolo generated by hbm2java
 */
@Entity
@Table(name = "articolo", catalog = "database_gas")
public class Articolo implements java.io.Serializable {

	private Integer idarticolo;
	private Cybercontadino cybercontadino;
	private String codiceEsterno;
	private String descrizione;
	private boolean prenotazioneLungoTermine;
	private Float prezzoDefinitivo;
	private Set<Ordine> ordines = new HashSet<Ordine>(0);

	public Articolo() {
	}

	public Articolo(String descrizione, boolean prenotazioneLungoTermine) {
		this.descrizione = descrizione;
		this.prenotazioneLungoTermine = prenotazioneLungoTermine;
	}

	public Articolo(Cybercontadino cybercontadino, String codiceEsterno,
			String descrizione, boolean prenotazioneLungoTermine,
			Float prezzoDefinitivo, Set<Ordine> ordines) {
		this.cybercontadino = cybercontadino;
		this.codiceEsterno = codiceEsterno;
		this.descrizione = descrizione;
		this.prenotazioneLungoTermine = prenotazioneLungoTermine;
		this.prezzoDefinitivo = prezzoDefinitivo;
		this.ordines = ordines;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "IDArticolo", unique = true, nullable = false)
	public Integer getIdarticolo() {
		return this.idarticolo;
	}

	public void setIdarticolo(Integer idarticolo) {
		this.idarticolo = idarticolo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CyberContadino_PartitaIVA")
	public Cybercontadino getCybercontadino() {
		return this.cybercontadino;
	}

	public void setCybercontadino(Cybercontadino cybercontadino) {
		this.cybercontadino = cybercontadino;
	}

	@Column(name = "CodiceEsterno", length = 45)
	@Length(max = 45)
	public String getCodiceEsterno() {
		return this.codiceEsterno;
	}

	public void setCodiceEsterno(String codiceEsterno) {
		this.codiceEsterno = codiceEsterno;
	}

	@Column(name = "Descrizione", nullable = false, length = 65535)
	@NotNull
	@Length(max = 65535)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name = "PrenotazioneLungoTermine", nullable = false)
	@NotNull
	public boolean isPrenotazioneLungoTermine() {
		return this.prenotazioneLungoTermine;
	}

	public void setPrenotazioneLungoTermine(boolean prenotazioneLungoTermine) {
		this.prenotazioneLungoTermine = prenotazioneLungoTermine;
	}

	@Column(name = "Prezzo_definitivo", precision = 12, scale = 0)
	public Float getPrezzoDefinitivo() {
		return this.prezzoDefinitivo;
	}

	public void setPrezzoDefinitivo(Float prezzoDefinitivo) {
		this.prezzoDefinitivo = prezzoDefinitivo;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "articolo")
	public Set<Ordine> getOrdines() {
		return this.ordines;
	}

	public void setOrdines(Set<Ordine> ordines) {
		this.ordines = ordines;
	}

}