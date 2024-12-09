package example

import scala.deriving.Mirror

sealed trait Labels[A] {
  def apply(): String
}

object Labels {
  final class Inst[A](s: String) extends Labels[A] { final def apply(): String = s }

  private inline def summonLabel[L, A]: String =
    compiletime.summonFrom {
      case _: Mirror.Of[A] => compiletime.constValue[L].toString + "(" + derived[A].apply() + ")"
      case _ => compiletime.constValue[L].toString
    }

  private inline def summonLabels[T <: Tuple]: List[String] =
    inline compiletime.erasedValue[T] match {
      case _: EmptyTuple => Nil
      case _: ((l, a) *: t) => summonLabel[l, a] :: summonLabels[t]
    }

  inline def derived[A](using A: Mirror.Of[A]): Labels[A] =
    inline A match {
      case m: Mirror.ProductOf[A] =>
        new Inst[A](summonLabels[Tuple.Zip[A.MirroredElemLabels, A.MirroredElemTypes]].mkString(", "))
    }
}
