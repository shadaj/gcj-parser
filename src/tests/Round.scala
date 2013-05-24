package problems

import parser._

class MinScalarTestCase(val testCaseNumber: Int, n: Int, v1: Seq[Long], v2: Seq[Long]) extends TestCase {
  def solve = {
    Solution(v1.sorted.zip(v2.sorted.reverse).map(t => t._1 * t._2).sum.toString, testCaseNumber)
  }
}

object MinScalarLauncher extends ProblemLauncher[MinScalarTestCase]("A-large-practice", 1) {
  implicit object MinScalarConverter extends TestCaseSeqConverter[MinScalarTestCase] {
    def fixedLineCount = 3
    
    def parseTestCase(fixedLines: Seq[String], lines: Iterator[String], testCaseNumber: Int) = {
      new MinScalarTestCase(testCaseNumber, fixedLines(0).convert[Int], fixedLines(1).convertSeq[Long](), fixedLines(2).convertSeq[Long]())
    }
  }

  override val problem = new Problem[MinScalarTestCase]
}