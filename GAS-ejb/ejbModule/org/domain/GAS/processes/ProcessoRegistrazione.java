package org.domain.GAS.processes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.domain.GAS.entity.Cybercontadino;
import org.domain.GAS.session.Message;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.bpm.CreateProcess;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.annotations.bpm.StartTask;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jbpm.taskmgmt.exe.TaskInstance;

@Name("processoRegistrazione")
@Scope(ScopeType.EVENT)
public class ProcessoRegistrazione {
	
	@Logger
	private Log log;
	@In private Credentials credentials;
	
	@Out(value="notificaMediatore", scope= ScopeType.BUSINESS_PROCESS, required=false)
	private Message message;
	
	@Out(value="messageSubProcess", scope= ScopeType.BUSINESS_PROCESS, required=false)
	private Message messageSubProcess;
	
	private String content;
	
	private String mittente;

	@Out(value="notificaUtente", scope= ScopeType.BUSINESS_PROCESS, required=false)
	protected Message messageUtente;
	
	@In private FacesMessages facesMessages;
	
	@Out(value="MediatoreCheManda",scope=ScopeType.BUSINESS_PROCESS, required=false)
	private String MediatoreCheManda;

	@Out(value="dataProposta", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Date dataProposta;
	
	@Out(value="compilatoQuestionario", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Boolean compilatoQuestionario;
	
	@Out(value="dataQuestionario", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Date dataQuestionario ;
	
	@Out(value="dataTimer", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Date dataTimer ;
	
	@Out(value="dataMassimaAccettazione", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Date dataMassimaAccettazione;
	
	@Out(value="postiOccupati",scope=ScopeType.BUSINESS_PROCESS, required= false)
	private int postiOccupati;
	
	@Out(value="inviati",scope=ScopeType.BUSINESS_PROCESS, required= false)
	private List<String> usernameInviati;
		
	// contadino che serve per il panel della jsf (Clelio)
	private Cybercontadino contadinoCorrente;
	
	private TaskInstance taskCorrente;
	
	@CreateProcess(definition="notificaRegistrazione")
	public void inviaRegistrazione()
	{	
		facesMessages.add("La richiesta e' stata inoltrata");
	}	
	
	@StartTask @EndTask(transition="invia")
	public String creaVisita()
	{
		Calendar g= new GregorianCalendar();
		Date dataOra = g.getTime();
		if(dataProposta.before(dataOra))
			return null;
		else
		{
			Calendar gc= new GregorianCalendar();
			gc.setTime((Date) dataProposta.clone());
			gc.add(Calendar.MINUTE, +0);
			dataMassimaAccettazione = gc.getTime();
			postiOccupati=0;
			gc= new GregorianCalendar();
			gc.setTime((Date) dataProposta.clone());
			// dopo quanto tempo dalla visita si può iniziare a compilare il questionario
			gc.add(Calendar.MINUTE, +1);
			dataQuestionario = gc.getTime();
			gc.setTime((Date) dataProposta.clone());
			String format = "dd-MM-yyyy";
			messageUtente= new Message();
			messageUtente.setContent(content+"\n In data "+(new SimpleDateFormat(format)).format(gc.getTime())
					+" alle ore: "+gc.get(Calendar.HOUR_OF_DAY)+":"+gc.get(Calendar.MINUTE)+" .");
			messageUtente.setMittente(mittente);
			usernameInviati = new ArrayList<String>();
			messageSubProcess = new Message();
			messageSubProcess.setContent("Ora puoi compilare il questionario per dare il tuo parere "
					+"sull'azienda che abbiamo visitato, clikkando nella sezione \"Compila Questionario\"");
			messageSubProcess.setDestinatario(credentials.getUsername());
			messageSubProcess.setBroadcast(false);
			messageSubProcess.setMittente(mittente);
			messageSubProcess.setTipo("questionario");
			gc= new GregorianCalendar();
			gc.setTime((Date) dataQuestionario.clone());
			// dopo quanto tempo il questionario si cancella e non si può più compilarlo
			gc.add(Calendar.MINUTE, +5);
			dataTimer = gc.getTime();
			MediatoreCheManda = credentials.getUsername();
			compilatoQuestionario = false;
			return "ok";
		}
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
	
	public void update(){}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public String getMittente() {
		return mittente;
	}	
}