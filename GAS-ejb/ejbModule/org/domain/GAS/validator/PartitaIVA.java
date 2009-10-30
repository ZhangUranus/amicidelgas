package org.domain.GAS.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hibernate.validator.ValidatorClass;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ValidatorClass(PartitaIVAValidator.class)

public @interface PartitaIVA 
{
	String message() default "La partita IVA non � valida: "
		+ "il codice di controllo non corrisponde.";
}
