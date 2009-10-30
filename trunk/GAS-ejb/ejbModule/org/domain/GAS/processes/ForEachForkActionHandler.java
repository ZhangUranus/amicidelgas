package org.domain.GAS.processes;


import java.util.LinkedList;
import java.util.List;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

public class ForEachForkActionHandler implements ActionHandler{

	protected static final String FOREACH_PREFIX = "foreach.";
	private static final long serialVersionUID = 852846064870422941L;
	
	public void execute(final ExecutionContext executionContext) throws Exception 
	{
		final Token rootToken = executionContext.getToken();
        final Node node = executionContext.getNode();
        final List argSets = new LinkedList();
        List<String> gruppoInviati = (List<String>) executionContext.getVariable("inviati");
        if(gruppoInviati == null)
        	return;
        for (int i = 0; i < node.getLeavingTransitions().size(); i++) {
            final Transition transition = (Transition) node.getLeavingTransitions().get(0);

            for (int j = 0; j < gruppoInviati.size(); j++) 
            {
                final Token newToken = new Token(rootToken, FOREACH_PREFIX + node.getId() + "." + j);
                newToken.setTerminationImplicit(true);
                executionContext.getJbpmContext().getSession().save(newToken);   
                final ExecutionContext newExecutionContext = new ExecutionContext( newToken );
                //associa la variabile al token
                newExecutionContext.getContextInstance().createVariable( "inviato", gruppoInviati.get(j), newToken);
                argSets.add(new Object[] {newExecutionContext, transition});
            }
        }

        for (int i = 0; i < argSets.size(); i++) 
        {
            final Object[] args = (Object[]) argSets.get(i);
            node.leave((ExecutionContext) args[0], (Transition) args[1]);
        }

	}

}
