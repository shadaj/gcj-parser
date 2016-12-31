package me.shadaj.gcj.parser

object Examples extends App {
  println("1 2 3".convert[List[Int]])

  println("1 2 3".convert[Array[Int]])

  println("1 2 3".convert[Stream[Int]])
  
  println("4,5,6".convertWithTokenizer[List[Int]](','))

  println("4foo5bar6bar7".convertWithTokenizer[List[Int]](List("foo", "bar")))
}