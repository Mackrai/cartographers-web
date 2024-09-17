package io.cartographers.app.dto.request

import io.cartographers.app.dto.RequestBase
import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec

case class JoinRequest(gameId: String, userId: String) extends RequestBase

object JoinRequest {
    implicit lazy val codec: Codec[JoinRequest] = deriveCodec[JoinRequest]
}
