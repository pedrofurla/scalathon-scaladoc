package scalathon.scaladoc
package examples

object Html {
	def main(args:Array[String]):Unit = {
		//val u = docUniverse(scalaFiles("C:/dev/langs/scala/projects/scaladoc-testing/samples"))
		//TreeView.packView(u.rootPackage.packages)
		//println(TreeView.gather2(u.rootPackage))
    document(scalaFiles("C:/dev/langs/scala/projects/scaladoc-testing/samples"))
	}
}