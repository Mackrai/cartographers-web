import sbt.*

object Dependencies {
    object V {
        val Zio = "2.1.9"
        val ZioHttp = "3.0.0-RC9"
        val ZioQuill = "4.8.4"
        val ZioLogging = "2.3.1"
        val Logback = "1.5.7"
        val Postgresql = "42.3.1"
    }

    val zio: Seq[ModuleID] =
        Seq(
            "dev.zio" %% "zio",
        ).map(_ % V.Zio)

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
