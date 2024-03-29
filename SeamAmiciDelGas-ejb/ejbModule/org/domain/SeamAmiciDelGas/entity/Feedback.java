package org.domain.SeamAmiciDelGas.entity;

// Generated 7-feb-2009 13.24.15 by Hibernate Tools 3.2.2.GA

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.Name;

/**
 * Feedback generated by hbm2java
 */
@Entity
@Table(name = "feedback", catalog = "database_gas")
@Name("newFeedback")
public class Feedback implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6410508667783071743L;
	private Integer idfeedback;
	private Account accountByDestinatario;
	private Ordine ordine;
	private Account accountByValidatore;
	private Account accountBySegnalatore;
	private boolean analizzato;
	private String descrizione;
	private Date dataValidazione;
	private Date dataSegnalazione;
	private float punteggio;

	public Feedback() {
	}

	public Feedback(Account accountByDestinatario, Ordine ordine,
			Account accountByValidatore, Account accountBySegnalatore,
			boolean analizzato, Date dataValidazione, Date dataSegnalazione) {
		this.accountByDestinatario = accountByDestinatario;
		this.ordine = ordine;
		this.accountByValidatore = accountByValidatore;
		this.accountBySegnalatore = accountBySegnalatore;
		this.analizzato = analizzato;
		this.dataValidazione = dataValidazione;
		this.dataSegnalazione = dataSegnalazione;
	}

	public Feedback(Account accountByDestinatario, Ordine ordine,
			Account accountByValidatore, Account accountBySegnalatore,
			boolean analizzato, String descrizione, Date dataValidazione,
			Date dataSegnalazione, float punteggio) {
		this.accountByDestinatario = accountByDestinatario;
		this.ordine = ordine;
		this.accountByValidatore = accountByValidatore;
		this.accountBySegnalatore = accountBySegnalatore;
		this.analizzato = analizzato;
		this.descrizione = descrizione;
		this.dataValidazione = dataValidazione;
		this.dataSegnalazione = dataSegnalazione;
		this.punteggio = punteggio;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "IDFeedback", unique = true, nullable = false)
	public Integer getIdfeedback() {
		return this.idfeedback;
	}

	public void setIdfeedback(Integer idfeedback) {
		this.idfeedback = idfeedback;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Destinatario", nullable = false)
	@NotNull
	public Account getAccountByDestinatario() {
		return this.accountByDestinatario;
	}

	public void setAccountByDestinatario(Account accountByDestinatario) {
		this.accountByDestinatario = accountByDestinatario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDOrdine", nullable=true)
	public Ordine getOrdine() {
		return this.ordine;
	}

	public void setOrdine(Ordine ordine) {
		this.ordine = ordine;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Validatore", nullable = false)
	@NotNull
	public Account getAccountByValidatore() {
		return this.accountByValidatore;
	}

	public void setAccountByValidatore(Account accountByValidatore) {
		this.accountByValidatore = accountByValidatore;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Segnalatore", nullable = false)
	@NotNull
	public Account getAccountBySegnalatore() {
		return this.accountBySegnalatore;
	}

	public void setAccountBySegnalatore(Account accountBySegnalatore) {
		this.accountBySegnalatore = accountBySegnalatore;
	}

	@Column(name = "Analizzato", nullable = false)
	@NotNull
	public boolean isAnalizzato() {
		return this.analizzato;
	}

	public void setAnalizzato(boolean analizzato) {
		this.analizzato = analizzato;
	}

	@Column(name = "Descrizione", length = 65535)
	@Length(max = 65535)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DataValidazione", length = 10)
	public Date getDataValidazione() {
		return this.dataValidazione;
	}

	public void setDataValidazione(Date dataValidazione) {
		this.dataValidazione = dataValidazione;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DataSegnalazione", nullable = false, length = 10)
	@NotNull
	public Date getDataSegnalazione() {
		return this.dataSegnalazione;
	}

	public void setDataSegnalazione(Date dataSegnalazione) {
		this.dataSegnalazione = dataSegnalazione;
	}

	@Column(name = "Punteggio" ,nullable = false)
	@NotNull
	public float getPunteggio() {
		return this.punteggio;
	}

	public void setPunteggio(float punteggio) {
		this.punteggio = punteggio;
	}

}
