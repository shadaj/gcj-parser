package me.shadaj.gcj.parser

trait StringConverter[T] {
  def convert(string: String): T
}