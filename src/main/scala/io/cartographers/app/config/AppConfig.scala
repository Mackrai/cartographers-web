package io.cartographers.app.config

import zio.*

case class AppConfig(db: DBConfig, serverConfig: ServerConfig)

object AppConfig {
    val live: ZLayer[DBConfig & ServerConfig, Nothing, AppConfig] =
        ZLayer {
            for {
                db <- ZIO.service[DBConfig]
                server <- ZIO.service[ServerConfig]
            } yield AppConfig(db, server)
        }
}
