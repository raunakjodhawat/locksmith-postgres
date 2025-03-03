package com.raunakjodhawat.locksmith.postgres.db

import cats.effect.IO
import cats.syntax.parallel.*
import cats.effect.unsafe.IORuntime
import com.raunakjodhawat.locksmith.postgres.config.{DBConfig, LockConfig}
import doobie.Transactor

import scala.concurrent.duration.FiniteDuration

object DB {
  private val dbConfig: IO[DBConfig] = IO.fromOption(DBConfig())(new Exception("Unable to load config"))
  private val lockConfig: IO[LockConfig] = IO.fromOption(LockConfig())(new Exception("Unable to load lock config"))

  val configs: IO[(DBConfig, LockConfig)] = (dbConfig, lockConfig).parMapN((db, lock) => (db, lock))

  def createTable(): Unit = {
    val transactor: Transactor[IO] = for {
      (dC, lC) <- configs
      tableName = lC.tableName
      jdbcUrl =
        s"jdbc:postgresql://${dC.postgres.properties.user}:${dC.postgres.properties.password}@${dC.postgres.properties.serverName}/${dC.postgres.properties.databaseName}"
    } yield Transactor.fromDriverManager[IO]("org.postgresql.Driver",
                                             jdbcUrl,
                                             dC.postgres.properties.user,
                                             dC.postgres.properties.password,
                                             None
    )
  }
  def lock(key: String, instanceId: String, duration: FiniteDuration)(implicit runtime: IORuntime): Boolean = IO
    .parSequenceN(2)(List(dbConfig, lockConfig))
    .map { case List(dbConfig, lockConfig) =>
      (locksmithConfig, lockConfig)
    }
    .unsafeRunSync() match {
    case (Some(locksmithConfig), Some(lockConfig)) => true
    case _                                         => false
  }
  // get the two config in parallel

  // create a table if it does not exist
  // id: PrimaryKey, AutoInc
  // key: String, Unique
  // instanceId: String
  // createdAt: Timestamp
  // auto delete expired locks

  // attempt to write to DB with key, if successful return true else false

}
