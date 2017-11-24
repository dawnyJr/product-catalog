package controllers

import models.Product
import javax.inject._

import play.api.i18n.{I18nSupport, Messages}
import play.api.mvc._
import play.mvc.Controller
import play.mvc.Http.Context.Implicit

//import for managing Forms
import play.api.data._
import play.api.data.Forms._


class Products @Inject()(val cc: ControllerComponents) extends AbstractController(cc) with I18nSupport{
  val productForm: Form[Product] = Form{
    mapping(
      "ean" -> longNumber.verifying("validation.ean.duplicate",Product.findByEan(_).isEmpty),
      "name" -> nonEmptyText,
      "description" -> nonEmptyText
    )(Product.apply)(Product.unapply)
  }

  def save = Action {implicit request =>
  productForm.bindFromRequest.fold(
    hasErrors = {form =>
      Redirect(routes.Products.newProduct).flashing(Flash(form.data) +
        ("error" -> Messages("validation.errors")))
    },
    success = {newProduct =>
      Product.add(newProduct)
      val message = Messages("products.new.success",newProduct.name)
      Redirect(routes.Products.show(newProduct.ean)).flashing("success" -> message)
    }
  )
  }

  def newProduct = Action { implicit request =>
    val form = if (request.flash.get("error").isDefined)
      productForm.bind(request.flash.data)
    else
      productForm
    Ok(views.html.products.editProduct(form))
  }

  def list = Action { implicit request => // Controller action
    val products = Product.findAll // get a product list from model
    Ok(views.html.products.list(products)) // Render view template
  }

  def show(ean: Long) = Action { implicit request =>
    Product.findByEan(ean).map { product =>
      Ok(views.html.products.details(product))
    }.getOrElse(NotFound)
  }




}