import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import scala.concurrent.duration._

class Scrolling extends Simulation {

  val hostName: String = Option(System.getProperty("hostName")).getOrElse("localhost")
  val port: String = Option(System.getProperty("port")).getOrElse("443")
  val baseParadineUrl = s"https://$hostName:$port"

  val httpProtocol: HttpProtocolBuilder = {
    val protocol = http
      .baseUrl(baseParadineUrl)
      .inferHtmlResources()
      .acceptHeader("*/*")
      .acceptEncodingHeader("gzip, deflate, sdch")
      .acceptLanguageHeader("en-US,en;q=0.8")
      .contentTypeHeader("application/json")
      .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36")
      .shareConnections

    protocol
  }

  val scn = scenario("Scrolling Restaurants List")
   .exec(http("get page 1")
    .get("/api/paradine/v2/restaurants"))

   .exec(http("get page 2")
    .get("/api/paradine/v2/restaurants?page=2"))

   .exec(http("get page 3")
    .get("/api/paradine/v2/restaurants?page=3"))

   .exec(http("get page 4")
    .get("/api/paradine/v2/restaurants?page=4"))

   .exec(http("get page 5")
    .get("/api/paradine/v2/restaurants?page=5"))

   .exec(http("get page 6")
    .get("/api/paradine/v2/restaurants?page=6"))

   .exec(http("get page 7")
    .get("/api/paradine/v2/restaurants?page=7"))

   .exec(http("get page 8")
    .get("/api/paradine/v2/restaurants?page=8"))

   .exec(http("get page 9")
    .get("/api/paradine/v2/restaurants?page=9"))

   .exec(http("get page 10")
    .get("/api/paradine/v2/restaurants?page=10"))


setUp(
  scn.inject(
    nothingFor(4 seconds),
    atOnceUsers(2),
    rampUsers(10) during (5 seconds),
    constantUsersPerSec(50) during (15 seconds) randomized,
    rampUsersPerSec(10) to 20 during (10 minutes) randomized,
    heavisideUsers(1) during (20 seconds)
  ).protocols(httpProtocol)
)

}