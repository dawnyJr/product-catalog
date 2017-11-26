package controllers

case class StockItem(
                      id: Long,
                      productId: Long,
                      warehouseId: Long,
                      quantity: Long)
