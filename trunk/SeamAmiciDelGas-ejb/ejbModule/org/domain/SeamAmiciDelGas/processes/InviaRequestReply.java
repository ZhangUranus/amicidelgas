package org.domain.SeamAmiciDelGas.processes;

import java.util.Date;
import javax.persistence.EntityManager;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
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
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.log.Log;
import org.jbpm.taskmgmt.exe.TaskInstance;

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
	private Boolean compilato;
	
	private Date dataCorrente;
	
	@In(value="newQuestionario")
	private Questionario questionario;


	@StartTask @EndTask(transition="inviaReply")
	public String riceviMessaggio()
	{
		System.out.println("RICEVI MESSAGGIO");
		
		compilato = true;
		System.out.println("COMPILATOOOOOOOOOOOOO"+compilato);
		if(questionario == null)
		{
			System.out.println("QUESTIONARIO NULLOOOOOOOOOOOOOOO");
			return null;
		}
		else
		{
			System.out.println("QUESTIONARIO aaaaaaaaaaa"+questionario.getVotoIgiene());
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
				return null;
			}
		}
		
		System.out.println("NOMEEEEEEEEEEe:"+message.getDestinatario());
		return "OutQuestionario";
			
	}
	
	@Transactional public boolean registraQuestionario()
    {
		dataCorrente = new Date(System.currentTimeMillis());
		System.out.println("ALLEVAMENTO1 "+questionario.getVotoIgiene());
		System.out.println("ALLEVAMENTO2 "+questionario.getVotoAllevamento());
		System.out.println("ALLEVAMENTO3 "+questionario.getVotoProdotti());
		System.out.println("ALLEVAMENTO4 "+questionario.getVotoProfessionalita());
		System.out.println("ALLEVAMENTO5 "+questionario.getVotoStabile());
		System.out.println("ALLEVAMENTO6 "+questionario.getCommenti());
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
	
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
	public void update(){
		log.info("QESTIONARIO COMPILATO"+ questionario.toString());
	}
}