package switchboard.config_provider

import org.scalatest._
import switcboard.logging.LoggingSupport

class ConfigValueConvertTest extends FunSuite with Matchers with LoggingSupport {

  case class Hoge(foo: String)

  test("macro") {

    val e = Hoge("foo")
    ConfigValueConvert.config[Hoge](Map("foo" -> "foo", "bar" -> "bar")) should be(e)
  }

}
