package io.cartographers.app.core

import io.cartographers.app.error.*
import zio.*
import io.cartographers.app.util.Implicits.BooleanOps

case class AppState(users: Ref[List[String]], games: Ref[Map[String, List[String]]]) {
    def addUser(userId: String): UIO[Unit] =
        users.update(_ :+ userId)

    def removeUser(userId: String): UIO[Unit] =
        users.update(_.filterNot(_ == userId))

    def addGame(gameId: String): ZIO[Any, AlreadyExistsError, Unit] =
        for {
            gamesMap <- games.get
            _ <- (!gamesMap.contains(gameId)).ifZio(
                onTrue = games.update(_ + (gameId -> List.empty[String])),
                onFalse = ZIO.fail(AlreadyExistsError())
            )
        } yield ()

    def removeGame(gameId: String): UIO[Unit] =
        games.update(_.filterNot { case (gameId, _) => gameId == gameId })

    def joinGame(gameId: String, userId: String): ZIO[Any, GameNotFoundError, Unit] =
        for {
            gamesMap <- games.get
            users <- ZIO.fromOption(gamesMap.get(gameId)).orElseFail(GameNotFoundError())
            _ <- games.update(_ + (gameId -> (users :+ userId)))
        } yield ()
}

object AppState {
    val live: ZLayer[Any, Nothing, AppState] =
        ZLayer(
            for {
                users <- Ref.make(List.empty[String])
                games <- Ref.make(Map.empty[String, List[String]])
            } yield AppState(users, games)
        )
}
