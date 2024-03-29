package com.javapapers.webservices.rest.jersey;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Path("/products")
public class ProductsResource {

	@Context
	UriInfo uriInfo;
	
	@Context
	Request request;

	ProductService productService;

	public ProductsResource() {
		productService = new ProductService();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON )
	public List<Product> getProducts() {
		return productService.getProductAsList();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProductsAsHtml() {
		return productService.getProductAsList();
	}

	// URI: /rest/products/count
	@GET
	@Path("count")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCount() {
		return String.valueOf(productService.getProductsCount());
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void createProduct(@FormParam("id") String id,
			@FormParam("productname") String name,
			@FormParam("productcategory") String category,
			@Context HttpServletResponse servletResponse) throws IOException {
		Product product = new Product(id, name, category);
		productService.createProduct(product);
		servletResponse.sendRedirect("./products/");
	}

	@Path("{product}")
	public ProductResource getProduct(@PathParam("product") String id) {
		return new ProductResource(uriInfo, request, id);
	}

}