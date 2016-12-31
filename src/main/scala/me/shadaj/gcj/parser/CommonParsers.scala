package me.shadaj.gcj.parser

trait CommonParsers {
  implicit object StringToStringConverter extends Converter[String] {
    def convert(string: String) = string
  }

  implicit object BooleanConverter extends Converter[Boolean] {
    def convert(string: String) = string.toBoolean
  }

  implicit object CharConverter extends Converter[Char] {
    def convert(string: String) = if (string.length() == 1) {
      string.head
    } else {
      throw new IllegalArgumentException("A multiple length string cannot be converted to a char")
    }
  }

  implicit object ByteConverter extends Converter[Byte] {
    def convert(string: String) = string.toByte
  }

  implicit object ShortConverter extends Converter[Short] {
    def convert(string: String) = string.toShort
  }

  implicit object IntConverter extends Converter[Int] {
    def convert(string: String) = string.toInt
  }

  implicit object LongConverter extends Converter[Long] {
    def convert(string: String) = string.toLong
  }

  implicit object FloatConverter extends Converter[Float] {
    def convert(string: String) = string.toFloat
  }

  implicit object DoubleConverter extends Converter[Double] {
    def convert(string: String) = string.toDouble
  }

  implicit object BigIntConverter extends Converter[BigInt] {
    def convert(string: String) = math.BigInt(string)
  }

  implicit object SpaceRepeatingConverter extends Converter[Seq[String]] {
    def convert(string: String) = string.split(' ').toSeq
  }
}
