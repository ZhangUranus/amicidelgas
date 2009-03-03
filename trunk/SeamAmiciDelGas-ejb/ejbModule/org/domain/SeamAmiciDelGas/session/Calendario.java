package org.domain.SeamAmiciDelGas.session;

import java.util.Date;
import java.util.GregorianCalendar;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;


@Name("calendarBean")
//@Scope(value=ScopeType.SESSION)
public class Calendario {
	
	GregorianCalendar oggi = new GregorianCalendar();
	int giorno = oggi.get(oggi.DAY_OF_MONTH)+3;
	int mese = oggi.get(oggi.MONTH);
	int anno = oggi.get(oggi.YEAR);
	GregorianCalendar newCalendar = new GregorianCalendar(anno, mese, giorno);
	private Date selectedDate = newCalendar.getTime();
	
	
	public void setSelectedDate(Date selectedDate) {
		System.out.println("SET SELECTEDDATE");
		this.selectedDate = selectedDate;
	}

	public Date getSelectedDate() {
		System.out.println("GET SELECTEDDATE: "+selectedDate.toString());
		return selectedDate;
	}

}
