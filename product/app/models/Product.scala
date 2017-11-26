package models
import java.util.Date
import javax.inject.{Inject, Singleton}

import anorm.SqlParser.get
import anorm.{~, _}
import play.api.db.DBApi


case class Product(id: Long, ean: Long, name: String, description: String) // Model class

object Product { // the Data Access Object : DAO
  var products = Set(
    Product(1,501820624461L,"Paperclips Large","Large Plain pack of 1000"),
    Product(2,501820634462L,"Giant Paperclips","Giant Plain 51mm 100 pack"),
    Product(3,501820644463L,"Paperclips Giant Plain","Giant Plain pack of 10000"),
    Product(4,501820654464L,"No Tear Paper Clip","No Tear Extra Large Pack of 1000"),
    Product(5,501820664465L,"Zebra Paperclips","Zebra Legth 28mm Assorted 150 Pack")
  )

  def findAll = products.toList.sortBy(_.ean) // Finder function
  def findByEan(ean: Long) = products.find(_.ean==ean)
  def add(product: Product): Unit = {
    products = products + product
  }


}
@Singleton
class ProductService @Inject()(dbapi: DBApi){
  val DB = dbapi.database("default")
  //using anorm to fetch the database
  private val productParser = {
    get[Long]("products.id") ~
      get[Long]("products.ean") ~
      get[String]("products.name") ~
      get[String]("products.description") map {
      case id~ean~name~description =>
        Product(id,ean,name,description)
    }
  }

  def sql : SqlQuery = SQL("select * from products order by name asc")
  def getAll: List[Product] = DB.withConnection{
    implicit connection =>
      sql.as(productParser.*)
  }
}
