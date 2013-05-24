package parser

object Timing {
  def timed[T](message: String = "")(thunk: => T): T = {
    val start = System.currentTimeMillis()
    val ret = thunk
    val end = System.currentTimeMillis()
    println(message + (end - start))
    ret
  }
  
}