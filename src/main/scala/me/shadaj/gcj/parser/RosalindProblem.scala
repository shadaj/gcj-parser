package me.shadaj.gcj.parser

import java.io.PrintWriter
import scala.io.Source
import Timing.timed

class RosalindProblem[T <: TestCase: TestCaseSeqConverter] extends Problem[T] {
  override def solve(in: String, out: String, unneededLines: Int) {
    val input = Source.fromFile(in)
    val output = new PrintWriter(out)

    input.getLines.drop(unneededLines)

    val testCases: Seq[T] = input.parse

    val solutions = solve(testCases).sortBy(_._2)

    solutions.foreach { p =>
      println(p._1.answer)
      output.println(p._1.answer)
    }
    output.close
  }

  override def solve(testCases: Seq[T]) = {
    timed("Total Time: ") {
      testCases.zipWithIndex.map(t => timed(s"Test Case #${t._2 + 1} took ") {
        (t._1.solve, t._2 + 1)
      })
    }
  }
}

abstract class RosalindProblemLauncher[T <: TestCase](unneededLines: Int = 1) {
  val converter: TestCaseSeqConverter[T]

  def main(args: Array[String]) = {
    val problem: RosalindProblem[T] = new RosalindProblem[T]()(converter)
    problem.solve(args(0), args(1), unneededLines)
  }
}