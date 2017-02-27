package com.akkachat

import scala.concurrent.duration.DurationInt

import org.scalatest.FunSpec
import org.scalatest.Matchers

import akka.actor.ActorSystem
import akka.actor.Props
import akka.testkit.TestActorRef
import akka.testkit.TestProbe

class ChatroomSpec extends FunSpec with Matchers {

  val system = ActorSystem()

  describe("Given a Chatroom has no users") {
    val props: Props = Props.create(classOf[Chatroom])
    val ref: TestActorRef[Chatroom] = TestActorRef.create(system, props, "testChatroom")
    val chatroom: Chatroom = ref.underlyingActor

    chatroom.joinedUsers.length should equal(0)

    describe("when it receives a request from a user to join the chatroom") {
      val userRef = UserRef(system.deadLetters, "user")
      ref ! JoinChatroom(userRef)

      it("should add the UserRef to the list of joined user") {
        chatroom.joinedUsers.head should equal(userRef)
      }
    }
  }

  describe("Given a Chatroom has a history") {
    val props: Props = Props.create(classOf[Chatroom])
    val ref: TestActorRef[Chatroom] = TestActorRef.create(system, props, "testChatroom2")
    val chatroom: Chatroom = ref.underlyingActor

    val msg = PostToChatroom("test", "user")
    chatroom.chatHistory = chatroom.chatHistory :+ msg

    describe("When a user joins the chatroom") {
      val probe = new TestProbe(system)
      val userRef = UserRef(probe.ref, "user")
      ref tell (JoinChatroom(userRef), probe.ref)

      it("(the user) should recieve the history") {
        probe.expectMsg(1 second, List(msg))
      }
    }
  }

  describe("Given a Chatroom has a joined user") {
    val props: Props = Props.create(classOf[Chatroom])
    val ref: TestActorRef[Chatroom] = TestActorRef.create(system, props, "testChatroom3")
    val chatroom: Chatroom = ref.underlyingActor

    val probe1 = new TestProbe(system)
    val userRef1 = UserRef(probe1.ref, "user1")
    val probe2 = new TestProbe(system)
    val userRef2 = UserRef(probe2.ref, "user2")

    chatroom.join(JoinChatroom(userRef1))
    chatroom.join(JoinChatroom(userRef2))

    describe("When someone posts to the chatroom") {
      val msg = PostToChatroom("test", "user1")
      ref tell (msg, probe1.ref)

      it("(joined users) should get an update") {
        probe1.expectMsg(msg)
        probe2.expectMsg(msg)
      }

      it("(chatroom) should add the post message to the chat history") {
        chatroom.chatHistory.head should equal(msg)
      }
    }
  }

}
