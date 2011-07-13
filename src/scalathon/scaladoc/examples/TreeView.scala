package scalathon.scaladoc
package examples

import scala.tools.nsc.doc.model._

object TreeView {

	def packView(packages:List[Package], tab:Int = 0):Unit = 
		for(pack <- packages sortBy(_.name)) {
			println((" " * tab) + nature2string(pack) + " " + pack.qualifiedName)
			templateView(pack, pack.templates, tab+2)
			packView(pack.packages,tab+2)
			
			nonTemplateView(pack, pack.values,tab+2)
			nonTemplateView(pack, pack.methods,tab+2)
			
			typeView(pack, pack.abstractTypes, tab+2)
			typeView(pack, pack.aliasTypes, tab+2)
		}
	
	def templateView(owner:DocTemplateEntity, templates:List[DocTemplateEntity], tab:Int = 0):Unit = 	
		for(t <- templates sortBy(_.name) if t.inDefinitionTemplates.isEmpty || t.inDefinitionTemplates.head == owner) {
			println((" " * tab) + nature2string(t) + " " + t.name);
			
			typeView(t, t.aliasTypes, tab+2)
			typeView(t, t.abstractTypes, tab+2)
			templateView(t, t.templates, tab+2)
			nonTemplateView(t, t.methods,tab+2)
			nonTemplateView(t, t.values,tab+2)
		}
	
			
	def nonTemplateView(owner:DocTemplateEntity, nonTemplates:List[NonTemplateMemberEntity], tab:Int) = {
		val filtered = nonTemplates.filter( _.inDefinitionTemplates.head == owner )
		if(!filtered.isEmpty) println(" " * tab)
		for(member <- filtered) 
			println(" " * tab + nature2string(member) + " " +member.name+", ")
	}
			
	def typeView(owner:DocTemplateEntity, types:List[NonTemplateMemberEntity], tab:Int = 0) = 
		for(t <- types sortBy(_.name) if t.inDefinitionTemplates.isEmpty || t.inDefinitionTemplates.head == owner)     	  
			println((" " * tab) +  " type " + t.name)



	val tab = "\t" * 3
	def indent(s:String) = tab +  s replaceAll("\n","\n"+tab)
	
	def isContainer(tpl:TemplateEntity) = tpl.isInstanceOf[TemplateEntity]
	
	def isLocalDefinition(m:MemberEntity, owner:DocTemplateEntity) = m.inDefinitionTemplates.isEmpty || m.inDefinitionTemplates.head == owner 
	
	implicit def pimpedMember(m:MemberEntity) = new {
		def isDefinedAt(owner:TemplateEntity) = m.inDefinitionTemplates.isEmpty || m.inDefinitionTemplates.head == owner 
	}
	
	def gather2(owner:DocTemplateEntity):String = {
		val locals = owner.members filter { _ isDefinedAt owner } 
		val containers = locals collect { case tpl:DocTemplateEntity => tpl }
		val nonContainers = locals filter { x => x.isAliasType || x.isAbstractType };

		" * " + ( if(owner.isPackage) owner.qualifiedName +" * "+owner.inDefinitionTemplates.head.qualifiedName else owner.name+" * "+owner.inDefinitionTemplates.head.qualifiedName )+ "\n" +		
		( if (containers.size>0) indent(
		//(nonContainers map {" - " + _.qualifiedName } mkString("","\n","")) +
			(containers.map { c => gather2(c) } mkString("","",""))
		) else "" )
	}
	
	def gather(owner:DocTemplateEntity):String = {
		(for(m <- owner.members if m isDefinedAt owner) yield { 
			m.name +"\n"+ 
				(m match {
					case tpl:DocTemplateEntity => indent(gather(tpl))	         
					case _ => "" 
				})
		}) mkString("","","")
	}
	
	def gatherHtml(owner:DocTemplateEntity):String = {
		(for(m <- owner.members if m isDefinedAt owner) yield { 
			m.name +"\n"+ 
				(m match {
					case tpl:DocTemplateEntity => indent(gather(tpl))	         
					case _ => "" 
				})
		}) mkString("","","")
	}
          
            
      
}