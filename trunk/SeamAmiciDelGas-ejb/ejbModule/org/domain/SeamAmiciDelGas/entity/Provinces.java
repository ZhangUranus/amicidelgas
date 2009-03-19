package org.domain.SeamAmiciDelGas.entity;

// Generated 7-feb-2009 13.24.15 by Hibernate Tools 3.2.2.GA

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * Provinces generated by hbm2java
 */
@Entity
@Table(name = "provinces", catalog = "database_gas")
public class Provinces implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1134900126946124833L;
	private String idprovinces;
	private String nome;
	private Set<Comune> comunes = new HashSet<Comune>(0);

	public Provinces() {
	}

	public Provinces(String idprovinces, String nome) {
		this.idprovinces = idprovinces;
		this.nome = nome;
	}

	public Provinces(String idprovinces, String nome, Set<Comune> comunes) {
		this.idprovinces = idprovinces;
		this.nome = nome;
		this.comunes = comunes;
	}

	@Id
	@Column(name = "idprovinces", unique = true, nullable = false, length = 20)
	@NotNull
	@Length(max = 20)
	public String getIdprovinces() {
		return this.idprovinces;
	}

	public void setIdprovinces(String idprovinces) {
		this.idprovinces = idprovinces;
	}

	@Column(name = "nome", nullable = false, length = 20)
	@NotNull
	@Length(max = 20)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "provinces")
	public Set<Comune> getComunes() {
		return this.comunes;
	}

	public void setComunes(Set<Comune> comunes) {
		this.comunes = comunes;
	}

}
