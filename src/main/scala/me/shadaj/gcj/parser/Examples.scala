package me.shadaj.gcj.parser

object Examples extends App {
//  implicit val conv = singleToSequence[Int, Seq[Int]]
  println("1 2 3".convert[List[Int]])

  println("1 2 3".convert[Array[Int]])

  println("1 2 3".convert[Stream[Int]])

  object CommaRepeatingConverter extends Converter[Seq[String]] {
    def convert(string: String) = string.split(',').toSeq
  }
  
  println("4,5,6".convertWithTokenizer[List[Int]](CommaRepeatingConverter))
}