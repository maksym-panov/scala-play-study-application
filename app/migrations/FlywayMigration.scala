package migrations

import play.api.inject.{SimpleModule, _}
import javax.inject._
import play.api._
import org.flywaydb.core.Flyway

@Singleton 
class FlywayMigration @Inject()(config: Configuration) {
  private val flyway = Flyway.configure()
    .dataSource(
      config.getOptional[String]("slick.dbs.default.db.url").get,
      config.getOptional[String]("slick.dbs.default.db.user").get,
      config.getOptional[String]("slick.dbs.default.db.password").get
    )
    .locations(config.getOptional[String]("flyway.locations").getOrElse("classpath:db/migration"))
    .load()

  flyway.migrate()
}

class FlywayModule extends SimpleModule(bind[FlywayMigration].toSelf.eagerly())
