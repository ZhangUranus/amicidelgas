package org.domain.SeamAmiciDelGas.processes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.EntityManager;

import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.session.Message;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.bpm.BeginTask;
import org.jboss.seam.annotations.bpm.CreateProcess;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.annotations.bpm.StartTask;
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
	@Out(value="notificaMediatore", scope= ScopeType.BUSINESS_PROCESS, required=false)
	protected Message message;
	private String msg="";
	
	
	@Out(value="notificaUtente", scope= ScopeType.BUSINESS_PROCESS, required=false)
	protected Message messageUtente;
	
	@In private FacesMessages facesMessages;
	
	@Out(value="nomeContadino",scope=ScopeType.BUSINESS_PROCESS, required=false)
	private String nomeContadino;
	

	@Out(value="dataProposta", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Date dataProposta;
	
	@Out(value="dataQuestionario", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Date dataQuestionario ;
	
	@Out(value="dataMassimaAccettazione", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Date dataMassimaAccettazione;
	
	@Out(value="postiOccupati",scope=ScopeType.BUSINESS_PROCESS, required= false)
	private int postiOccupati;
	
	
	@Out(value="inviati",scope=ScopeType.BUSINESS_PROCESS, required= false)
	private ArrayList<String> usernameInviati;
	
	private Cybercontadino contadinoCorrente;
	private TaskInstance taskCorrente;
	
	@CreateProcess(definition="notificaRegistrazione")
	public void inviaRegistrazione()
	{
		//log.info("E' arrivata la richiesta driver");
		
		// Discutiamone con antonio probabilmente non serve a nulla.....
		nomeContadino= credentials.getUsername();
		facesMessages.add("La richiesta e' stata inoltrata");
	}
	
	
	@StartTask @EndTask(transition="invia")
	public void creaVisita()
	{
		Calendar gc= new GregorianCalendar();
		gc.setTime((Date) dataProposta.clone());
		gc.roll(Calendar.DATE, -1);
		dataMassimaAccettazione = gc.getTime();
		postiOccupati=0;
		
		gc= new GregorianCalendar();
		gc.setTime((Date) dataProposta.clone());
	//	gc.roll(Calendar.DATE, +1);
		gc.roll(Calendar.MINUTE, +1);
		gc.roll(Calendar.DATE, -1);
		dataQuestionario = gc.getTime();
		messageUtente= new Message();
		String rejectMsg="Andiamo tutti dal cybercontadino.";
		messageUtente.setContent(rejectMsg);
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
		log.info("Update- la data corrente �: "+ dataProposta);
	}
	
}
