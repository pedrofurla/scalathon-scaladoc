name := "scalathon-scaladoc"

scalaVersion := "2.10.0-SNAPSHOT"

fork := true

mainClass := Some("scalathon.scaladoc.BuildDocs")

resolvers += "Scala-Tools Maven2 Snapshots Repository" at "http://scala-tools.org/repo-snapshots"

libraryDependencies ++= Seq("org.scala-lang" % "scala-compiler" % "2.10.0-SNAPSHOT")
