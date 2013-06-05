import scala.io.Source

package object parser {
  implicit class ConvertingString(val string: String) extends AnyVal {
    def convert[T](implicit converter: Converter[T]): T = {
      converter.convert(string)
    }

    def convertSeq[T](tokenizer: Converter[Seq[String]] = SpaceRepeatingConverter)(implicit converter: Converter[T]): Seq[T] = {
      string.convert(tokenizer).map(_.convert(converter))
    }
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

  object LineRepeatingConverter extends Converter[Seq[String]] {
    def convert(string: String) = string.split('\n').toSeq
  }

  abstract class TestCaseSeqConverter[T <: TestCase] extends Converter[Seq[T]] {
    def parseTestCase(lines: Iterator[String], testCaseNumber: Int): T
    
    def convert(string: String) = {
      val lines = Source.fromString(string).getLines
      lines.foldLeft((1, Seq[T]())) {case ((n, testCases), cur) =>
        (n + 1, testCases :+ parseTestCase(Iterator.single(cur) ++ lines, n))
      }._2
    }
  }

  //Parsing
  implicit class ParsingSource(val source: Source) extends AnyVal {
    def parse[T <: TestCase](implicit converter: TestCaseSeqConverter[T]): Seq[T] =
      source.getLines.mkString("\n").convert(converter)
  }
  
}