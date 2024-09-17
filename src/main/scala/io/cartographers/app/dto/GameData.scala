package io.cartographers.app.dto

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec

case class GameData(title: String)

object GameData {
    implicit lazy val codec: Codec[GameData] = deriveCodec[GameData]
}
