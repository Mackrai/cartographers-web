package io.cartographers.app.dto.response

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec

case class JoinGameResponse(gameId: String)

object JoinGameResponse {
    implicit lazy val codec: Codec[JoinGameResponse] = deriveCodec[JoinGameResponse]
}