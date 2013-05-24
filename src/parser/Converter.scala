package parser

trait Converter[T] {
  def convert(string: String): T
}