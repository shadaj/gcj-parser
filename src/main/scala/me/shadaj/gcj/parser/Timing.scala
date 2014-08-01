package me.shadaj.gcj.parser

object Timing {
  def timed[T](message: String = null)(thunk: => T): T = {
    val start = System.currentTimeMillis()
    val ret = thunk
    val end = System.currentTimeMillis()
    println(message + (end - start))
    ret
  }
}