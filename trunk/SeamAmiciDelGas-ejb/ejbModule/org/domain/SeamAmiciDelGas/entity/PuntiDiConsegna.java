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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * PuntiDiConsegna generated by hbm2java
 */
@Entity
@Table(name = "punti_di_consegna", catalog = "database_gas")
@Name("newPuntiDiConsegna")
@Scope(value=ScopeType.SESSION)
public class PuntiDiConsegna implements java.io.Serializable {

	private Integer idpuntiConsegna;
	private Comune comune;
	private String indirizzo;
	private String coordinate;
	private Set<Itinerario> itinerarios = new HashSet<Itinerario>(0);

	public PuntiDiConsegna() {
	}

	public PuntiDiConsegna(Comune comune, String indirizzo) {
		this.comune = comune;
		this.indirizzo = indirizzo;
	}

	public PuntiDiConsegna(Comune comune, String indirizzo, String coordinate,
			Set<Itinerario> itinerarios) {
		this.comune = comune;
		this.indirizzo = indirizzo;
		this.coordinate = coordinate;
		this.itinerarios = itinerarios;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "IDPuntiConsegna", unique = true, nullable = false)
	public Integer getIdpuntiConsegna() {
		return this.idpuntiConsegna;
	}

	public void setIdpuntiConsegna(Integer idpuntiConsegna) {
		this.idpuntiConsegna = idpuntiConsegna;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDComune", nullable = false)
	@NotNull
	public Comune getComune() {
		return this.comune;
	}

	public void setComune(Comune comune) {
		this.comune = comune;
	}

	@Column(name = "Indirizzo", nullable = false, length = 500)
	@NotNull
	@Length(max = 500)
	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	@Column(name = "Coordinate", length = 500)
	@Length(max = 500)
	public String getCoordinate() {
		return this.coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "itinerario_has_punti_di_consegna", catalog = "database_gas", joinColumns = { @JoinColumn(name = "IDPuntiConsegna", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "IDItinerario", nullable = false, updatable = false) })
	public Set<Itinerario> getItinerarios() {
		return this.itinerarios;
	}

	public void setItinerarios(Set<Itinerario> itinerarios) {
		this.itinerarios = itinerarios;
	}

}
