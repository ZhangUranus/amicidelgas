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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.domain.SeamAmiciDelGas.validator.PartitaIVA;
import org.hibernate.validator.Email;
import org.hibernate.validator.Future;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Pattern;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Role;
import org.jboss.seam.annotations.Scope;

/**
 * Cybercontadino generated by hbm2java
 */
@Entity
@Table(name = "cybercontadino", catalog = "database_gas")
@Role(name = "currentContadino", scope = ScopeType.SESSION)
@Name("newCybercontadino")
@Scope(value=ScopeType.SESSION)
public class Cybercontadino implements java.io.Serializable {

	private String partitaIva;
	private Account account;
	private Comune comune;
	private String nomePresidente;
	private String indirizzo;
	private String cognomePresidente;
	private String nomeAzienda;
	private String pathAsl;
	private String urlWsdl;
	private String descrizioneAzienda;
	private String recapitoTelefonico;
	@Email (message="errore")
	private String email;
	private String coordinate;
	private Date dataVisita;
	private Set<Questionario> questionarios = new HashSet<Questionario>(0);
	private Set<Itinerario> itinerarios = new HashSet<Itinerario>(0);
	private Set<Articolo> articolos = new HashSet<Articolo>(0);

	public Cybercontadino() {
	}

	public Cybercontadino(String partitaIva, Account account, Comune comune,
			String nomePresidente, String indirizzo, String cognomePresidente,
			String nomeAzienda, String pathAsl, String recapitoTelefonico,
			String email) {
		this.partitaIva = partitaIva;
		this.account = account;
		this.comune = comune;
		this.nomePresidente = nomePresidente;
		this.indirizzo = indirizzo;
		this.cognomePresidente = cognomePresidente;
		this.nomeAzienda = nomeAzienda;
		this.pathAsl = pathAsl;
		this.recapitoTelefonico = recapitoTelefonico;
		this.email = email;
	}

	public Cybercontadino(String partitaIva, Account account, Comune comune,
			String nomePresidente, String indirizzo, String cognomePresidente,
			String nomeAzienda, String pathAsl, String urlWsdl,
			String descrizioneAzienda, String recapitoTelefonico, String email,
			String coordinate, Set<Questionario> questionarios,
			Set<Itinerario> itinerarios, Set<Articolo> articolos) {
		this.partitaIva = partitaIva;
		this.account = account;
		this.comune = comune;
		this.nomePresidente = nomePresidente;
		this.indirizzo = indirizzo;
		this.cognomePresidente = cognomePresidente;
		this.nomeAzienda = nomeAzienda;
		this.pathAsl = pathAsl;
		this.urlWsdl = urlWsdl;
		this.descrizioneAzienda = descrizioneAzienda;
		this.recapitoTelefonico = recapitoTelefonico;
		this.email = email;
		this.coordinate = coordinate;
		this.questionarios = questionarios;
		this.itinerarios = itinerarios;
		this.articolos = articolos;
	}

	@Id
	@Column(name = "PartitaIVA", unique = true, nullable = false, length = 11)
	@NotNull
	@Length(max = 11)
	@Pattern(regex="^[0-9]{11}$", message="Formato partita IVA non corretto")
	@PartitaIVA
	public String getPartitaIva() {
		return this.partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Comune_ubicazione", nullable = false)
	@NotNull
	public Comune getComune() {
		return this.comune;
	}

	public void setComune(Comune comune) {
		this.comune = comune;
	}

	@Column(name = "NomePresidente", nullable = false, length = 50)
	@NotNull
	@Length(max = 50)
	public String getNomePresidente() {
		return this.nomePresidente;
	}

	public void setNomePresidente(String nomePresidente) {
		this.nomePresidente = nomePresidente;
	}

	@Column(name = "Indirizzo", nullable = false, length = 100)
	@NotNull
	@Length(max = 100)
	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	@Column(name = "CognomePresidente", nullable = false, length = 50)
	@NotNull
	@Length(max = 50)
	public String getCognomePresidente() {
		return this.cognomePresidente;
	}

	public void setCognomePresidente(String cognomePresidente) {
		this.cognomePresidente = cognomePresidente;
	}

	@Column(name = "NomeAzienda", nullable = false, length = 50)
	@NotNull
	@Length(max = 50)
	public String getNomeAzienda() {
		return this.nomeAzienda;
	}

	public void setNomeAzienda(String nomeAzienda) {
		this.nomeAzienda = nomeAzienda;
	}

	@Column(name = "PathASL", nullable = false, length = 500)
	@NotNull
	@Length(max = 500)
	public String getPathAsl() {
		return this.pathAsl;
	}

	public void setPathAsl(String pathAsl) {
		this.pathAsl = pathAsl;
	}

	@Column(name = "UrlWSDL", length = 500)
	@NotNull
	@Length(max = 500)
	public String getUrlWsdl() {
		return this.urlWsdl;
	}

	public void setUrlWsdl(String urlWsdl) {
		this.urlWsdl = urlWsdl;
	}

	@Column(name = "DescrizioneAzienda", length = 65535)
	@Length(max = 65535)
	public String getDescrizioneAzienda() {
		return this.descrizioneAzienda;
	}

	public void setDescrizioneAzienda(String descrizioneAzienda) {
		this.descrizioneAzienda = descrizioneAzienda;
	}

	@Column(name = "recapito_telefonico", nullable = false)
	@NotNull
	public String getRecapitoTelefonico() {
		return this.recapitoTelefonico;
	}

	public void setRecapitoTelefonico(String recapitoTelefonico) {
		this.recapitoTelefonico = recapitoTelefonico;
	}

	@Column(name = "email", nullable = false, length = 200)
	@NotNull
	@Length(max = 200)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "Coordinate", length = 500)
	@Length(max = 500)
	@NotNull
	public String getCoordinate() {
		return this.coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cybercontadino")
	public Set<Questionario> getQuestionarios() {
		return this.questionarios;
	}

	public void setQuestionarios(Set<Questionario> questionarios) {
		this.questionarios = questionarios;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cybercontadinos")
	public Set<Itinerario> getItinerarios() {
		return this.itinerarios;
	}

	public void setItinerarios(Set<Itinerario> itinerarios) {
		this.itinerarios = itinerarios;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cybercontadino")
	public Set<Articolo> getArticolos() {
		return this.articolos;
	}

	public void setArticolos(Set<Articolo> articolos) {
		this.articolos = articolos;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dataVisita", nullable = false)
	@NotNull
	@Future
	public Date getDataVisita() {
		return dataVisita;
	}

	public void setDataVisita(Date dataVisita) {
		this.dataVisita = dataVisita;
	}
	
	public boolean equals(Cybercontadino contadino) {
		if(this.partitaIva.equals(contadino.getPartitaIva()))
				return true;
		return false;
	}
	

}
