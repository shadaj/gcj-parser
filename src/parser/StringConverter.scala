package parser

trait StringConverter[T] {
  def convert(string: String): T
}