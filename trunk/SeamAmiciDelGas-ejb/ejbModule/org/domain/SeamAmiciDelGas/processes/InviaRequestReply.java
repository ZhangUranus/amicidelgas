package org.domain.SeamAmiciDelGas.processes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.domain.SeamAmiciDelGas.crud.FeedbackListExtended;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.entity.Feedback;
import org.domain.SeamAmiciDelGas.entity.Questionario;
import org.domain.SeamAmiciDelGas.session.Message;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.bpm.StartTask;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.log.Log;

@Name("inviaRequestReply")
public class InviaRequestReply {
		
	@Logger
	private Log log;
	
	@In(value="entityManager")
    private EntityManager em;
	
	@In(value="currentAccount" , scope=ScopeType.SESSION , required=false)
	private Account account;
	
	@In(value="dataVisita", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Date dataVisita;
	
    @In(value="contadino", scope= ScopeType.BUSINESS_PROCESS ,required =false)
    private Cybercontadino contadino;
    
    @In(value="nomeDestinatario", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private String nomeUtente;
	
	@In(value="notifyMessageRequest",scope=ScopeType.BUSINESS_PROCESS, required=false) 
	@Out(value="notifyMessageReply", scope= ScopeType.BUSINESS_PROCESS, required=false)
	private Message message;
	
	@In(value="compilato", scope= ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="compilato", scope= ScopeType.BUSINESS_PROCESS, required=false)
	private boolean compilato;
	
	private Date dataCorrente;
	
	@In(value="newQuestionario" , create=true)
	private Questionario questionario;
	
	@In(value="newFeedback" , create=true)
	private Feedback feedback;
	
	@In(value="newFeedbackListExtended",create=true)
	private FeedbackListExtended feedbackList;
	
	@StartTask @EndTask(transition="inviaReply")
	public String riceviMessaggio()
	{
		System.out.println("RICEVI MESSAGGIO");
		
		compilato = true;
		if(questionario == null)
		{
			System.out.println("QUESTIONARIO NULLOOOOOOOOOOOOOOO");
			//message.setContent("Messaggio generato dal sistema in seguito alla mancata compilazione del questionario da parte dell'utente" +
			//		" nel tempo prestabilito");
		}
		else
		{
			boolean ret = this.registraQuestionario();
			if(ret)
			{
				message.setContent("Ho compilato il questionario in data "+dataCorrente+" in seguito alla visita nell'azienda" +
						 " " + contadino.getNomeAzienda() +" effettuata il giorno "+dataVisita);
				System.out.println("NOMEEEEEEEEEEEEEEEe:"+message.getDestinatario());
			}
			else
			{
				System.out.println("ERRORE CRITICO");
				message.setContent("ERRORE CRITICO NEL DATABASE in data:"+dataCorrente);
			}
		}
		
		System.out.println("NOMEEEEEEEEEEEEEEEe:"+message.getDestinatario());
		return "OutQuestionario";
			
	}
	
	@Transactional public boolean registraQuestionario()
    {
		dataCorrente = new Date(System.currentTimeMillis());
		float somma = (questionario.getVotoAllevamento()+questionario.getVotoIgiene()+questionario.getVotoProdotti()+questionario.getVotoProfessionalita()+questionario.getVotoStabile())/5;
		questionario.setAccount(account);
		questionario.setCybercontadino(contadino);
		questionario.setDataCompilazione(dataCorrente);
		questionario.setDataVisita(dataVisita);
		questionario.setVisionato(false);
		questionario.setVotoGlobale(somma);
		em.persist(questionario);
		return true; 
		
    }
	
	@StartTask @EndTask(transition="fine")
	public void riceviRisposta()
	{
		System.out.println("OKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
		if(!compilato)
			this.inserisciFeedbackNegativo();
	}
	
	@Transactional public boolean inserisciFeedbackNegativo()
    {
		dataCorrente = new Date(System.currentTimeMillis());
		Account accountUtente = (Account) em.createQuery(
				"select account from Account account " +
				"where account.username = "+nomeUtente)
				.getSingleResult();
		feedback.setAccountBySegnalatore(account);
		feedback.setAccountByValidatore(account);
		feedback.setAccountByDestinatario(accountUtente);
		feedback.setDataSegnalazione(dataCorrente);
		feedback.setDataValidazione(dataCorrente);
		feedback.setDescrizione("Feedback Negativo inserito perchè l'utente non " +
				"ha compilato il questionario in tempo per l'azienda "+contadino.getNomeAzienda()+" in seguito alla visita" +
						" in data "+dataVisita);
		feedback.setAnalizzato(true);
		feedback.setPunteggio(2.0f);
		float punteggioCorrente = this.calcolaPunteggioFeedback(2.0f);
		accountUtente.setPunteggioFeedback(punteggioCorrente);
		em.persist(accountUtente);
		em.persist(feedback);
		
		return true; 
		
    }
	
	private float calcolaPunteggioFeedback(float punteggioNuovo)
	{
		List<Feedback> listaFeedback = feedbackList.getPunteggioFeedback(nomeUtente);
		ArrayList<Float> listaFloat = new ArrayList<Float>();
		for (Feedback feedback : listaFeedback) {
			listaFloat.add(feedback.getPunteggio());
		}
		int size = listaFloat.size();
		float somma = 0.0f;
		for (Float float1 : listaFloat) {
			somma += float1.floatValue();
		}
		somma += punteggioNuovo;
		return somma/size;
	}

	public Message getMessage() {
		return message;
	}


	public void setMessage(Message message) {
		this.message = message;
	}
	
	public void update(){
		log.info("QESTIONARIO COMPILATO"+ questionario.toString());
	}

	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}

	public Questionario getQuestionario() {
		return questionario;
	}
}

/*
@In protected FacesMessages facesMessages;
	@In protected EntityManager entityManager;
	@Out(value="notificaDecisioneDriver", scope= ScopeType.BUSINESS_PROCESS, required=false)
	protected Message message;
	private String msg="";
	
	
	@BeginTask @EndTask(transition="approva")
	public void approve(){
		String nomeRichiedente=(String) Component.getInstance("nomeRichiedente", ScopeType.BUSINESS_PROCESS);
		
		Account account = (Account) entityManager.createQuery(
				"select account from Account account " +
				"where account.username = #{nomeRichiedente}")
				.getSingleResult();
		Role r= new Role();
		r.setName("driver");
		r.setAccount(account);
		entityManager.persist(r);
		message= new Message();
		String approveMsg="La tua richiesta di divenire driver � stata accettata.";
		if(msg!=null)
			approveMsg+="Il responsabile ha incluso il seguente messaggio:\n\""+msg+"\"";
		message.setContent(approveMsg);
		message.addRecipient(nomeRichiedente);
		facesMessages.add("L'utente � stato reso driver");
	}
	
	@BeginTask @EndTask(transition="rifiuta")
	public void reject(){
		String nomeRichiedente=(String) Component.getInstance("nomeRichiedente", ScopeType.BUSINESS_PROCESS);
		message= new Message();
		String rejectMsg="La tua richiesta di divenire driver � stata rifiutata.";
		if(msg!=null)
			rejectMsg+="Il responsabile ha incluso il seguente messaggio:\n\""+msg+"\"";
		message.setContent(rejectMsg);
		message.addRecipient(nomeRichiedente);
		
		facesMessages.add("La richiesta dell'utente � stata rifiutata");
	}
	
	@BypassInterceptors
	public String getMsg() {
		return msg;
	}
	@BypassInterceptors
	public void setMsg(String msg) {
		this.msg = msg;
	}
*/