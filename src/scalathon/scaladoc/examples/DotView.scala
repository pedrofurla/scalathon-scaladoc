package scalathon.scaladoc
package examples

import java.io.ByteArrayInputStream
import scala.tools.nsc.doc.Universe



object DotView {
	import tools.nsc.doc.model._
  import tools.nsc.doc.model.{ Object => SDObject}

  val fqnExclusions = "scala.Any" :: "scala.AnyRef" :: "scala.AnyVal" :: Nil

  def filter(e:Entity):Boolean = (!fqnExclusions.contains(e.qualifiedName))
  def filter2(e:Entity) = e.inTemplate.qualifiedName == "scala.tools.nsc.doc.model.comment" //&& e.inTemplate.qualifiedName!="scala.tools.nsc.doc.model.comment"

  def makeDot(universe: Universe): String = {

    def directedNode(from:Entity, to:Entity):String = from.name + " -> " + to.name + ";"
    def directedNodes(from:Entity, tos:List[Entity]):String =
      if (!tos.isEmpty)
        //tos.foldLeft(from.name){ (acc:String,e:Entity) => acc + "->" + e.name  } + ";"
        tos map { directedNode(from, _) } mkString("\n")
      else ""

    def gather(owner: DocTemplateEntity): String =
        (for(m <- owner.members if m.isLocal && filter(m)) yield
          m match {
            case tpl: Package =>
              gather(tpl)
            case tpl: Class =>
              println(qualifiedDefinitionName(tpl))
              tpl.parents filter (x => filter(x) && filter2(x)) foreach { x=>println("\t"+qualifiedDefinitionName(x)) }
              directedNodes(tpl,tpl.parents filter (x => filter(x) && filter2(x))) + gather(tpl)
            case tpl: Trait =>
              println(qualifiedDefinitionName(tpl))
              tpl.parents filter (x => filter(x) && filter2(x)) foreach { x=>println("\t"+qualifiedDefinitionName(x)) }
              directedNodes(tpl,tpl.parents filter (x => filter(x) && filter2(x))) + gather(tpl)
            case tpl: SDObject =>
              println(qualifiedDefinitionName(tpl))
              tpl.parents filter (x => filter(x) && filter2(x)) foreach { x=>println("\t"+qualifiedDefinitionName(x)) }
              directedNodes(tpl,tpl.parents filter (x => filter(x) && filter2(x))) + gather(tpl)
            case x @ _ => ""
          }) filter {_ != ""} mkString("\n")

    gather(universe.rootPackage)
  }

  def digraph(name:String,contents:String)=
    "digraph "+name+""" {
      node [shape=box];
      rankdir=BT;
    """ + "\n" +
    contents + "\n"+
    " overlap=false" +
    "}"


  def main(args:Array[String]):Unit = {
    import scala.tools.nsc.io._

    val dotExe = "/usr/local/bin/dot"

    val scalas = "Entity.scala" :: "Body.scala" :: "Comment.scala" :: Nil

    val files = scalaFiles(scalaSourcesPath+"/src") filter {scalas contains _.name   }
    val dotFile =  scalathonPath + "/output/out.dot"
    val graphName = "ScaladocWikiModel"

		val universe = makeDot(docUniverse(files))

    val graph = digraph(graphName, universe);
    File(dotFile).writeAll(graph)

    import scala.sys.process._
    val p = Process(dotExe,"-Tpng" :: Nil)
    val is = new ByteArrayInputStream(graph.getBytes("UTF-8"))
    val pro = (p #< is #> new JFile(scalathonPath + "/output/"+graphName+".png")).run
    println(pro.exitValue)
	}
}