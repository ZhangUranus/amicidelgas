package org.domain.GAS.entity;

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

import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.SnowballPorterFilterFactory;
import org.apache.solr.analysis.StandardFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.apache.solr.analysis.StopFilterFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * Articolo generated by hbm2java
 */
@Indexed
@AnalyzerDef(
	       name="italianSnowballArticolo", 
	      tokenizer = 
	           @TokenizerDef(factory = StandardTokenizerFactory.class ), 
	      filters = { 
	           @TokenFilterDef(factory=StandardFilterFactory.class),
	           @TokenFilterDef(factory=LowerCaseFilterFactory.class),
	           @TokenFilterDef(factory = StopFilterFactory.class, 
	               params = @Parameter(name="words", 
	                                           value="org/domain/GAS/session/stopwords.txt") ), 
	           @TokenFilterDef( 
	               factory = SnowballPorterFilterFactory.class,
	               params = @Parameter(name="language", 
	                                           value="Italian") )
	} )
@Entity
@Table(name = "articolo", catalog = "database_gas")
public class Articolo implements java.io.Serializable {

	private static final long serialVersionUID = 2967434424485676201L;
	private Integer idarticolo;
	private Cybercontadino cybercontadino;
	private String codiceEsterno;
	private String descrizione;
	private Float prezzo;
	private int quantitaOttenuta;
	private int quantitaRichiesta;
	private int quantitaMinRichiesta;
	private Ordine ordine;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDOrdine", nullable = false)
	@NotNull
	public Ordine getOrdine() {
		return ordine;
	}

	public void setOrdine(Ordine ordine) {
		this.ordine = ordine;
	}

	public Articolo() {
	}

	public Articolo(String descrizione) {
		this.descrizione = descrizione;
	}

	public Articolo(Cybercontadino cybercontadino, String codiceEsterno,
			String descrizione, Float prezzo, Ordine ordine) {
		this.cybercontadino = cybercontadino;
		this.codiceEsterno = codiceEsterno;
		this.descrizione = descrizione;
		this.prezzo= prezzo;
		this.ordine = ordine;
	}
	
	@DocumentId
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "IDArticolo", unique = true, nullable = false)
	public Integer getIdarticolo() {
		return this.idarticolo;
	}

	public void setIdarticolo(Integer idarticolo) {
		this.idarticolo = idarticolo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CyberContadino_PartitaIVA")
	public Cybercontadino getCybercontadino() {
		return this.cybercontadino;
	}

	public void setCybercontadino(Cybercontadino cybercontadino) {
		this.cybercontadino = cybercontadino;
	}

	@Column(name = "CodiceEsterno", length = 45)
	@Length(max = 45)
	public String getCodiceEsterno() {
		return this.codiceEsterno;
	}

	public void setCodiceEsterno(String codiceEsterno) {
		this.codiceEsterno = codiceEsterno;
	}

	@Field(index=Index.TOKENIZED,  analyzer=@Analyzer(definition="italianSnowballArticolo"))
	@Column(name = "Descrizione", nullable = false, length = 65535)
	@NotNull
	@Length(max = 65535)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	@Column(name = "QuantitaMinRichiesta", nullable = true)
	public int getQuantitaMinRichiesta() {
		return quantitaMinRichiesta;
	}
	
	public void setQuantitaRichiesta(int quantitaRichiesta) {
		this.quantitaRichiesta = quantitaRichiesta;
	}

	public void setQuantitaMinRichiesta(int quantitaMinRichiesta) {
		this.quantitaMinRichiesta = quantitaMinRichiesta;
	}
	
	@Column(name = "QuantitaOttenuta", nullable = true)
	public int getQuantitaOttenuta() {
		return quantitaOttenuta;
	}

	public void setQuantitaOttenuta(int quantitaOttenuta) {
		this.quantitaOttenuta = quantitaOttenuta;
	}
	
	@Column(name = "QuantitaRichiesta", nullable = false)
	@NotNull
	public int getQuantitaRichiesta() {
		return quantitaRichiesta;
	}

	@Column(name = "prezzo", precision = 12, scale = 0)
	public Float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Float prezzo) {
		this.prezzo = prezzo;
	}

}
