package switchboard.config_provider

import scala.concurrent.Future
import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context


trait ConfigValueConvert[V] {

  def apply(configs: Map[String, String]): V

}


object ConfigValueConvert {

  def config[T]:ConfigValueConvert[T] = macro impl[T]

  def impl[T: c.WeakTypeTag](c: Context): c.Expr[ConfigValueConvert[T]] = {
    import c.universe._

    // case classのクラス名
    val caseClassSym: c.universe.Symbol = c.weakTypeOf[T].typeSymbol

    if (!caseClassSym.isClass || !caseClassSym.asClass.isCaseClass) c.abort(c.enclosingPosition, s"$caseClassSym is not a case class")

    // 各フィールドのシンボル
    val syms: List[TermSymbol] = caseClassSym.typeSignature.decls.toList.collect { case x: TermSymbol if x.isVal && x.isCaseAccessor => x }


    val args =
      q"""
         ${syms.map(s=>"configs.get(\""+s.fullName+"\")").mkString(",")}
       """

    val companion:Symbol = caseClassSym.companion

    println(args)

    val expr: Tree =
      q"""
       new ConfigValueConvert[${caseClassSym}]{
          override def apply(configs: Map[String, String]):${caseClassSym} = {
            ${companion}($args)
          }
       }
     """
    c.Expr[ConfigValueConvert[T]](expr)
  }

}


trait ConfigProvider {

  def get[V](path: String)(implicit convert: ConfigValueConvert[V]): Future[Option[V]]

}
