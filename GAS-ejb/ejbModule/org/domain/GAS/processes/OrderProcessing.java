package org.domain.GAS.processes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.domain.GAS.catalog.CatalogImpl;
import org.domain.GAS.catalog.CatalogInterface;
import org.domain.GAS.catalog.CatalogNoServiceImpl;
import org.domain.GAS.crud.AccountHome;
import org.domain.GAS.crud.OrdineList;
import org.domain.GAS.entity.Account;
import org.domain.GAS.entity.Articolo;
import org.domain.GAS.entity.Cybercontadino;
import org.domain.GAS.entity.Itinerario;
import org.domain.GAS.entity.Ordine;
import org.domain.GAS.entity.PuntiDiConsegna;
import org.domain.GAS.session.GestioneFeedback;
import org.domain.GAS.session.GestioneFondo;
import org.domain.GAS.session.InfoFeedback;
import org.domain.GAS.session.ItemQuantita;
import org.domain.GAS.session.LoginSelectBean;
import org.domain.GAS.session.Message;
import org.domain.GAS.session.MyOrdine;
import org.domain.GAS.session.OrdineBean;
import org.domain.GAS.session.TakeInHandForCustomer;
import org.domain.GAS.session.TakeInHandForDriver;
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
	
	@In(value="ordine", scope=ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="ordine",scope=ScopeType.BUSINESS_PROCESS,required=false)
	private Ordine ordine;
	
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
	
	@In(value="itinerario", scope=ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="itinerario", scope=ScopeType.BUSINESS_PROCESS, required=false)
	private Itinerario itinerario;
	
	@In(value="responsabileConsegna", scope=ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="responsabileConsegna", scope=ScopeType.BUSINESS_PROCESS, required=false)
	private Account responsabileConsegna;
	
	@In(value="dataConsegna", scope=ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="dataConsegna", scope=ScopeType.BUSINESS_PROCESS, required=false)
	private Date dataConsegna;
	
	@Out(value="selectedItemShoppingCart",scope=ScopeType.BUSINESS_PROCESS,required=false)
	private List<ItemQuantita> selectedItem;
	
	@Out(value="dataMassimaShoppingCart",scope=ScopeType.BUSINESS_PROCESS,required=false)
	@In(value="dataMassimaShoppingCart",scope=ScopeType.BUSINESS_PROCESS,required=false)
	private Date dataMassima;

	@Out(value="errorData",scope=ScopeType.BUSINESS_PROCESS,required=false)
	private String errorData;
	
	@In private Credentials credentials;
	
	@In(value="gestioneFondo", create=true)
	private GestioneFondo gestioneFondo;
	
	@In(value="gestioneFeedback", create=true)
	@Out(value="gestioneFeedback",scope=ScopeType.BUSINESS_PROCESS,required=false)
	private GestioneFeedback gestioneFeedback;
	
	@In(value="booleanCustomerToContadino", scope=ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="booleanCustomerToContadino", scope=ScopeType.BUSINESS_PROCESS, required=false)
	private Boolean booleanCustomerToContadino;
	
	@In(value="booleanCustomerToResponsabileConsegna", scope=ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="booleanCustomerToResponsabileConsegna", scope=ScopeType.BUSINESS_PROCESS, required=false)
	private Boolean booleanCustomerToResponsabileConsegna;
	
	@In(value="booleanResponsabileConsegnaToContadino", scope=ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="booleanResponsabileConsegnaToContadino", scope=ScopeType.BUSINESS_PROCESS, required=false)
	private Boolean booleanResponsabileConsegnaToContadino;
	
	@In(value="booleanResponsabileConsegnaToCustomer", scope=ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="booleanResponsabileConsegnaToCustomer", scope=ScopeType.BUSINESS_PROCESS, required=false)
	private Boolean booleanResponsabileConsegnaToCustomer;
	
	@In(value="responsabileIsDriver", scope=ScopeType.BUSINESS_PROCESS,required=false)
	@Out(value="responsabileIsDriver", scope=ScopeType.BUSINESS_PROCESS,required=false)
	private Boolean responsabileIsDriver = new Boolean(false);
	
	//feedback dei contadini al driver
	@In(value="booleanFeedbackContadiniToResponsabile", scope=ScopeType.BUSINESS_PROCESS,required=false)
	@Out(value="booleanFeedbackContadiniToResponsabile", scope=ScopeType.BUSINESS_PROCESS,required=false)
	private Hashtable<String, Boolean> booleanFeedbackContadiniToResponsabile;
	
	@In(value="loginSelectBean", scope=ScopeType.SESSION, required=false)
	private LoginSelectBean loginSelectBean;
	
	@CreateProcess(definition="myOrderProcessing")
	public String startOrder(List<ItemQuantita> itemQ, Date dm){
		selectedItem = itemQ;
		dataMassima = dm;
		customer = currentAccount;
		 
		myOrdine = MyOrdine.createMyOrder(itemQ, dm);
		myOrdine.setItemQuantita(itemQ);
		dataRichiesta = myOrdine.getDataRichiesta();
		
		boolean isStessoContadino=true;
		messageDriverContadino = new Message();
		
		String usernameContadino = selectedItem.get(0).getCybercontadino().getAccount().getUsername();
		for(ItemQuantita iq : selectedItem)//vedo se il contadino � sempre lo stesso
			if(!(iq.getCybercontadino().getAccount().getUsername().equals(usernameContadino)))
			{	
				isStessoContadino=false; break;
			}
		
		if(isStessoContadino)
			messageDriverContadino.addRecipient(usernameContadino);
		String content = "RICHIESTA ORDINE Customer: " +credentials.getUsername();
		messageDriverContadino.setContent(content);
		
		messageDataMassimaScaduta = new Message();
		messageDataMassimaScaduta.setMittente("Sistema GAS");
		messageDataMassimaScaduta.addRecipient(customer.getUsername());
		messageDataMassimaScaduta.setContent("Ordine non evaso entro data massima");		
		
		return "partito";
	}
	
	@BeginTask @EndTask(transition="ordine_preso_in_carico")
	public String verificaDisponibilita(Itinerario itinerario, PuntiDiConsegna punto){
		
		//faccio il controllo sulla data di consegna
		errorData = null;
		Date dataConsegnaTemp = itinerario.getDataConsegna();
		if (dataConsegnaTemp.after(this.dataMassima)) {
			errorData = "Data inserita successiva a quella massima";
			return null;
		}
		
		Hashtable<String,String> transactionIdList = new Hashtable<String,String>();
		boolean isAvailable=true;
		int[] quantitaOttenute = new int[myOrdine.getItemQuantita().size()];
		//verifico la disponibilit� per ogni contandino
		int i = 0;
		for (ItemQuantita iq : myOrdine.getItemQuantita()) {
			String partitaIva= iq.getCybercontadino().getPartitaIva();
			CatalogInterface catalog = CatalogImpl.getInstanceForContadino(partitaIva);
			
			String uuid = transactionIdList.get(partitaIva);
			if(uuid==null)
			{	
				uuid = catalog.beginTransaction(myOrdine.getDataMassima());
				transactionIdList.put(partitaIva, uuid);
			}
			quantitaOttenute[i]=catalog.reserveItem(uuid, iq.getItem(),iq.getQuantitaParziale(), iq.getQuantita());
			if (quantitaOttenute[i]==0) {
				isAvailable = false;
				break;
			}
			i++;
		}
		messageStatoOrdine =new Message();
		messageStatoOrdine.setMittente(credentials.getUsername());
		messageStatoOrdine.addRecipient(customer.getUsername());
		if(!isAvailable) //uno degli itemquantita non � disponibile
		{
			Enumeration<String> enumContadini = transactionIdList.keys();
			while(enumContadini.hasMoreElements())
			{
				String partitaIva = enumContadini.nextElement();
				CatalogInterface catalog= CatalogImpl.getInstanceForContadino(partitaIva);
				catalog.rollBackTransaction(transactionIdList.get(partitaIva));
			}
			messageStatoOrdine.setContent("Ordine non fattibile, e' stato rimesso in coda");
			messageStatoOrdine.setInfoFilter("orderProcessingNonPreso");
		}
		else //l'ordine pu� essere evaso
		{
			Enumeration<String> enumContadini = transactionIdList.keys();
			while(enumContadini.hasMoreElements())
			{
				String partitaIva = enumContadini.nextElement();
				CatalogInterface catalog= CatalogImpl.getInstanceForContadino(partitaIva);
				catalog.commitTransaction(transactionIdList.get(partitaIva));
			}
			messageStatoOrdine.setInfoFilter("orderProcessingPreso");
			responsabileConsegna = currentAccount;
			this.itinerario = itinerario;

			if (loginSelectBean.isDriver()) {
				responsabileIsDriver = new Boolean(true);
				this.itinerario.getCybercontadinos();
				this.itinerario.getPuntiDiConsegnas();
			}
			else {
				responsabileIsDriver = new Boolean(false);
				Set<PuntiDiConsegna> puntiDiConsegnas = new HashSet<PuntiDiConsegna>(0);
				puntiDiConsegnas.add(punto);
				itinerario.setPuntiDiConsegnas(puntiDiConsegnas);
			}
			this.dataConsegna = itinerario.getDataConsegna();
			myOrdine.setPendente(false);
			myOrdine.setEvaso(true);
			this.setFeedbackVariable();
			saveOrdine(quantitaOttenute); //salvo l'ordine nel database
			messageStatoOrdine.setContent("L'ordine "+ordine.getIdordine()+" e' stato preso in carico da "+ credentials.getUsername()+".");
		}

		return "ordine_preso_in_carico";
	}
	
	private void setFeedbackVariable() {
		List<String> contadiniEffettivi = new ArrayList<String>();
		booleanFeedbackContadiniToResponsabile = new Hashtable<String, Boolean>();
		List<ItemQuantita> items = myOrdine.getItemQuantita();
		for(ItemQuantita iq: items) {
			String username = iq.getCybercontadino().getAccount().getUsername();
			if (!contadiniEffettivi.contains(username)) {
				contadiniEffettivi.add(username);
				booleanFeedbackContadiniToResponsabile.put(username, new Boolean(false));
			}
		}
		booleanResponsabileConsegnaToContadino = new Boolean(false);
		booleanResponsabileConsegnaToCustomer = new Boolean(false);
		booleanCustomerToResponsabileConsegna = new Boolean(false);
		booleanCustomerToContadino = new Boolean(false);
		
	}
	
	@Transactional
	private void saveOrdine(int[] quantitaOttenute) {
		ordine = new Ordine();
		ordine.setAccount(customer);
		ordine.setConcluso(false);
		ordine.setDataRichiesta(dataRichiesta);
		ordine.setDataConclusione(dataConsegna);
		ordine.setDataMassimaConsegna(myOrdine.getDataMassima());
		ordine.setDriver(responsabileConsegna);
		//salvo l'ordine
		em.persist(ordine);
		
		Articolo articolo;
		Set<Articolo> articoli = new HashSet<Articolo>(0);
		int i = 0;
		for (ItemQuantita iq: myOrdine.getItemQuantita()) {
			articolo = new Articolo();
			articolo.setCodiceEsterno(iq.getItem().getName());
			articolo.setCybercontadino(iq.getCybercontadino());
			articolo.setQuantitaOttenuta(quantitaOttenute[i]);
			i++;
			articolo.setDescrizione(iq.getItem().getDescription());
			articolo.setPrezzo((float) iq.getItem().getPrezzo());
			if (!iq.isBooleanIsQuantitaMinima())
				iq.setQuantitaParziale(iq.getQuantita());
			articolo.setQuantitaMinRichiesta(iq.getQuantitaParziale());
			articolo.setQuantitaRichiesta(iq.getQuantita());
			articolo.setOrdine(ordine);
			em.persist(articolo);
			articoli.add(articolo);
		}
		ordine.setArticolos(articoli);
		
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
	public String notificaOrdineNonDisponibile() {
		return "deleted";
	}
	
	
	
	@BeginTask @EndTask(transition="attendi_consegna")
	public String attendi_consegna() {
		return "deleted";
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

	@BeginTask @EndTask(beforeRedirect=true,transition="fb_customer_to_contadino")
	public String fb_customer_to_contadino(TakeInHandForCustomer takeInHandForCustomer) {
		booleanCustomerToContadino = new Boolean(true);
		Hashtable<String, InfoFeedback> hashTableContadini = takeInHandForCustomer.getHashTable();
		Enumeration<String> enum1 = hashTableContadini.keys();
		while(enum1.hasMoreElements()) {
			String username = enum1.nextElement();
			InfoFeedback infoFeedback = hashTableContadini.get(username);
			gestioneFeedback.assegnaFeedback(username, ordine, (float) infoFeedback.getFeedback(), infoFeedback.getComment());
		}
		
		takeInHandForCustomer.reset();
		return "fb_customer_to_contadino";
	}
	
	@BeginTask @EndTask(beforeRedirect=true,transition="fb_customer_to_responsabile_consegna")
	public String fb_customer_to_responsabile_consegna(TakeInHandForCustomer  takeInHandForCustomer ) {
		//single
		booleanCustomerToResponsabileConsegna = new Boolean(true);
		InfoFeedback infoFeedback = takeInHandForCustomer.getInfoFeedbackResponsabile();
		gestioneFeedback.assegnaFeedback(ordine.getDriver().getUsername(), ordine, (float) infoFeedback.getFeedback(), infoFeedback.getComment());
		takeInHandForCustomer .reset();
		return "fb_customer_to_responsabile_consegna";
	}
	
	@BeginTask @EndTask(beforeRedirect=true,transition="fb_responsabile_to_customer")
	public String fb_responsabile_to_customer(TakeInHandForDriver takeInHandForDriver) {

		return "fb_responsabile_to_customer";
	}
	
	@BeginTask @EndTask(beforeRedirect=true,transition="fb_contadino_to_responsabile_consegna")
	public String fb_contadino_to_responsabile_consegna() {
		//list..
		return "fb_contadino_to_responsabile_consegna";
	}
	
	@BeginTask @EndTask(beforeRedirect=true,transition="fb_responsabile_consegna_to_contadino")
	public String fb_responsabile_consegna_to_contadino(TakeInHandForDriver takeInHandForDriver) {
		//list..
		booleanResponsabileConsegnaToContadino = new Boolean(true);
		Hashtable<String, InfoFeedback> hashTableContadini = takeInHandForDriver.getHashTableContadini();
		Enumeration<String> enum1 = hashTableContadini.keys();
		while(enum1.hasMoreElements()) {
			String username = enum1.nextElement();
			InfoFeedback infoFeedback = hashTableContadini.get(username);
			gestioneFeedback.assegnaFeedback(username, ordine, (float) infoFeedback.getFeedback(), infoFeedback.getComment());
		}
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