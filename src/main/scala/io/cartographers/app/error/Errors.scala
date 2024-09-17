package io.cartographers.app.error

trait AppError extends Throwable

case class AlreadyExistsError() extends AppError

case class GameNotFoundError() extends AppError
