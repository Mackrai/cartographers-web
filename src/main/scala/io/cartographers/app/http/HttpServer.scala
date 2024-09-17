package io.cartographers.app.http

import io.cartographers.app.core.AppState
import io.cartographers.app.dto.RequestBase
import io.cartographers.app.http.RequestHandler
import io.cartographers.app.util.Implicits.WebSocketChannelOps
import io.circe.parser.*
import zio.*
import zio.http.*
import zio.http.ChannelEvent.{ExceptionCaught, Read, UserEvent, UserEventTriggered}

import java.util.UUID

class HttpServer(appState: AppState, requestHandler: RequestHandler) {
    private val socketApp: WebSocketApp[Any] =
        Handler.webSocket { channel =>
            channel.receiveAll {
                // Send a "greeting" message to the client once the connection is established
                case UserEventTriggered(UserEvent.HandshakeComplete) =>
                    for {
                        newUserId <- ZIO.succeed(UUID.randomUUID().toString)
                        _ <- appState.addUser(newUserId)
                        _ <- channel.send(Read(WebSocketFrame.text(s"Greetings, $newUserId")))
                    } yield ()

                case Read(WebSocketFrame.Text("end")) =>
                    for {
//                        _ <- appState.removeUser()
                        _ <- channel.shutdown
                    } yield ()

                // Log when the channel is getting closed
                case Read(WebSocketFrame.Close(status, reason)) =>
                    ZIO.log("Closing channel with status: " + status + " and reason: " + reason)

                // Print the exception if it's not a normal close
                case ExceptionCaught(cause) =>
                    ZIO.log(s"Channel error!: ${cause.getMessage}")

                // List users
                case Read(WebSocketFrame.Text("users")) =>
                    for {
                        users <- appState.users.get
                        _ <- channel.send(users.mkString(", "))
                    } yield ()

                // List games
                case Read(WebSocketFrame.Text("games")) =>
                    for {
                        games <- appState.games.get
                        _ <- channel.send(games.toString())
                    } yield ()

                case Read(WebSocketFrame.Text(raw)) =>
                    for {
                        r <- ZIO.fromEither(decode[RequestBase](raw))
                        _ <- requestHandler.handle(channel, r)
                    } yield ()

                case _ =>
                    ZIO.unit
            }
        }

    val routes: Routes[Any, Nothing] = {
        Routes(
            Method.GET / "greet" / string("name") -> handler { (name: String, _: Request) =>
                Response.text(s"Greetings $name!")
            },
            Method.GET / "ws" -> handler(socketApp.toResponse),
        )
    }
}

object HttpServer {
    val live: ZLayer[AppState & RequestHandler, Nothing, HttpServer] =
        ZLayer {
            for {
                appState <- ZIO.service[AppState]
                handler <- ZIO.service[RequestHandler]
                httpServer <- ZIO.succeed(new HttpServer(appState, handler))
            } yield httpServer
        }
}
