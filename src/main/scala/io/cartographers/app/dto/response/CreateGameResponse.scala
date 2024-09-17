package io.cartographers.app.dto.response

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec

case class CreateGameResponse(gameId: String)

object CreateGameResponse {
    implicit lazy val codec: Codec[CreateGameResponse] = deriveCodec[CreateGameResponse]
}
