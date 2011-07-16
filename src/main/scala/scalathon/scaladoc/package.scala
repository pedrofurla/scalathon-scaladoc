package scalathon

import tools.nsc.io._
import tools.nsc.{Global, doc, CompilerCommand}
import doc.model._
import tools.nsc.doc.model.comment.CommentFactory

package object scaladoc {

  val scalaSourcesPath="/Users/pedrofurla/dev/projects/scala-doc-prj-svn"
  val scalathonPath="/Users/pedrofurla/dev/projects/scalathon-scaladoc"

  /* Given a directory recursively returns the list of files ending with `.scala`.
	 *  Directories named `.git` or `.svn` are ignored. */
	def scalaFiles(directory:Directory) =
	  directory.walkFilter( x => x.name != ".git" && x.name != ".svn")
	  .filter(f => f.isFile && f.name.endsWith(".scala")) map(_.toFile) toList

	def scalaFiles(dir:String):List[File] = scalaFiles(Path(dir).toDirectory)

	def docUniverse(files:List[File]) = new DocCompiler(files).docUniverse

  def document(files:List[File],outPath:String) = {
    val docCompiler = new DocCompiler(files) {
      docSettings.embeddedDefaults
      docSettings.usejavacp.value = true
      override val command = new CompilerCommand(
        "-d" :: outPath :: (files.map {_.path}), docSettings)
    }
    docCompiler.document
  }

	lazy val modelFactory = {
    val settings = new doc.Settings((str: String) => {})
    val reporter = new scala.tools.nsc.reporters.ConsoleReporter(settings)
    val g = new Global(settings, reporter)
    (new SimpleModelFactory(g, settings) with CommentFactory with doc.model.TreeFactory)
  }

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

    implicit def memberOps(m:MemberEntity) = new {
    /** Is this MemberEntity defined locally? */
    def isLocal = m.inDefinitionTemplates.isEmpty || m.inDefinitionTemplates.head == m.inTemplate
  }
  implicit def docTemplOps(dte:DocTemplateEntity) = new {
    /** Returns a ordered List of entities extended directly */
    def parents:List[TemplateEntity] = dte.parentType.map { _.refEntity.map { x => x._2._1 } toList } getOrElse List.empty
    /** Is this MemberEntity defined locally in relation to owner? */
    def isDefinedAt(owner:TemplateEntity) = dte.inDefinitionTemplates.isEmpty || dte.inDefinitionTemplates.head == owner
  }
}