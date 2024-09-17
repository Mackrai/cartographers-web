package io.cartographers.app.util

import zio.*
import zio.http.*
import zio.http.ChannelEvent.Read

object Implicits {
    implicit class WebSocketChannelOps(webSocketChannel: WebSocketChannel) {
        def send(message: String): Task[Unit] = webSocketChannel.send(Read(WebSocketFrame.text(message)))
    }

    implicit class BooleanOps(condition: Boolean) {
        def ifZio[R1, E1, A](onTrue: ZIO[R1, E1, A], onFalse: ZIO[R1, E1, A]): ZIO[R1, E1, A] =
            if (condition) onTrue else onFalse
    }
}