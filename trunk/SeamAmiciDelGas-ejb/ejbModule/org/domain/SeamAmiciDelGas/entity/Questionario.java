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

/**
 * Questionario generated by hbm2java
 */
@Entity
@Table(name = "questionario", catalog = "database_gas")
public class Questionario implements java.io.Serializable {

	private Integer idquestionario;
	private Account account;
	private Cybercontadino cybercontadino;
	private Integer votoProdotti;
	private Integer votoStabile;
	private Integer votoIgiene;
	private Integer votoAllevamento;
	private Integer votoProfessionalita;
	private float votoGlobale;
	private String commenti;
	private boolean visionato;
	private Date dataVisita;
	private Date dataCompilazione;

	public Questionario() {
	}

	public Questionario(Account account, Cybercontadino cybercontadino,
			boolean visionato, Date dataVisita, Date dataCompilazione) {
		this.account = account;
		this.cybercontadino = cybercontadino;
		this.visionato = visionato;
		this.dataVisita = dataVisita;
		this.dataCompilazione = dataCompilazione;
	}

	public Questionario(Account account, Cybercontadino cybercontadino,
			Integer votoProdotti, Integer votoStabile, Integer votoIgiene,
			Integer votoAllevamento, Integer votoProfessionalita,
			float votoGlobale, String commenti, boolean visionato,
			Date dataVisita, Date dataCompilazione) {
		this.account = account;
		this.cybercontadino = cybercontadino;
		this.votoProdotti = votoProdotti;
		this.votoStabile = votoStabile;
		this.votoIgiene = votoIgiene;
		this.votoAllevamento = votoAllevamento;
		this.votoProfessionalita = votoProfessionalita;
		this.votoGlobale = votoGlobale;
		this.commenti = commenti;
		this.visionato = visionato;
		this.dataVisita = dataVisita;
		this.dataCompilazione = dataCompilazione;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idquestionario", unique = true, nullable = false)
	public Integer getIdquestionario() {
		return this.idquestionario;
	}

	public void setIdquestionario(Integer idquestionario) {
		this.idquestionario = idquestionario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Account_Username", nullable = false)
	@NotNull
	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CyberContadino_PartitaIVA", nullable = false)
	@NotNull
	public Cybercontadino getCybercontadino() {
		return this.cybercontadino;
	}

	public void setCybercontadino(Cybercontadino cybercontadino) {
		this.cybercontadino = cybercontadino;
	}

	@Column(name = "voto_prodotti")
	public Integer getVotoProdotti() {
		return this.votoProdotti;
	}

	public void setVotoProdotti(Integer votoProdotti) {
		this.votoProdotti = votoProdotti;
	}

	@Column(name = "voto_stabile")
	public Integer getVotoStabile() {
		return this.votoStabile;
	}

	public void setVotoStabile(Integer votoStabile) {
		this.votoStabile = votoStabile;
	}

	@Column(name = "voto_igiene")
	public Integer getVotoIgiene() {
		return this.votoIgiene;
	}

	public void setVotoIgiene(Integer votoIgiene) {
		this.votoIgiene = votoIgiene;
	}

	@Column(name = "voto_allevamento")
	public Integer getVotoAllevamento() {
		return this.votoAllevamento;
	}

	public void setVotoAllevamento(Integer votoAllevamento) {
		this.votoAllevamento = votoAllevamento;
	}

	@Column(name = "voto_professionalita")
	public Integer getVotoProfessionalita() {
		return this.votoProfessionalita;
	}

	public void setVotoProfessionalita(Integer votoProfessionalita) {
		this.votoProfessionalita = votoProfessionalita;
	}

	@Column(name = "voto_globale")
	public float getVotoGlobale() {
		return this.votoGlobale;
	}

	public void setVotoGlobale(float votoGlobale) {
		this.votoGlobale = votoGlobale;
	}

	@Column(name = "commenti", length = 500)
	@Length(max = 500)
	public String getCommenti() {
		return this.commenti;
	}

	public void setCommenti(String commenti) {
		this.commenti = commenti;
	}

	@Column(name = "visionato", nullable = false)
	@NotNull
	public boolean isVisionato() {
		return this.visionato;
	}

	public void setVisionato(boolean visionato) {
		this.visionato = visionato;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_visita", nullable = false, length = 10)
	@NotNull
	public Date getDataVisita() {
		return this.dataVisita;
	}

	public void setDataVisita(Date dataVisita) {
		this.dataVisita = dataVisita;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_compilazione", nullable = false, length = 19)
	@NotNull
	public Date getDataCompilazione() {
		return this.dataCompilazione;
	}

	public void setDataCompilazione(Date dataCompilazione) {
		this.dataCompilazione = dataCompilazione;
	}

}
