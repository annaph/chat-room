package com.akkachat

import java.io.File

import scala.util.Failure
import scala.util.Success
import scala.util.Try

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

import akka.actor.ActorSystem
import akka.actor.Props

object Main extends App {

  val log: Logger = LoggerFactory.getLogger(getClass)

  val configFile: Try[String] = Try {
    getClass.getClassLoader.getResource("akkachat.conf").getFile
  }
  val config: Try[Config] = configFile map { f =>
    ConfigFactory.parseFile(new File(f))
  }

  config match {
    case Success(c) =>
      val system = ActorSystem("akkachat", c)
      system.actorOf(Props(classOf[Chatroom]), "chatroom")
    case Failure(e) =>
      log.error("Error starting actor system")
  }

}
