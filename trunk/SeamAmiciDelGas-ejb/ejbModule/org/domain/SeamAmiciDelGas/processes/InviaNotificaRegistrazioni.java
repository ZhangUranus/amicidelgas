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
import org.jboss.seam.annotations.bpm.StartTask;

import org.jboss.seam.annotations.bpm.EndTask;

import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;



@Name("inviaNotificaRegistrazioni")
@Scope(ScopeType.SESSION)
public class InviaNotificaRegistrazioni {
	
	@Logger
	private Log log;
	@In private Credentials credentials;
	
	@In protected EntityManager entityManager;
	
	private String msg="";
	
	@In (value="notificaUtente" ,scope= ScopeType.BUSINESS_PROCESS, required=false)
//	@Out(value="notificaUtente", scope= ScopeType.BUSINESS_PROCESS, required=false)
	protected Message messageUtente;
	
	@In private FacesMessages facesMessages;
	
	@In(value="inviati",scope=ScopeType.BUSINESS_PROCESS, required= false)
	private ArrayList<String> usernameInviati;
	
	@In(value="postiOccupati" , scope= ScopeType.BUSINESS_PROCESS, required =false)
	@Out(value="postiOccupati", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private int postiOccupati;
	
	@In(value="dataProposta", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Date dataProposta;
	/*
	@Out(value="dataProposta", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Date dataProposta;
	
	@Out(value="dataQuestionario", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Date dataQuestionario ;
	
	@Out(value="dataMassimaAccettazione", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Date dataMassimaAccettazione;
	
	@Out(value="postiOccupati",scope=ScopeType.BUSINESS_PROCESS, required= false)
	private int postiOccupati;
	
	
	@In(value="inviati",scope=ScopeType.BUSINESS_PROCESS, required= false)
	private ArrayList<String> usernameInviati;
	
	private Cybercontadino contadinoCorrente;
	private TaskInstance taskCorrente;
	*/
	
	
	
	@StartTask @EndTask(transition="partecipa")
	public void partecipa_alla_visita()
	{
		if(usernameInviati == null)
		{
			usernameInviati = new ArrayList<String>();
		}
		usernameInviati.add(credentials.getUsername());
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa"+usernameInviati.toString());
		postiOccupati++;
		System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBb "+postiOccupati);
		/*
		Calendar gc= new GregorianCalendar();
		gc.setTime((Date) dataProposta.clone());
		gc.roll(Calendar.DATE, -2);
		dataMassimaAccettazione= gc.getTime();
		*/
		messageUtente= new Message();
		String rejectMsg="Andiamo tutti dal cybercontadino.";
		messageUtente.setContent(rejectMsg);
		
	}
	
	@StartTask @EndTask(transition="compila")
	public void compilaQuestionario()
	{
		log.info("Stampa qualcosaa: ");
		
	}
	
	


}
