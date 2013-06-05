package parser

import java.io.PrintWriter

import scala.io.Source

import Timing._

class Problem[T <: TestCase: TestCaseSeqConverter] {
  def solve(problemName: String, unneededLines: Int) {
    val input = Source.fromFile(problemName + ".in")
    val output = new PrintWriter(problemName + ".out")

    input.getLines.drop(unneededLines)

    val testCases: Seq[T] = input.parse

    val solutions = solve(testCases).sortBy(_.problemNumber)

    solutions.foreach { p =>
      output.println("Case #" + p.problemNumber + ": " + p.answer)
    }
    output.close
  }

  def solve(testCases: Seq[T]) = {
    timed("Total Time: ") {
      testCases.map(t => timed(s"Test Case #${t.testCaseNumber} took ") {
        t.solve
      })
    }
  }
}

abstract class ProblemLauncher[T <: TestCase](problemName: String, unneededLines: Int = 1) {
  val problem: Problem[T] = new Problem[T]()(converter)

  val converter: TestCaseSeqConverter[T]
  
  def main(args: Array[String]) = {
    problem.solve(problemName, unneededLines)
  }
}