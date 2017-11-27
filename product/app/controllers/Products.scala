package controllers

import models.{Product, ProductService}
import javax.inject._

import play.api.i18n.{I18nSupport, Messages}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

//import for managing Forms
import play.api.data._
import play.api.data.Forms._

@Singleton
class Products @Inject()(val cc: ControllerComponents,
                         val productService: ProductService)(implicit ec: ExecutionContext)
                        extends AbstractController(cc) with I18nSupport{
  val productForm: Form[Product] = Form{
    mapping(
      "id"-> longNumber,
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

  def list = Action.async { implicit request => // Controller action
    //val products = Product.findAll // get a product list from model
    productService.findAll.map { products =>
      Ok(views.html.products.list(products))
    }
  }

  def show(ean: Long) = Action.async { implicit request =>
    productService.findByEan(ean).map {product =>
      Ok(views.html.products.details(product))
    }
  }
}
