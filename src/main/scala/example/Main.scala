package example

import scala.deriving.Mirror

object Main {
  def main(args: Array[String]): Unit = {
    val m = summon[Mirror.ProductOf[Test]]
    println("\n***** Fields: " + compiletime.constValueTuple[m.MirroredElemLabels] + "\n\n")
  }
}
