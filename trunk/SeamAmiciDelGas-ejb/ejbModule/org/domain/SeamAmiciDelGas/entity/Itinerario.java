package org.domain.SeamAmiciDelGas.entity;

// Generated 7-feb-2009 13.24.15 by Hibernate Tools 3.2.2.GA

import java.util.Date;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * Itinerario generated by hbm2java
 */
@Entity
@Table(name = "itinerario", catalog = "database_gas")
public class Itinerario implements java.io.Serializable {

	private Integer iditinerario;
	private Account account;
	private Date dataPartenza;
	private Date dataConsegna;
	private String documentazionePath;
	private Date dataCreazione;
	private Set<Ordine> ordines = new HashSet<Ordine>(0);
	private Set<PuntiDiConsegna> puntiDiConsegnas = new HashSet<PuntiDiConsegna>(
			0);
	private Set<Cybercontadino> cybercontadinos = new HashSet<Cybercontadino>(0);

	public Itinerario() {
	}

	public Itinerario(Account account, Date dataPartenza, Date dataConsegna,
			Date dataCreazione) {
		this.account = account;
		this.dataPartenza = dataPartenza;
		this.dataConsegna = dataConsegna;
		this.dataCreazione = dataCreazione;
	}

	public Itinerario(Account account, Date dataPartenza, Date dataConsegna,
			String documentazionePath, Date dataCreazione, Set<Ordine> ordines,
			Set<PuntiDiConsegna> puntiDiConsegnas,
			Set<Cybercontadino> cybercontadinos) {
		this.account = account;
		this.dataPartenza = dataPartenza;
		this.dataConsegna = dataConsegna;
		this.documentazionePath = documentazionePath;
		this.dataCreazione = dataCreazione;
		this.ordines = ordines;
		this.puntiDiConsegnas = puntiDiConsegnas;
		this.cybercontadinos = cybercontadinos;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "IDItinerario", unique = true, nullable = false)
	public Integer getIditinerario() {
		return this.iditinerario;
	}

	public void setIditinerario(Integer iditinerario) {
		this.iditinerario = iditinerario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Username", nullable = false)
	@NotNull
	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DataPartenza", nullable = false, length = 19)
	@NotNull
	public Date getDataPartenza() {
		return this.dataPartenza;
	}

	public void setDataPartenza(Date dataPartenza) {
		this.dataPartenza = dataPartenza;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DataConsegna", nullable = false, length = 19)
	@NotNull
	public Date getDataConsegna() {
		return this.dataConsegna;
	}

	public void setDataConsegna(Date dataConsegna) {
		this.dataConsegna = dataConsegna;
	}

	@Column(name = "DocumentazionePath", length = 500)
	@Length(max = 500)
	public String getDocumentazionePath() {
		return this.documentazionePath;
	}

	public void setDocumentazionePath(String documentazionePath) {
		this.documentazionePath = documentazionePath;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DataCreazione", nullable = false, length = 19)
	@NotNull
	public Date getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "itinerarios")
	public Set<Ordine> getOrdines() {
		return this.ordines;
	}

	public void setOrdines(Set<Ordine> ordines) {
		this.ordines = ordines;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "itinerarios")
	public Set<PuntiDiConsegna> getPuntiDiConsegnas() {
		return this.puntiDiConsegnas;
	}

	public void setPuntiDiConsegnas(Set<PuntiDiConsegna> puntiDiConsegnas) {
		this.puntiDiConsegnas = puntiDiConsegnas;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "itinerario_has_cybercontadino", catalog = "database_gas", joinColumns = { @JoinColumn(name = "IDItinerario", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "PartitaIVA", nullable = false, updatable = false) })
	public Set<Cybercontadino> getCybercontadinos() {
		return this.cybercontadinos;
	}

	public void setCybercontadinos(Set<Cybercontadino> cybercontadinos) {
		this.cybercontadinos = cybercontadinos;
	}

}