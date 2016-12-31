package me.shadaj.gcj

import java.util.regex.Pattern

import scala.io.Source
import scala.collection.generic.CanBuildFrom

import scala.language.higherKinds

package object parser extends CommonParsers with TupleParsers {
  implicit class ConvertingString(val string: String) extends AnyVal {
    def convert[T](implicit converter: Converter[T]): T = {
      converter.convert(string)
    }
    
    def convertWithTokenizer[T](tokenizer: Converter[Seq[String]])(implicit converter: CollectionConverter[T]) = {
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

  // Tokenizers
  implicit def charToTokenizer(char: Char): Converter[Seq[String]] = new Converter[Seq[String]] {
    def convert(string: String): Seq[String] = string.split(char)
  }

  // Tokenizers
  implicit def stringsToTokenizer(strings: Seq[String]): Converter[Seq[String]] = new Converter[Seq[String]] {
    def convert(string: String): Seq[String] =
      string.split(strings.map(Pattern.quote).mkString("(", ")|(", ")"))
  }
  
}