package scalathon

import tools.nsc.doc.model.{AbstractType, MemberEntity, TemplateEntity, Entity}
import tools.nsc.io._
import tools.nsc.{Global, doc, CompilerCommand}
import tools.nsc.doc.model.comment.CommentFactory

package object scaladoc {
  /* Given a directory recursively returns the list of files ending with `.scala`.
	 *  Directories named `.git` or `.svn` are ignored. */
	def scalaFiles(directory:Directory) =
	  directory.walkFilter( x => x.name != ".git" && x.name != ".svn")
	  .filter(f => f.isFile && f.name.endsWith(".scala")) map(_.toFile) toList

	def scalaFiles(dir:String):List[File] = scalaFiles(Path(dir).toDirectory)

	def docUniverse(files:List[File]) = new DocCompiler(files).docUniverse

  def document(files:List[File]) = {
    val docCompiler = new DocCompiler(files) {
      override val command = new CompilerCommand(
        "-d" :: """C:\dev\langs\scala\projects\tmp\scaladoc2\doc-testing""" :: (files.map {_.path}), docSettings)
    }
    docCompiler.document
  }

	lazy val modelFactory = {
    val settings = new doc.Settings((str: String) => {})
    val reporter = new scala.tools.nsc.reporters.ConsoleReporter(settings)
    val g = new Global(settings, reporter)
    (new SimpleModelFactory(g, settings) with CommentFactory with doc.model.TreeFactory)

  def nature2string(e : Entity) =
    e match {
      case t : TemplateEntity if(t.isTrait) => "trait "
      case t : TemplateEntity if(t.isObject) => "object "
      case t : TemplateEntity if(t.isPackage) => "package "
      case t : TemplateEntity if(t.isClass) => "class "
      case e : MemberEntity if(e.isDef) => "def "
      case e : MemberEntity if(e.isVal) => "val "
      case e : MemberEntity if(e.isLazyVal) => "lazy val "
      case e : MemberEntity if(e.isVar) => "var "
      case e : MemberEntity if(e.isAliasType) => "type "
      case a : AbstractType => "type "
      case u @ _ =>
        println("unknown "+ u+ " " +u.getClass); ""
    }

   def definitionName(e:Entity) = nature2string(e) + " " + e.name
   def qualifiedDefinitionName(e:Entity) = nature2string(e) + " " + e.qualifiedName
}