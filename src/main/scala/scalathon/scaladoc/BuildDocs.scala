package scalathon.scaladoc

object BuildDocs extends App {
  docUniverse(scalaFiles("samples"))
}