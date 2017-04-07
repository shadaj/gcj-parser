organization := "me.shadaj"

name := "gcj-parser"

scalaVersion := "2.12.1"

version := "0.1-SNAPSHOT"

resolvers += "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % Test

sourceGenerators in Compile <+= baseDirectory map { dir =>
  val fileToWrite = dir / "src" / "gen" / "scala" / "me/shadaj/gcj/parser" / "TupleParsers.scala"
  val methods = (2 to 22).map { n =>
    s"""implicit def tuple${n}ToConverter[${(1 to n).map(t => s"T$t: Converter").mkString(", ")}]: CollectionConverter[(${(1 to n).map(t => s"T$t").mkString(", ")})] = {
       |  new CollectionConverter[(${(1 to n).map(t => s"T$t").mkString(", ")})] {
       |    override def convertWithTokenizer(string: String, tokenizer: Converter[Seq[String]]) = {
       |      val split = string.convert[Seq[String]](tokenizer)
       |      (${(1 to n).map(t => s"split(${t - 1}).convert[T$t]").mkString(", ")})
       |    }
       |  }
       |}"""
  }

  val toWrite =
    s"""package me.shadaj.gcj.parser
       |trait TupleParsers {
       |${methods.mkString("\n")}
       |}""".stripMargin

  IO.write(fileToWrite, toWrite)
  Seq(fileToWrite)
}
