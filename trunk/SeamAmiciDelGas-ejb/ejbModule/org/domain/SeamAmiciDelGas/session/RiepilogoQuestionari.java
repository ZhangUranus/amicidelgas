package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.List;
import org.domain.SeamAmiciDelGas.crud.QuestionarioList;
import org.domain.SeamAmiciDelGas.entity.Questionario;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.ScopeType;

@Name(value="newRiepilogoQuestionari")
@Scope(ScopeType.SESSION)
public class RiepilogoQuestionari {

	@Logger
	private Log log;	
	
	@In(value="questionarioList",create=true)
	private QuestionarioList questionarioList;
	
	public ArrayList<Integer> getVotoByVisitatore(String usernameVisitatore, String cybercontadino)
	{
		questionarioList.setEjbql("select questionario from Questionario questionario where questionario.account.username='"+usernameVisitatore+"' and questionario.cybercontadino.partitaIva='"+cybercontadino+"'");
		List<Questionario> lu = questionarioList.getResultList();
	
		ArrayList<Integer> voti = new ArrayList<Integer>();
		if(lu.isEmpty())
		{
			voti.add(0);
			voti.add(0);
			voti.add(0);
			voti.add(0);
			voti.add(0);
			return voti;
		} else {
			voti.add(lu.iterator().next().getVotoAllevamento());
			voti.add(lu.iterator().next().getVotoIgiene());
			voti.add(lu.iterator().next().getVotoProdotti());
			voti.add(lu.iterator().next().getVotoProfessionalita());
			voti.add(lu.iterator().next().getVotoStabile());
			return voti;
		}
			
	}

}
