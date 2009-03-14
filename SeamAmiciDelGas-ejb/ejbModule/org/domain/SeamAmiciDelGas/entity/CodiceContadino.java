package org.domain.SeamAmiciDelGas.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Role;
import org.jboss.seam.annotations.Scope;

@Entity
@Table(name = "codicecontadino", catalog = "database_gas")
@Name("newCodiceContadino")
@Scope(value=ScopeType.SESSION)
public class CodiceContadino {
	
	private Integer id;
	private Cybercontadino cybercontadino;

	public CodiceContadino() {
	}

	public CodiceContadino(Cybercontadino cybercontadino) {
		this.cybercontadino = cybercontadino;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PartitaIva", nullable = false)
	@NotNull
	public Cybercontadino getCybercontadino() {
		return this.cybercontadino;
	}

	public void setCybercontadino(Cybercontadino cybercontadino) {
		this.cybercontadino = cybercontadino;
	}

}
