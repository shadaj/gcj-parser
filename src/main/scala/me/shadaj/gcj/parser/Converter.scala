package me.shadaj.gcj.parser

trait Converter[T] {
  def convert(string: String): T
}

trait CollectionConverter[T] extends Converter[T] {
  def convert(string: String) = convertWithTokenizer(string, SpaceRepeatingConverter)
  def convertWithTokenizer(string: String, tokenizer: Converter[Seq[String]]): T
}