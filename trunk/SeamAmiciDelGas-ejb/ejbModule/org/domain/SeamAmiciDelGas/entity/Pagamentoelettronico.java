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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * Pagamentoelettronico generated by hbm2java
 */
@Entity
@Table(name = "pagamentoelettronico", catalog = "database_gas")
public class Pagamentoelettronico implements java.io.Serializable {

	private Integer idPagamentoElettronico;
	private String tipoCarta;
	private int numeroCarta;
	private Set<Account> accounts = new HashSet<Account>(0);

	public Pagamentoelettronico() {
	}

	public Pagamentoelettronico(int numeroCarta) {
		this.numeroCarta = numeroCarta;
	}

	public Pagamentoelettronico(String tipoCarta, int numeroCarta,
			Set<Account> accounts) {
		this.tipoCarta = tipoCarta;
		this.numeroCarta = numeroCarta;
		this.accounts = accounts;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idPagamentoElettronico", unique = true, nullable = false)
	public Integer getIdPagamentoElettronico() {
		return this.idPagamentoElettronico;
	}

	public void setIdPagamentoElettronico(Integer idPagamentoElettronico) {
		this.idPagamentoElettronico = idPagamentoElettronico;
	}

	@Column(name = "tipo_carta", length = 45)
	@Length(max = 45)
	public String getTipoCarta() {
		return this.tipoCarta;
	}

	public void setTipoCarta(String tipoCarta) {
		this.tipoCarta = tipoCarta;
	}

	@Column(name = "numero_carta", nullable = false)
	@NotNull
	public int getNumeroCarta() {
		return this.numeroCarta;
	}

	public void setNumeroCarta(int numeroCarta) {
		this.numeroCarta = numeroCarta;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pagamentoelettronico")
	public Set<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

}
