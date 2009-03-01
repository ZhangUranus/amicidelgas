package org.domain.SeamAmiciDelGas.processes;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.EntityManager;

import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.entity.Role;
import org.domain.SeamAmiciDelGas.session.Message;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.bpm.BeginTask;
import org.jboss.seam.annotations.bpm.CreateProcess;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jbpm.taskmgmt.exe.TaskInstance;


@Name("processoRegistrazione")
@Scope(ScopeType.SESSION)
public class ProcessoRegistrazione {
	
	@Logger
	private Log log;
	@In private Credentials credentials;
	
	@In protected EntityManager entityManager;
	@Out(value="notificaMediatore", scope= ScopeType.BUSINESS_PROCESS, required=false)protected Message message;
	private String msg="";
	
	
	@In private FacesMessages facesMessages;
	
	@Out(value="nomeContadino",scope=ScopeType.BUSINESS_PROCESS, required=false)
	private String nomeContadino;
	
	
	@Out(value="dataProposta", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Date dataProposta;
	
	@Out(value="dataMassimaAccettazione", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Date dataMassimaAccettazione;
	
	@Out(value="postiOccupati",scope=ScopeType.BUSINESS_PROCESS, required= false)
	private int postiOccupati;
	
	private Cybercontadino contadinoCorrente;
	private TaskInstance taskCorrente;
	
	@CreateProcess(definition="notificaRegistrazione")
	public void inviaRegistrazione(){
		//log.info("E' arrivata la richiesta driver");
		nomeContadino= credentials.getUsername();
		
		facesMessages.add("La richiesta ï¿½ stata inoltrata");
	}
	
	
	@BeginTask @EndTask
	public void creaVisita(){
		Calendar gc= new GregorianCalendar();
		gc.setTime(dataProposta);
		gc.roll(Calendar.DATE, -2);
		dataMassimaAccettazione= gc.getTime();
		postiOccupati=0;
		log.info(("Stampa della data proposta: "+this.dataProposta));
	}
	
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
		String approveMsg="La tua richiesta di divenire driver ï¿½ stata accettata.";
		if(msg!=null)
			approveMsg+="Il responsabile ha incluso il seguente messaggio:\n\""+msg+"\"";
		message.setContent(approveMsg);
		message.setRecipient(nomeRichiedente);
		facesMessages.add("L'utente ï¿½ stato reso driver");
		
	}
	
	@BeginTask @EndTask(transition="rifiuta")
	public void reject(){
		String nomeRichiedente=(String) Component.getInstance("nomeRichiedente", ScopeType.BUSINESS_PROCESS);
		message= new Message();
		String rejectMsg="La tua richiesta di divenire driver ï¿½ stata rifiutata.";
		if(msg!=null)
			rejectMsg+="Il responsabile ha incluso il seguente messaggio:\n\""+msg+"\"";
		message.setContent(rejectMsg);
		message.setRecipient(nomeRichiedente);
		
		facesMessages.add("La richiesta dell'utente ï¿½ stata rifiutata");
	}
	
	@BypassInterceptors
	public String getMsg() {
		return msg;
	}
	@BypassInterceptors
	public void setMsg(String msg) {
		this.msg = msg;
	}


	public Cybercontadino getContadinoCorrente() {
		return contadinoCorrente;
	}


	public void setContadinoCorrente(Cybercontadino contadinoCorrente) {
		this.contadinoCorrente = contadinoCorrente;
	}


	public Date getDataProposta() {
		return dataProposta;
	}


	public void setDataProposta(Date dataProposta) {
		this.dataProposta = dataProposta;
	}


	public TaskInstance getTaskCorrente() {
		return taskCorrente;
	}


	public void setTaskCorrente(TaskInstance taskCorrente) {
		this.taskCorrente = taskCorrente;
	}
	

	public void update(){
		log.info("Update- la data corrente è: "+ dataProposta);
	}
	
}
