package org.domain.SeamAmiciDelGas.processes;

import java.util.Date;
import javax.persistence.EntityManager;
import org.domain.SeamAmiciDelGas.crud.AccountHome;
import org.domain.SeamAmiciDelGas.crud.FeedbackListExtended;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.entity.Feedback;
import org.domain.SeamAmiciDelGas.entity.Questionario;
import org.domain.SeamAmiciDelGas.entity.Role;
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
	
	@In(value="nomeMittente", scope= ScopeType.BUSINESS_PROCESS, required=false)
	private String nomeMittente;
	
	@In(value="compilato", scope= ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="compilato", scope= ScopeType.BUSINESS_PROCESS, required=false)
	private boolean compilato;
	
	private Date dataCorrente;
	
	@In(value="accountHome", create=true)
	private AccountHome accounthome;
	
	@In(value="newQuestionario", required=false)
	private Questionario questionario;
	
	@In(value="newFeedback" , create=true)
	private Feedback feedback;
	
	@In(value="newFeedbackListExtended",create=true)
	private FeedbackListExtended feedbackList;
	
	@StartTask @EndTask(transition="inviaReply")
	public String riceviMessaggio(int risposta)
	{
		//System.out.println("RICEVI MESSAGGIO");
		if(compilato)
		{
			System.out.println("BECOMEDRIVER"+risposta);
			this.setDriver(risposta);
		}
		else
		{
			
			compilato = true;
			if(questionario == null)
			{
				System.out.println("QUESTIONARIO NULLOOOOOOOOOOOOOOO");
				return null;
			}
			else
			{
				boolean ret = this.registraQuestionario();
				if(ret)
				{
					message.setContent("Ho compilato il questionario in data "+dataCorrente+" in seguito alla visita nell'azienda" +
							 " " + contadino.getNomeAzienda() +" effettuata il giorno "+dataVisita);
				}else{
					
					message.setContent("ERRORE CRITICO NEL DATABASE in data:"+dataCorrente);
					return null;
				}
			}
		}
		return "OutQuestionario";
			
	}
	
	@Transactional public boolean registraQuestionario()
    {
		dataCorrente = new Date(System.currentTimeMillis());
		float somma = ((float)(questionario.getVotoAllevamento()+questionario.getVotoIgiene()+questionario.getVotoProdotti()+questionario.getVotoProfessionalita()+questionario.getVotoStabile()))/5.0f;
		questionario.setAccount(account);
		questionario.setCybercontadino(contadino);
		questionario.setDataCompilazione(dataCorrente);
		questionario.setDataVisita(dataVisita);
		questionario.setVisionato(false);
		questionario.setVotoGlobale(somma);
		em.persist(questionario);
		return true; 
		
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
	
	@Transactional public boolean setDriver(int i)
    {
		if(i==1)
		{
			System.out.println("DRIVERRRRRRRRRRRRRRRRRRRRRRRRRSIIIIIIIIIIIIIII");
			accounthome.setAccountUsername(nomeUtente);
			Account accountUtente = accounthome.find();
			Role r= new Role();
			r.setName("driver");
			r.setAccount(accountUtente);
			em.persist(r);
			message.setContent("La tua richiesta di divenire driver e' stata accettata.");
		}
		else
		{
			message.setContent("La tua richiesta di divenire driver � stata rifiutata.");
			
		}
		message.setDestinatario(nomeMittente);
		return true;
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