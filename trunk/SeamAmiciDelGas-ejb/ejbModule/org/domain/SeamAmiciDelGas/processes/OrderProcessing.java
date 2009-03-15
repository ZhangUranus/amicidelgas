package org.domain.SeamAmiciDelGas.processes;

import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.domain.SeamAmiciDelGas.crud.AccountHome;
import org.domain.SeamAmiciDelGas.crud.OrdineList;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Articolo;
import org.domain.SeamAmiciDelGas.entity.Ordine;
import org.domain.SeamAmiciDelGas.session.GestioneFondo;
import org.domain.SeamAmiciDelGas.session.ItemQuantita;
import org.domain.SeamAmiciDelGas.session.Message;
import org.domain.SeamAmiciDelGas.session.MyOrdine;
import org.domain.SeamAmiciDelGas.session.OrdineBean;
import org.domain.SeamAmiciDelGas.webservices.CatalogInterface;
import org.domain.SeamAmiciDelGas.webservices.CatalogNoServiceImpl;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.bpm.BeginTask;
import org.jboss.seam.annotations.bpm.CreateProcess;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.security.Credentials;

@Name("orderProcessing")
@Scope(ScopeType.SESSION)
public class OrderProcessing {
	
	@In(value="myOrdine", scope=ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="myOrdine",scope=ScopeType.BUSINESS_PROCESS,required=false)
	private MyOrdine myOrdine;
	
	@In(value="dataRichiesta", scope=ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="dataRichiesta",scope=ScopeType.BUSINESS_PROCESS,required=false)
	private Date dataRichiesta;
	
	@Out(value="notificaDriverContadino",scope=ScopeType.BUSINESS_PROCESS,required=false)
	protected Message messageDriverContadino;
	
	@In(value="entityManager")
    private EntityManager em;
	
	@In(value="currentAccount", scope=ScopeType.SESSION, required=false)
	@Out(value="currentAccount", scope=ScopeType.BUSINESS_PROCESS, required=false)
	private Account currentAccount;
	
	
	@Out(value="notifyMessageStatoOrdine",scope=ScopeType.BUSINESS_PROCESS,required=false)
	protected Message messageStatoOrdine;
	
	@In(value="customer", scope=ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="customer", scope=ScopeType.BUSINESS_PROCESS, required=false)
	private Account customer;
	
	@In(value="driver", scope=ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="driver", scope=ScopeType.BUSINESS_PROCESS, required=false)
	private Account driver;
	
	@In(value="dataConsegna", scope=ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="dataConsegna", scope=ScopeType.BUSINESS_PROCESS, required=false)
	private Date dataConsegna;
	
	@Out(value="selectedItemShoppingCart",scope=ScopeType.BUSINESS_PROCESS,required=false)
	private List<ItemQuantita> selectedItem;
	
	@Out(value="dataMassimaShoppingCart",scope=ScopeType.BUSINESS_PROCESS,required=false)
	private Date dataMassima;

	@In private Credentials credentials;
	
	@In(value="gestioneFondo", create=true)
	private GestioneFondo gestioneFondo;
	
	@CreateProcess(definition="myOrderProcessing")
	public String startOrder(List<ItemQuantita> itemQ, Date dm){
		selectedItem = itemQ;
		dataMassima = dm;
		//setto la data di richiesta
		GregorianCalendar gc = new GregorianCalendar();
		dataRichiesta = gc.getTime();
		customer = currentAccount;
		myOrdine = new MyOrdine();
		myOrdine.setDataMassima(dataMassima);
		myOrdine.setItemQuantita(selectedItem);
		myOrdine.setDataRichiesta(dataRichiesta);
		//setto lo stato dell'ordine
		myOrdine.setPendente(true);
		myOrdine.setEvaso(false);
		boolean isStessoContadino=true;
		messageDriverContadino = new Message();
		
		String usernameContadino = selectedItem.get(0).getCybercontadino().getAccount().getUsername();
		for(ItemQuantita iq : selectedItem)//vedo se il contadino è sempre lo stesso
			if(!(iq.getCybercontadino().getAccount().getUsername().equals(usernameContadino)))
			{	
				isStessoContadino=false; break;
			}
		
		if(isStessoContadino)
			messageDriverContadino.addRecipient(usernameContadino);
		String content = "RICHIESTA ORDINE"+
						"Customer: " +credentials.getUsername();
		messageDriverContadino.setContent(content);
		return "partito";
	}
	
	@BeginTask @EndTask(transition="ordine_preso_in_carico")
	public void verificaDisponibilita(Date dataConsegna){
		//setto lo stato dell'ordine
		//myOrdine.setPendente(false);
		CatalogInterface catalog = new CatalogNoServiceImpl();
		Hashtable<String,UUID> transactionIdList = new Hashtable<String,UUID>();
		boolean isAvailable=true;
		//verifico la disponibilità per ogni contandino
		for (ItemQuantita iq : myOrdine.getItemQuantita()) {
			String idContadino = iq.getCybercontadino().getAccount().getUsername();
			UUID uuid = transactionIdList.get(idContadino);
			if(uuid==null)
			{	
				uuid = catalog.beginTransaction(idContadino,myOrdine.getDataMassima());
				transactionIdList.put(idContadino, uuid);
			}
			isAvailable=catalog.reserveItem(idContadino,uuid, iq.getItem(),iq.getQuantitaParziale(), iq.getQuantita());
			if(!isAvailable)
				break;
		}
		messageStatoOrdine =new Message();
		messageStatoOrdine.setMittente(credentials.getUsername());
		messageStatoOrdine.addRecipient(customer.getUsername());
		if(!isAvailable) //uno degli itemquantita non è disponibile
		{
			Enumeration<String> enumContadini = transactionIdList.keys();
			while(enumContadini.hasMoreElements())
			{
				String idContadino = enumContadini.nextElement();
				catalog.rollBackTransaction(idContadino, transactionIdList.get(idContadino));
			}
			messageStatoOrdine.setContent("Ordine non fattibile, e' stato rimesso in coda");
			messageStatoOrdine.setInfoFilter("orderProcessingNonPreso");
		}
		else //l'ordine può essere evaso
		{
			Enumeration<String> enumContadini = transactionIdList.keys();
			while(enumContadini.hasMoreElements())
			{
				String idContadino = enumContadini.nextElement();
				catalog.commitTransaction(idContadino, transactionIdList.get(idContadino));
			}
			String isNull = "NOT NULL";
			if (dataConsegna==null)
				isNull = "NULLLLL";
			messageStatoOrdine.setContent("Ordine preso in carico da "+ credentials.getUsername()+" dataConsegna = "+isNull);
			messageStatoOrdine.setInfoFilter("orderProcessingPreso");
			driver = currentAccount;
			myOrdine.setPendente(false);
			myOrdine.setEvaso(true);
			this.dataConsegna = dataConsegna;
			saveOrdine(); //salvo l'ordine nel database
		}
	}
	

	@BeginTask @EndTask(transition="attendi_data_consegna")
	public void attendiDataConsegna(){
		
	}
	
	@Transactional
	private void saveOrdine() {
		Ordine ordine = new Ordine();
		ordine.setAccount(customer);
		ordine.setConcluso(false);
		ordine.setDataRichiesta(dataRichiesta);
		ordine.setDataConclusione(dataConsegna);
		ordine.setDataMassimaConsegna(myOrdine.getDataMassima());
		ordine.setDriver(driver);
		//salvo l'ordine
		em.persist(ordine);
		
		Articolo articolo;
		for (ItemQuantita iq: myOrdine.getItemQuantita()) {
			articolo = new Articolo();
			articolo.setCodiceEsterno(iq.getItem().getName());
			articolo.setCybercontadino(iq.getCybercontadino());
			articolo.setDescrizione(iq.getItem().getDescription());
			articolo.setPrezzo((float) iq.getItem().getPrezzo());
			if (!iq.isBooleanIsQuantitaMinima())
				iq.setQuantitaParziale(iq.getQuantita());
			articolo.setQuantitaMinRichiesta(iq.getQuantitaParziale());
			articolo.setQuantitaRichiesta(iq.getQuantita());
			articolo.setOrdine(ordine);
			em.persist(articolo);
		}
	}
	
	@BeginTask @EndTask(transition="ordine_eliminato_dall_utente")
	public String delete() {
		//riaggiorno il fondo dell'utente customer
		float prezzoOrdine = 0;
		List<ItemQuantita> items = myOrdine.getItemQuantita();
		for (ItemQuantita iq: items)
			prezzoOrdine+=iq.getPrezzoTotale();
		gestioneFondo.plusFondo(prezzoOrdine);
		return "deleted";
	}
	
	@BeginTask @EndTask(transition="ordine_rimesso_nel_pool")
	public void notificaOrdineNonDisponibile() {
		
	}
	
	@BeginTask @EndTask(transition="ordine_preso_in_consegna")
	public void notificaOrdineDisponibile() {
		
	}

	/*
	 * Getter and settrer methods...
	 */
	
	@Destroy @Remove
	 public void destroy() {}

	
	public MyOrdine getMyOrdine() {
		return myOrdine;
	}

	public void setMyOrdine(MyOrdine myOrdine) {
		this.myOrdine = myOrdine;
	}

	public List<ItemQuantita> getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(List<ItemQuantita> selectedItem) {
		this.selectedItem = selectedItem;
	}

	public Date getDataMassima() {
		return dataMassima;
	}

	public void setDataMassima(Date dataMassima) {
		this.dataMassima = dataMassima;
	}
}
