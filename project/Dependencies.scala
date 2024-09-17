import sbt.*

object Dependencies {
    object V {
        val Circe = "0.14.10"
        val Zio = "2.1.9"
        val ZioConfig = "4.0.2"
        val ZioHttp = "3.0.0-RC9"
        val ZioQuill = "4.8.4"
        val ZioLogging = "2.3.1"
        val Logback = "1.5.7"
        val Postgresql = "42.3.1"
    }

    val circe: Seq[ModuleID] = Seq(
        "io.circe" %% "circe-core",
        "io.circe" %% "circe-generic",
        "io.circe" %% "circe-parser"
    ).map(_ % V.Circe)

    val zio: Seq[ModuleID] =
        Seq(
            "dev.zio" %% "zio",
        ).map(_ % V.Zio)

    val zioConfig: Seq[ModuleID] =
        Seq(
            "dev.zio" %% "zio-config",
            "dev.zio" %% "zio-config-magnolia",
        ).map(_ % V.ZioConfig)

    val zioHttp: Seq[ModuleID] =
        Seq(
            "dev.zio" %% "zio-http",
        ).map(_ % V.ZioHttp)

    val zioQuill: Seq[ModuleID] =
        Seq(
            "io.getquill" %% "quill-jdbc-zio",
        ).map(_ % V.ZioQuill)

    val zioLogging: Seq[ModuleID] =
        Seq(
            "dev.zio" %% "zio-logging",
            "dev.zio" %% "zio-logging-slf4j2",
        ).map(_ % V.ZioLogging)

    val logback: Seq[ModuleID] =
        Seq(
            "ch.qos.logback" % "logback-classic",
        ).map(_ % V.Logback)

    val postgresql: Seq[ModuleID] =
        Seq(
            "org.postgresql" % "postgresql",
        ).map(_ % V.Postgresql)

}
