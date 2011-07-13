package scalathon.scaladoc
package examples

class ParseComment {
	def main(args:Array[String]) = {
		val factory = modelFactory;
		val doc = """
				|/**
				| * blabla
				| *
				| * @version bla
				| * @author Fulano
				| * @author Bleh
				| */
				""".stripMargin
		val doc2 = "/** @author Fulano */"
		println(factory.parseComment(doc))
		println(factory.parseComment(doc).authors)
	}
}