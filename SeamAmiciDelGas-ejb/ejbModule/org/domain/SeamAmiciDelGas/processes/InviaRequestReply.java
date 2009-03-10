package org.domain.SeamAmiciDelGas.processes;

import org.domain.SeamAmiciDelGas.session.Message;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.bpm.StartTask;
import org.jboss.seam.annotations.bpm.EndTask;


@Name("inviaRequestReply")
public class InviaRequestReply {
	
	@In(value="notifyMessageRequest",scope=ScopeType.BUSINESS_PROCESS, required=false) 
	@Out(value="notifyMessageReply", scope= ScopeType.BUSINESS_PROCESS, required=false)
	private Message message;
	
	@In(value="nomeMittente",scope=ScopeType.BUSINESS_PROCESS, required=false)
	private String nomeMittente;
	
	
	@StartTask @EndTask(transition="inviaReply")
	public void riceviMessaggio()
	{
		System.out.println();
		message.setContent("Seam Amici Del Gas e non ce ne tene e fa nu Gas");
		message.setDestinatario(nomeMittente);
	}
	
	@StartTask @EndTask(transition="ack")
	public void riceviRisposta()
	{
		
	}
	
	
	


	public Message getMessage() {
		return message;
	}


	public void setMessage(Message message) {
		this.message = message;
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