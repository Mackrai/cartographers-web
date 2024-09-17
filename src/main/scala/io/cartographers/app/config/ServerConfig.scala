package io.cartographers.app.config

import zio.*
import zio.http.Server

case class ServerConfig(port: Int) {
    def init(): Server.Config =
        Server.Config.default.port(port)
}

object ServerConfig {
    val live: ULayer[ServerConfig] =
        ZLayer.succeed(
            ServerConfig(8080)
        )
}
