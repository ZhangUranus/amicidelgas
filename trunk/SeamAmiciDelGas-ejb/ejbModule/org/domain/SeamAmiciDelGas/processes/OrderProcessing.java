package org.domain.SeamAmiciDelGas.processes;

import java.util.ArrayList;
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
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.entity.Ordine;
import org.domain.SeamAmiciDelGas.session.GestioneFondo;
import org.domain.SeamAmiciDelGas.session.ItemQuantita;
import org.domain.SeamAmiciDelGas.session.LoginSelectBean;
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
	
	@Out(value="notificaDataMassimaScaduta",scope=ScopeType.BUSINESS_PROCESS,required=false)
	protected Message messageDataMassimaScaduta;
	
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
	
	@In(value="responsabileConsegna", scope=ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="responsabileConsegna", scope=ScopeType.BUSINESS_PROCESS, required=false)
	private Account responsabileConsegna;
	
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
	
	@In(value="loginSelectBean", scope=ScopeType.SESSION, required=false)
	private LoginSelectBean loginSelectBean;
	
	//@In(value="responsabileIsDriver", scope=ScopeType.BUSINESS_PROCESS,required=false)
	//@Out(value="responsabileIsDriver", scope=ScopeType.BUSINESS_PROCESS,required=false)
	//private boolean responsabileIsDriver;
	
	//liste per i feedback
/*	@In(value="feedbackListCustomerToContadini", scope=ScopeType.BUSINESS_PROCESS,required=false)
	@Out(value="feedbackListCustomerToContadini", scope=ScopeType.BUSINESS_PROCESS,required=false)
	private List<Cybercontadino> feedbackListCustomerToContadini;
	
	@In(value="feedbackListDriverToContadini", scope=ScopeType.BUSINESS_PROCESS,required=false)
	@Out(value="feedbackListDriverToContadini", scope=ScopeType.BUSINESS_PROCESS,required=false)
	private List<Cybercontadino> feedbackListDriverToContadini;
	
	@In(value="feedbackListContadiniToDriver", scope=ScopeType.BUSINESS_PROCESS,required=false)
	@Out(value="feedbackListContadiniToDriver", scope=ScopeType.BUSINESS_PROCESS,required=false)
	private Hashtable<String,Boolean> feedbackListContadiniToDriver;
	
	@In(value="feedbackDriverToCustomer", scope=ScopeType.BUSINESS_PROCESS,required=false)
	@Out(value="feedbackDriverToCustomer", scope=ScopeType.BUSINESS_PROCESS,required=false)
	private boolean feedbackDriverToCustomer;

	@In(value="feedbackCustomerToDriver", scope=ScopeType.BUSINESS_PROCESS,required=false)
	@Out(value="feedbackCustomerToDriver", scope=ScopeType.BUSINESS_PROCESS,required=false)
	private boolean feedbackCustomerToDriver;
	//customerToDriver
	//DriverToCustomer
*/	
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
		
		messageDataMassimaScaduta = new Message();
		messageDataMassimaScaduta.setMittente("Sistema GAS");
		messageDataMassimaScaduta.addRecipient(customer.getUsername());
		messageDataMassimaScaduta.setContent("Ordine non evaso entro data massima");		
		
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
			responsabileConsegna = currentAccount;
			/*if (loginSelectBean.isDriver())
				responsabileIsDriver = true;
			else 
				responsabileIsDriver = false;*/
			myOrdine.setPendente(false);
			myOrdine.setEvaso(true);
			this.dataConsegna = dataConsegna;
			saveOrdine(); //salvo l'ordine nel database
			//setto le variabili per i feedback
/*			feedbackListCustomerToContadini = listCybercontadiniEffettivi();
			feedbackListDriverToContadini = listCybercontadiniEffettivi();
			feedbackListContadiniToDriver = new Hashtable<String,Boolean>();
			for (Cybercontadino c: feedbackListCustomerToContadini)
				feedbackListContadiniToDriver.put(c.getPartitaIva(), new Boolean(false));
			feedbackDriverToCustomer = false;
			feedbackCustomerToDriver = false;		
*/			
		}
	}
	
	private List<Cybercontadino> listCybercontadiniEffettivi() {
		List<Cybercontadino> contadiniEffettivi = new ArrayList<Cybercontadino>();
		for (ItemQuantita iq: myOrdine.getItemQuantita()) {
			if (!contadiniEffettivi.contains(iq.getCybercontadino()))
				contadiniEffettivi.add(iq.getCybercontadino());
		}
		return contadiniEffettivi;
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
		ordine.setDriver(responsabileConsegna);
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
	
	@BeginTask @EndTask(beforeRedirect=true,transition="ordine_eliminato_dall_utente")
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
	
	@BeginTask @EndTask(beforeRedirect=true,transition="notifica_inviata_ordine_non_preso_in_carico_termina_processo")
	public String notificaOrdineNonPresoDataMassimaScaduta() {
		float prezzoOrdine = 0;
		List<ItemQuantita> items = myOrdine.getItemQuantita();
		for (ItemQuantita iq: items)
			prezzoOrdine+=iq.getPrezzoTotale();
		gestioneFondo.plusFondo(prezzoOrdine);
		return "deletedTimeOut";
	}

/*	public void removeFrom(String transition, Cybercontadino contadino)
	{
		if(transition.equals("fb_customer_to_contadino"))
			feedbackListCustomerToContadini.remove(contadino);
		if(transition.equals("fb_contadino_to_responsabile_consegna"))
			feedbackListContadiniToDriver.remove(contadino.getPartitaIva());
		if(transition.equals("fb_responsabile_consegna_to_contadino"))
			feedbackListDriverToContadini.remove(contadino);

	}
*/
	@BeginTask @EndTask(beforeRedirect=true,transition="fb_customer_to_contadino")
	public String fb_customer_to_contadino() {
		//list...
		return "fb_customer_to_contadino";
	}
	
	@BeginTask @EndTask(beforeRedirect=true,transition="fb_customer_to_responsabile_consegna")
	public String fb_customer_to_responsabile_consegna() {
		//single
		return "fb_customer_to_responsabile_consegna";
	}
	
	@BeginTask @EndTask(beforeRedirect=true,transition="fb_responsabile_to_customer")
	public String fb_responsabile_to_customer() {
		//single
		return "fb_responsabile_to_customer";
	}
	
	@BeginTask @EndTask(beforeRedirect=true,transition="fb_contadino_to_responsabile_consegna")
	public String fb_contadino_to_responsabile_consegna() {
		//list..
		return "fb_contadino_to_responsabile_consegna";
	}
	
	@BeginTask @EndTask(beforeRedirect=true,transition="fb_responsabile_consegna_to_contadino")
	public String fb_responsabile_consegna_to_contadino() {
		//list..
		return "fb_responsabile_consegna_to_contadino";
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
