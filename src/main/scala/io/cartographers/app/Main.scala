package io.cartographers.app

import io.cartographers.app.config.{AppConfig, DBConfig, ServerConfig}
import io.cartographers.app.core.AppState
import io.cartographers.app.http.{HttpServer, RequestHandler}
import zio.*
import zio.http.Server
import zio.logging.backend.SLF4J

object Main extends ZIOAppDefault {
    override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] =
        Runtime.removeDefaultLoggers >>> SLF4J.slf4j

    private val server =
        for {
            httpApp <- ZIO.service[HttpServer]
            conf <- ZIO.service[AppConfig]
            _ <- Server.serve(httpApp.routes).provide(
                ZLayer.succeed(conf.serverConfig.init()),
                Server.live
            )
        } yield ()

    def run: ZIO[Any, Throwable, Unit] =
        server.provide(
            AppConfig.live,
            DBConfig.live,
            ServerConfig.live,
            AppState.live,
            HttpServer.live,
            RequestHandler.live,
        )

}
