package io.cartographers.app.dto.request

import io.cartographers.app.dto.{GameData, RequestBase}
import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec

case class CreateGameRequest(userId: String, 
                             gameData: GameData) extends RequestBase

object CreateGameRequest {
    implicit lazy val codec: Codec[CreateGameRequest] = deriveCodec[CreateGameRequest]
}
