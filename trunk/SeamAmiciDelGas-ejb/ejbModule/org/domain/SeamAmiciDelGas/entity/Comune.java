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
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Roles;
import org.jboss.seam.annotations.Role;
import org.jboss.seam.annotations.Scope;

/**
 * Comune generated by hbm2java
 */
@Entity
@Table(name = "comune", catalog = "database_gas")
@Name(value="newComuneNascita")
@Scope(value=ScopeType.SESSION)
@Role(name="newComuneResidenza", scope=ScopeType.SESSION)

public class Comune implements java.io.Serializable {

	private Integer idcomune;
	private Provinces provinces;
	private String nome;
	private Integer cap;
	private Set<Utente> utentesForIdcomune = new HashSet<Utente>(0);
	private Set<Cybercontadino> cybercontadinos = new HashSet<Cybercontadino>(0);
	private Set<Utente> utentesForComuneNascita = new HashSet<Utente>(0);
	private Set<PuntiDiConsegna> puntiDiConsegnas = new HashSet<PuntiDiConsegna>(
			0);

	public Comune() {
	}

	public Comune(String nome) {
		this.nome = nome;
	}

	public Comune(Provinces provinces, String nome, Integer cap,
			Set<Utente> utentesForIdcomune,
			Set<Cybercontadino> cybercontadinos,
			Set<Utente> utentesForComuneNascita,
			Set<PuntiDiConsegna> puntiDiConsegnas) {
		this.provinces = provinces;
		this.nome = nome;
		this.cap = cap;
		this.utentesForIdcomune = utentesForIdcomune;
		this.cybercontadinos = cybercontadinos;
		this.utentesForComuneNascita = utentesForComuneNascita;
		this.puntiDiConsegnas = puntiDiConsegnas;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "IDComune", unique = true, nullable = false)
	public Integer getIdcomune() {
		return this.idcomune;
	}

	public void setIdcomune(Integer idcomune) {
		this.idcomune = idcomune;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provinces_idprovinces")
	public Provinces getProvinces() {
		return this.provinces;
	}

	public void setProvinces(Provinces provinces) {
		this.provinces = provinces;
	}

	@Column(name = "Nome", nullable = false, length = 50)
	@NotNull
	@Length(max = 50)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "CAP")
	public Integer getCap() {
		return this.cap;
	}

	public void setCap(Integer cap) {
		this.cap = cap;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "comuneByIdcomune")
	public Set<Utente> getUtentesForIdcomune() {
		return this.utentesForIdcomune;
	}

	public void setUtentesForIdcomune(Set<Utente> utentesForIdcomune) {
		this.utentesForIdcomune = utentesForIdcomune;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "comune")
	public Set<Cybercontadino> getCybercontadinos() {
		return this.cybercontadinos;
	}

	public void setCybercontadinos(Set<Cybercontadino> cybercontadinos) {
		this.cybercontadinos = cybercontadinos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "comuneByComuneNascita")
	public Set<Utente> getUtentesForComuneNascita() {
		return this.utentesForComuneNascita;
	}

	public void setUtentesForComuneNascita(Set<Utente> utentesForComuneNascita) {
		this.utentesForComuneNascita = utentesForComuneNascita;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "comune")
	public Set<PuntiDiConsegna> getPuntiDiConsegnas() {
		return this.puntiDiConsegnas;
	}

	public void setPuntiDiConsegnas(Set<PuntiDiConsegna> puntiDiConsegnas) {
		this.puntiDiConsegnas = puntiDiConsegnas;
	}

}
