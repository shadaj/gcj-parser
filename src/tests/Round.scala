package tests

import parser._

class MinScalarTestCase(val testCaseNumber: Int, n: Int, v1: Seq[Long], v2: Seq[Long]) extends TestCase {
  def solve = {
    Solution(v1.sorted.zip(v2.sorted.reverse).map(t => t._1 * t._2).sum.toString, testCaseNumber)
  }
}

object MinScalarLauncher extends ProblemLauncher[MinScalarTestCase]("A-large-practice", 1) {
  val converter = new TestCaseSeqConverter[MinScalarTestCase] {
    override def parseTestCase(lines: Iterator[String], testCaseNumber: Int) = {
      new MinScalarTestCase(testCaseNumber, lines.next.convert[Int], lines.next.convertSeq[Long](), lines.next.convertSeq[Long]())
    }
  }
}