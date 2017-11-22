package controllers

import models.Product
import javax.inject._

import play.api.i18n.I18nSupport
import play.api.mvc._



class Products @Inject()(val cc: ControllerComponents) extends AbstractController(cc) with I18nSupport{
    def list = Action { implicit request => // Controller action
    val products = Product.findAll // get a product list from model
    Ok(views.html.products.list(products)) // Render view template
  }
    def show(ean: Long) = Action{implicit request =>
      Product.findByEan(ean).map{product =>
        Ok(views.html.products.details(product))
      }.getOrElse(NotFound)
    }
}
