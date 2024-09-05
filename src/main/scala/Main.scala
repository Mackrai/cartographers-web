import zio.*
import zio.http.*
import zio.logging.backend.SLF4J

object Main extends ZIOAppDefault {
    private val routes =
        Routes(
            Method.GET / Root -> handler(Response.text("Hello, World!")),
        )

    override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] =
        Runtime.removeDefaultLoggers >>> SLF4J.slf4j

    def run: ZIO[Any, Throwable, Nothing] =
        Server.serve(routes).provide(Server.default)
}
