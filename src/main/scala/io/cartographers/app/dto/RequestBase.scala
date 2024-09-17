package io.cartographers.app.dto

import io.cartographers.app.dto.request.{CreateGameRequest, JoinRequest}
import io.circe.*

trait RequestBase

object RequestBase {
    implicit lazy val decoder: Decoder[RequestBase] =
        (c: HCursor) =>
            c.get[String]("code").flatMap {
                case "create" => c.get[CreateGameRequest]("data")
                case "join" => c.get[JoinRequest]("data")
                case code => Left(DecodingFailure(s"Unknown code: [$code]", Nil))
            }
}
