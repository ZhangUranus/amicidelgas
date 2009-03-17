package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

import org.jboss.seam.ScopeType;
import org.jbpm.taskmgmt.exe.TaskInstance;

@Name(value="takeInHandContadino") 
@Scope(ScopeType.SESSION)
public class TakeInHandContadino {

	public TakeInHandContadino(){}
	
	@Logger
	private Log log;
	
	private Hashtable<String, InfoFeedback> hashTable = new Hashtable<String, InfoFeedback>();
	
	private TaskInstance currentTaskInstance; 
	
	public void reset() {
		hashTable = new Hashtable<String, InfoFeedback>();
	}
	
	public InfoFeedback getInfoFeedback(String username){
		if(username==null){
			InfoFeedback feed= new InfoFeedback();
			feed.setComment("");
			feed.setFeedback(3);
			hashTable.put(username, feed);
			return feed;
		}
		InfoFeedback feed = hashTable.get(username);
		if(feed==null){
			feed= new InfoFeedback();
			feed.setComment("");
			feed.setFeedback(3);
			hashTable.put(username, feed);
		}
		return feed;
	}
	
	public List<Cybercontadino> listCybercontadiniEffettivi(TaskInstance task) {
		List<Cybercontadino> contadiniEffettivi = new ArrayList<Cybercontadino>();
		if(task!=null)
		{
			MyOrdine myOrdine = (MyOrdine) task.getVariable("myOrdine");
			if(myOrdine!=null)
				for (ItemQuantita iq: myOrdine.getItemQuantita()) {
					Cybercontadino contadino = iq.getCybercontadino();
					boolean isPresent = false;
					for(Cybercontadino effettivo : contadiniEffettivi) {
						if(effettivo.getPartitaIva().equals(contadino.getPartitaIva()))
						{
							isPresent = true;
							break;
						}
					}
					if(!isPresent)
						contadiniEffettivi.add(contadino);
				}
		}
		return contadiniEffettivi;
	}
	
		public class InfoFeedback {
		
		public InfoFeedback() {}
		
		public InfoFeedback(String c, int f) {
			comment = c; feedback = f;
		}

		private String comment;
		private int feedback;
		
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		public int getFeedback() {
			return feedback;
		}
		public void setFeedback(int feedback) {
			this.feedback = feedback;
		}
	}

		public TaskInstance getCurrentTaskInstance() {
			return currentTaskInstance;
		}

		public void setCurrentTaskInstance(TaskInstance currentTaskInstance) {
			this.reset();
			this.currentTaskInstance = currentTaskInstance;
			log.info("\n\n******** SETTATOOOO " +currentTaskInstance.getName() +"*********");
			
		}

		public Hashtable<String, InfoFeedback> getHashTable() {
			return hashTable;
		}

}