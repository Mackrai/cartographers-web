package io.cartographers.app.config

import zio.*

class DBConfig(name: String)

object DBConfig {
    val live: ULayer[DBConfig] =
        ZLayer.succeed(
            new DBConfig("my-test-db")
        )
}
