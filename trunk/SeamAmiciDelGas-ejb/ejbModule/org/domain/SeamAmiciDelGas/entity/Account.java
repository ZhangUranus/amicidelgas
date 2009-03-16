package org.domain.SeamAmiciDelGas.entity;

// Generated 7-feb-2009 13.24.15 by Hibernate Tools 3.2.2.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Role;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.security.management.UserPassword;
import org.jboss.seam.annotations.security.management.UserPrincipal;
import org.jboss.seam.annotations.security.management.UserRoles;

/**
 * Account generated by hbm2java
 */
@Entity
@Table(name = "account", catalog = "database_gas")
@Role(name = "currentAccount", scope = ScopeType.SESSION)
@Name(value="newAccount")
@Scope(value=ScopeType.SESSION)
public class Account implements java.io.Serializable {

	private String username;
	private Utente utente;
	private Pagamentoelettronico pagamentoelettronico;
	private String passwordHash;
	private Boolean bloccato;
	private Boolean elimato;
	private Boolean cancellato;
	private Boolean attivato;
	private Date dataRichiesta;
	private Date dataAccettazione;
	private float punteggioFeedback;
	private int numeroVotanti=1;
	private float fondo = 0.0f;
	private Set<Itinerario> itinerarios = new HashSet<Itinerario>(0);
	private Set<Cybercontadino> cybercontadinos = new HashSet<Cybercontadino>(0);
	private Set<org.domain.SeamAmiciDelGas.entity.Role> roles = new HashSet<org.domain.SeamAmiciDelGas.entity.Role>(0);
	private Set<Questionario> questionarios = new HashSet<Questionario>(0);
	private Set<Feedback> feedbacksForDestinatario = new HashSet<Feedback>(0);
	private Set<Feedback> feedbacksForSegnalatore = new HashSet<Feedback>(0);
	private Set<Feedback> feedbacksForValidatore = new HashSet<Feedback>(0);
	private Set<Ordine> ordines = new HashSet<Ordine>(0);

	public Account() {
	}

	public Account(String username, String passwordHash) {
		this.username = username;
		this.passwordHash = passwordHash;
	}

	public Account(String username, Utente utente,
			Pagamentoelettronico pagamentoelettronico, String passwordHash,
			Boolean bloccato, Boolean elimato, Boolean cancellato,
			Boolean attivato, Date dataRichiesta, Date dataAccettazione, float fondo,
			float punteggioFeedback, Set<Itinerario> itinerarios,
			Set<Cybercontadino> cybercontadinos, Set<org.domain.SeamAmiciDelGas.entity.Role> roles,
			Set<Questionario> questionarios,
			Set<Feedback> feedbacksForDestinatario,
			Set<Feedback> feedbacksForSegnalatore,
			Set<Feedback> feedbacksForValidatore, Set<Ordine> ordines) {
		this.username = username;
		this.utente = utente;
		this.pagamentoelettronico = pagamentoelettronico;
		this.passwordHash = passwordHash;
		this.bloccato = bloccato;
		this.elimato = elimato;
		this.cancellato = cancellato;
		this.attivato = attivato;
		this.dataRichiesta = dataRichiesta;
		this.dataAccettazione = dataAccettazione;
		this.fondo = fondo;
		this.punteggioFeedback = punteggioFeedback;
		this.itinerarios = itinerarios;
		this.cybercontadinos = cybercontadinos;
		this.roles = roles;
		this.questionarios = questionarios;
		this.feedbacksForDestinatario = feedbacksForDestinatario;
		this.feedbacksForSegnalatore = feedbacksForSegnalatore;
		this.feedbacksForValidatore = feedbacksForValidatore;
		this.ordines = ordines;
	}

	@Id
	@Column(name = "Username", unique = true, nullable = false, length = 20)
	@NotNull
	@Length(max = 20 , min= 4)
	@UserPrincipal
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Utente_CodiceFiscale")
	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PagamentoElettronico_idPagamentoElettronico")
	public Pagamentoelettronico getPagamentoelettronico() {
		return this.pagamentoelettronico;
	}

	public void setPagamentoelettronico(
			Pagamentoelettronico pagamentoelettronico) {
		this.pagamentoelettronico = pagamentoelettronico;
	}

	@Column(name = "passwordHash", nullable = false, length = 40)
	@NotNull
	@Length(max = 40 , min = 6)
	@UserPassword(hash="SHA")
	public String getPasswordHash() {
		return this.passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Column(name = "Bloccato")
	public Boolean getBloccato() {
		return this.bloccato;
	}

	public void setBloccato(Boolean bloccato) {
		this.bloccato = bloccato;
	}

	@Column(name = "Elimato")
	public Boolean getElimato() {
		return this.elimato;
	}

	public void setElimato(Boolean elimato) {
		this.elimato = elimato;
	}

	@Column(name = "Cancellato")
	public Boolean getCancellato() {
		return this.cancellato;
	}

	public void setCancellato(Boolean cancellato) {
		this.cancellato = cancellato;
	}

	@Column(name = "Attivato")
	public Boolean getAttivato() {
		return this.attivato;
	}

	public void setAttivato(Boolean attivato) {
		this.attivato = attivato;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DataRichiesta", length = 10)
	public Date getDataRichiesta() {
		return this.dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DataAccettazione", length = 10)
	public Date getDataAccettazione() {
		return this.dataAccettazione;
	}

	public void setDataAccettazione(Date dataAccettazione) {
		this.dataAccettazione = dataAccettazione;
	}

	@Column(name = "PunteggioFeedback")
	public float getPunteggioFeedback() {
		return this.punteggioFeedback;
	}

	public void setPunteggioFeedback(float punteggioFeedback) {
		this.punteggioFeedback = punteggioFeedback;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
	public Set<Itinerario> getItinerarios() {
		return this.itinerarios;
	}

	public void setItinerarios(Set<Itinerario> itinerarios) {
		this.itinerarios = itinerarios;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
	public Set<Cybercontadino> getCybercontadinos() {
		return this.cybercontadinos;
	}

	public void setCybercontadinos(Set<Cybercontadino> cybercontadinos) {
		this.cybercontadinos = cybercontadinos;
	}

	@UserRoles
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
	public Set<org.domain.SeamAmiciDelGas.entity.Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<org.domain.SeamAmiciDelGas.entity.Role> roles) {
		this.roles = roles;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
	public Set<Questionario> getQuestionarios() {
		return this.questionarios;
	}

	public void setQuestionarios(Set<Questionario> questionarios) {
		this.questionarios = questionarios;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "accountByDestinatario")
	public Set<Feedback> getFeedbacksForDestinatario() {
		return this.feedbacksForDestinatario;
	}

	public void setFeedbacksForDestinatario(
			Set<Feedback> feedbacksForDestinatario) {
		this.feedbacksForDestinatario = feedbacksForDestinatario;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "accountBySegnalatore")
	public Set<Feedback> getFeedbacksForSegnalatore() {
		return this.feedbacksForSegnalatore;
	}

	public void setFeedbacksForSegnalatore(Set<Feedback> feedbacksForSegnalatore) {
		this.feedbacksForSegnalatore = feedbacksForSegnalatore;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "accountByValidatore")
	public Set<Feedback> getFeedbacksForValidatore() {
		return this.feedbacksForValidatore;
	}

	public void setFeedbacksForValidatore(Set<Feedback> feedbacksForValidatore) {
		this.feedbacksForValidatore = feedbacksForValidatore;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
	public Set<Ordine> getOrdines() {
		return this.ordines;
	}

	public void setOrdines(Set<Ordine> ordines) {
		this.ordines = ordines;
	}

	@Column(name = "fondo")
	public float getFondo() {
		return fondo;
	}

	public void setFondo(float fondo) {
		this.fondo = fondo;
	}

	@Column(name = "numeroVotanti")
	public int getNumeroVotanti() {
		return numeroVotanti;
	}

	public void setNumeroVotanti(int numeroVotanti) {
		this.numeroVotanti = numeroVotanti;
	}

}
