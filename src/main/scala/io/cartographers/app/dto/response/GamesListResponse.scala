package io.cartographers.app.dto.response

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec

case class GamesListResponse(games: List[String])

object GamesListResponse {
    implicit lazy val codec: Codec[GamesListResponse] = deriveCodec[GamesListResponse]
}
