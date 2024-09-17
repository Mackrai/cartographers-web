package io.cartographers.app.http

import io.cartographers.app.core.AppState
import io.cartographers.app.dto.RequestBase
import io.cartographers.app.dto.request.*
import io.cartographers.app.dto.response.*
import io.cartographers.app.error.{AlreadyExistsError, GameNotFoundError}
import io.cartographers.app.util.Implicits.WebSocketChannelOps
import io.circe.syntax.*
import zio.*
import zio.http.*

import java.util.UUID

class RequestHandler(appState: AppState) {
    private val onError: PartialFunction[Throwable, UIO[Unit]] = {
        case AlreadyExistsError() => ZIO.logError("AlreadyExistsError")
        case GameNotFoundError() => ZIO.logError("GameNotFoundError")
        case e => ZIO.logError("Unknown error")
    }

    def handle(channel: WebSocketChannel, r: RequestBase): ZIO[Any, Throwable, Unit] = {
        r match
            case dto@CreateGameRequest(userId, gameData) =>
                for {
                    _ <- ZIO.log(dto.toString)
                    newGameId = UUID.randomUUID().toString
                    _ <- appState.addGame(newGameId)
                    _ <- channel.send(CreateGameResponse(newGameId).asJson.toString)
                } yield ()

            case dto@JoinRequest(gameId, userId) =>
                for {
                    _ <- ZIO.log(dto.toString)
                    _ <- appState.joinGame(gameId, userId)
                    _ <- channel.send(JoinGameResponse(gameId).asJson.toString)
                } yield ()

            case r =>
                ZIO.log(r.toString)
                
    }.catchSome(onError)
}

object RequestHandler {
    val live: ZLayer[AppState, Nothing, RequestHandler] =
        ZLayer(
            for {
                appState <- ZIO.service[AppState]
            } yield new RequestHandler(appState)
        )
}
