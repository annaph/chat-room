package com.akkachat

import akka.actor.Actor
import akka.actor.ActorLogging

class Chatroom extends Actor with ActorLogging {

  log.info("actor startup: {}", self.toString)

  var joinedUsers: Seq[UserRef] = Seq.empty
  var chatHistory: Seq[PostToChatroom] = Seq.empty

  override def receive = {
    case x: JoinChatroom =>
      sender ! join(x)
    case x: PostToChatroom =>
      notify(x)
    case _ =>
      println("received unknown message")
  }

  def join(joinChatroom: JoinChatroom): Seq[PostToChatroom] = {
    joinedUsers = joinedUsers :+ joinChatroom.userRef
    chatHistory
  }

  def notify(postToChatroom: PostToChatroom): Unit = {
    chatHistory = chatHistory :+ postToChatroom
    joinedUsers foreach { _.actorRef ! postToChatroom }
  }

}
