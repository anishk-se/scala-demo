package com.scala.demo.wrapper.futures

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}
import scala.util.{Failure, Success}

object PromiseApp extends App {

  val anish = ChatUser(1, "Anish")
  val rahul = ChatUser(2, "Rahul")
  val chatMessage = ChatMessage(id = 101, from = anish, to = rahul, text = "Hello Rahul!")

  val deliveryStatusFuture = ChatServer.sendChatMessage(chatMessage)
  println("[Anish] Waiting for delivery status...")
  deliveryStatusFuture.onComplete {
    case Success(status) => println(s"[Anish] ChatMessage status = $status")
    case Failure(ex) => println(ex.getMessage)
  }
  Future {
    Thread.sleep(3000) // Simulate Rahul reading the ChatMessage after 3 seconds
    println("[Rahul] Opens the chat.")
    ChatServer.readChatMessage(101)
  }
  Thread.sleep(7000) // Keep the Main thread running

}

object ChatServer {
  private val pendingPromises = mutable.Map[Int, Promise[DeliveryStatus]]()

  def sendChatMessage(ChatMessage: ChatMessage): Future[DeliveryStatus] = {
    println(s"[Server] ${ChatMessage.from.name} sent ChatMessage to ${ChatMessage.to.name}")
    val promise = Promise[DeliveryStatus]()
    // Store the promise until receiver reads the ChatMessage
    pendingPromises += (ChatMessage.id -> promise)
    println(s"[Server] Waiting for ${ChatMessage.to.name} to read the ChatMessage...")
    promise.future
  }

  def readChatMessage(ChatMessageId: Int): Unit = {
    println(s"[Server] ChatMessage $ChatMessageId has been read.")
    pendingPromises.get(ChatMessageId) match {
      case Some(promise) => 
        promise.success(Delivered)
        pendingPromises -= ChatMessageId
      case None => println(s"[Server] No pending promise found.")
    }
  }

}

case class ChatUser(id: Int, name: String)
case class ChatMessage(id: Int, from: ChatUser, to: ChatUser, text: String)

sealed trait DeliveryStatus
case object Delivered extends DeliveryStatus