package me.shadaj.gcj.examples

import me.shadaj.gcj.parser._

class MinScalarTestCase(n: Int, v1: Seq[Long], v2: Seq[Long]) extends TestCase {
  def solve = {
    Solution(v1.sorted.zip(v2.sorted.reverse).map(t => t._1 * t._2).sum.toString)
  }
}

object MinScalarLauncher extends ProblemLauncher[MinScalarTestCase](1) {
  val converter = new TestCaseSeqConverter[MinScalarTestCase] {
    override def parseTestCase(lines: Iterator[String]) = {
      new MinScalarTestCase(lines.next.convert[Int], lines.next.convert[Seq[Long]], lines.next.convert[Seq[Long]])
    }
  }
}