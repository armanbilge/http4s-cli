//> using scala "3.1.1"
//> using jsVersion "1.9.0"
//> using jsHeader "#!/usr/bin/env node\n"
//> using jsEmitSourceMaps "true"
//> using jsModuleKind "CommonJS"
//> using lib "org.http4s::http4s-ember-server::0.23.8"
//> using lib "com.monovore::decline-effect::2.2.0"

import cats.effect.*
import cats.syntax.all.*
import com.comcast.ip4s.*
import com.monovore.decline.*
import com.monovore.decline.effect.*
import org.http4s.ember.server.*
import org.http4s.server
import org.http4s.server.staticcontent.*

object Http4sCli
    extends CommandIOApp(
      name = "http4s",
      header = "static file server, backed by http4s ember",
      version = "0.1.0"
    ):

  val port = Opts
    .argument[Int](metavar = "port")
    .withDefault(8000)
    .mapValidated(p =>
      Port.fromInt(p).toRightNel(s"Invalid port: $p").toValidated
    )

  val bind = Opts
    .option[String](
      "bind",
      short = "b",
      metavar = "ADDRESS",
      help = "Specify alternate bind address [default: all interfaces]"
    )
    .withDefault("0.0.0.0")
    .mapValidated(a =>
      IpAddress.fromString(a).toRightNel(s"Invalid address: $a").toValidated
    )

  val dir = Opts
    .option[String](
      "directory",
      short = "d",
      metavar = "DIRECTORY",
      help = "Specify alternative directory [default: current directory]"
    )
    .withDefault(".")

  def routes(path: String) = fileService[IO](FileService.Config(path))

  def main = (port, bind, dir).mapN { (p, b, d) =>
    EmberServerBuilder
      .default[IO]
      .withHost(b)
      .withPort(p)
      .withHttpApp(routes(d).orNotFound)
      .build
      .evalTap(s =>
        IO.println(
          s"Serving HTTP on ${s.address.host} port ${s.address.port} (http://${s.address}/)"
        )
      )
      .useForever
  }
