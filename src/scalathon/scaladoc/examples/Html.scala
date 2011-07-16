package scalathon.scaladoc
package examples

object Html {
	def main(args:Array[String]):Unit = {

		document(scalaFiles(scalathonPath+"/samples"),
      scalathonPath+"/output")
	}
}