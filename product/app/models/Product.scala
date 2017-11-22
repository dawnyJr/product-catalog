package models

case class Product(ean: Long, name: String, description: String) // Model class

object Product{ // the Data Access Object : DAO
  var products = Set(
    Product(501820624461L,"Paperclips Large","Large Plain pack of 1000"),
    Product(501820634462L,"Giant Paperclips","Giant Plain 51mm 100 pack"),
    Product(501820644463L,"Paperclips Giant Plain","Giant Plain pack of 10000"),
    Product(501820654464L,"No Tear Paper Clip","No Tear Extra Large Pack of 1000"),
    Product(501820664465L,"Zebra Paperclips","Zebra Legth 28mm Assorted 150 Pack")
  )

  def findAll = products.toList.sortBy(_.ean) // Finder function
  def findByEan(ean: Long) = products.find(_.ean==ean)
}
