package example

import sangria.schema._
import sangria.macros._
import sangria.marshalling.circe._
import io.circe.Json
import io.circe.literal._
import sangria.execution.Executor
import cats.effect.IOApp
import scala.concurrent.ExecutionContext
import cats.effect.{ExitCode, IO}
import cats.effect.std.Dispatcher
import fs2.io.net.Network
import com.comcast.ip4s.Port
import com.comcast.ip4s._
import fs2.io.net.unixsocket.UnixSockets
import fs2.io.net.unixsocket.UnixSocketAddress
import cats.syntax.all._

object Hello extends IOApp.Simple {

  override def run: IO[Unit] = {
    Dispatcher.parallel[IO].use { dispatcher =>
      val builder = AstSchemaBuilder.resolverBased[Any](
        FieldResolver.map[Any](
          "Query" -> Map(
            "accountById" -> (_ => dispatcher.unsafeToFuture(response))
          )
        ),
        AnyFieldResolver.defaultInput[Any, Json]
      )

      val schema = Schema.buildFromAst(schemaAst, builder)
      implicit val compute: ExecutionContext = runtime.compute
      val result = IO.fromFuture(IO(Executor.execute(schema, query)))
      result.flatMap(IO.println)
    }

  }

  val schemaAst =
    gql"""
     scalar DateTime

    type Account {
      id: String!
      createdAt: DateTime!
    }

    type Query {
      accountById(id: String!): Account
    }
  """

  val query = gql"""
    query Account {
        accountById(id:"30003453") {
            id
            createdAt
        }
    }
  """

  val response = IO.pure(json"""
        {
          "id": "1000",
          "createdAt": "Luke Skywalker"
        }
        """)
}
