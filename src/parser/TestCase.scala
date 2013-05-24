package parser

trait TestCase {
  def testCaseNumber: Int
  def solve: Solution
}