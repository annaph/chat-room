package com.akkachat

import akka.actor.ActorRef

case class UserRef(actorRef: ActorRef, name: String)
