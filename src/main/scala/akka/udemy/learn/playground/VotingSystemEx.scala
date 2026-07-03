package akka.udemy.learn.playground

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object VotingSystemEx extends App {

  val actorSystem = ActorSystem("votingSystemEx")

  private case class SetCandidate(candidate: String)
  private case object PleaseVote
  private case class VotedFor(candidate: Option[String])
  private class CitizenActor extends Actor {
    private var candidate: Option[String] = None
    def receive: Receive = {
      case SetCandidate(c) => candidate = Some(c)
      case PleaseVote => sender() ! VotedFor(candidate)
    }
  }

  private case class StartVotePolling(citizen: Set[ActorRef])
  private class ElectionCommission extends Actor {
    private var stillWaiting: Set[ActorRef] = Set()
    private var currentStatus: Map[String, Int] = Map()

    def receive: Receive = {
      case StartVotePolling(citizens) =>
        stillWaiting = citizens
        citizens.foreach(citizenRef => citizenRef ! PleaseVote)
      case VotedFor(None) => sender() ! PleaseVote
      case VotedFor(Some(candidate)) =>
        val newStillWaiting = stillWaiting - sender()
        val currentVotesOfCandidate = currentStatus.getOrElse(candidate, 0)
        currentStatus = currentStatus + (candidate -> (currentVotesOfCandidate + 1))
        if(newStillWaiting.isEmpty) println(s"[aggregator] poll stats: $currentStatus")
        else stillWaiting = newStillWaiting
    }
  }

  private val alice = actorSystem.actorOf(Props[CitizenActor], "alice")
  private val bob = actorSystem.actorOf(Props[CitizenActor], "bob")
  private val charlie = actorSystem.actorOf(Props[CitizenActor], "charlie")
  private val daniel = actorSystem.actorOf(Props[CitizenActor], "daniel")

  alice ! SetCandidate("Martin")
  bob ! SetCandidate("Jonas")
  charlie ! SetCandidate("Roland")
  daniel ! SetCandidate("Roland")

  private val electionCommission = actorSystem.actorOf(Props[ElectionCommission], "electionCommission")
  electionCommission ! StartVotePolling(Set(alice, bob, charlie, daniel))
}
