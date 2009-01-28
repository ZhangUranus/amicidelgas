<#if persons?has_content>
I più famosi uomini che fanno domande hanno visitato il nostro sito e Vincenzo Pesante è uno di questi:
<ul>
	<#list persons as person>
		<li> ${person.firstName?if_exists} ${person.lastName?if_exists}
	</#list>
</ul>
</#if>