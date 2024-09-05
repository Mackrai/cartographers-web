ThisBuild / version := "0.1"

ThisBuild / scalaVersion := "3.5.0"

lazy val root = (project in file("."))
    .settings(
        name := "cartographers-web",
        libraryDependencies ++=
            Dependencies.zio ++
                Dependencies.zioHttp ++
                Dependencies.zioQuill ++
                Dependencies.zioLogging ++
                Dependencies.logback ++
                Dependencies.postgresql
    )
