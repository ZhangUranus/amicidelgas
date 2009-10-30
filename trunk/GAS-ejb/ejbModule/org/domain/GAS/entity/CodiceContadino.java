package org.domain.GAS.entity;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.NotNull;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Entity
@Table(name = "codicecontadino", catalog = "database_gas")
@Name("newCodiceContadino")
@Scope(value=ScopeType.SESSION)
public class CodiceContadino implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8181615707166663412L;
	private Integer id;
	private String descrizione;

	public CodiceContadino() {
	}

	public CodiceContadino(String descrizione) {
		this.descrizione = descrizione;
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

	@Column(name = "descrizione", nullable = false)
	@NotNull
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
