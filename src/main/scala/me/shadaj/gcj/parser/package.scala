package me.shadaj.gcj

import scala.io.Source
import scala.collection.mutable.Builder
import scala.collection.generic.CanBuildFrom

package object parser {
  implicit class ConvertingString(val string: String) extends AnyVal {
    def convert[T](implicit converter: Converter[T]): T = {
      converter.convert(string)
    }
    
    def convertWithTokenizer[T <: Traversable[_]](tokenizer: Converter[Seq[String]])(implicit converter: CollectionConverter[T]) = {
      converter.convertWithTokenizer(string, tokenizer)
    }
  }

  implicit def singleToMulti[T, Col[_]](implicit singleConverter: Converter[T],
                                        cbf: CanBuildFrom[Nothing, T, Col[T]]): CollectionConverter[Col[T]] = {
    new CollectionConverter[Col[T]] {
      override def convertWithTokenizer(string: String, tokenizer: Converter[Seq[String]]): Col[T] = {
        string.convert(tokenizer).map(_.convert(singleConverter)).to(cbf)
      }
    }
  }
  
  class CharRepeatingConverter(char: Char) extends Converter[Seq[String]] {
    def convert(string: String) = string.split(char)
  }

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

  abstract class TestCaseSeqConverter[T <: TestCase] extends Converter[Seq[T]] {
    def parseTestCase(lines: Iterator[String]): T
    
    def convert(string: String) = {
      val lines = Source.fromString(string).getLines
      lines.foldLeft(Seq[T]()) {case (testCases, cur) =>
        testCases :+ parseTestCase(Iterator.single(cur) ++ lines)
      }
    }
  }

  //Parsing
  implicit class ParsingSource(val source: Source) extends AnyVal {
    def parse[T <: TestCase](implicit converter: TestCaseSeqConverter[T]): Seq[T] =
      source.getLines.mkString("\n").convert(converter)
  }
  
}