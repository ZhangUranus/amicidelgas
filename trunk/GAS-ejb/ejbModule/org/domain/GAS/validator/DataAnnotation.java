package org.domain.GAS.validator;



import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;
import org.hibernate.validator.ValidatorClass;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ValidatorClass(DataValidator.class)

public @interface DataAnnotation {
	
	String message() default "Sei troppo piccolo per partecipare al GAS :-D";

}
