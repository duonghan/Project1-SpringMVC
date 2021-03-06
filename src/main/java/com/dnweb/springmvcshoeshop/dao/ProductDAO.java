package com.dnweb.springmvcshoeshop.dao;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dnweb.springmvcshoeshop.entities.Category;
import com.dnweb.springmvcshoeshop.entities.OrderDetail;
import com.dnweb.springmvcshoeshop.entities.Product;
import com.dnweb.springmvcshoeshop.model.PaginationResult;
import com.dnweb.springmvcshoeshop.model.ProductInfo;

@Transactional
@Repository
public class ProductDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private CategoryDAO categoryDAO;

//	@Autowired
//	private ProductInfo productInfo;
	
	
	// Tim kiem san pham theo id
	public Product findProduct(String id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Product.class);
		crit.add(Restrictions.eq("id", id));
		return (Product) crit.uniqueResult();
	}

	// Tim kiem thong tin san pham theo id
	public ProductInfo findProductInfo(String id) {
		Product product = this.findProduct(id);
		if (product == null) {
			return null;
		}
		return new ProductInfo(product);
	}

	// Them hoac sua san pham
	public void save(ProductInfo productInfo) {

		Session session = sessionFactory.getCurrentSession();

		String id = productInfo.getId();

		Product product = null;

		boolean isNew = false;
		if (id != null) {
			
			product = this.findProduct(id);
		}
		// Neu chua co no moi tao moi!!
		if (product == null) {
			isNew = true;
			product = new Product();
			product.setCreated(new Date());
		}
		product.setId(id);
		product.setName(productInfo.getName());
		product.setPrice(productInfo.getPrice());
		product.setCategory(categoryDAO.findCategory(productInfo.getCategoryId()));
		product.setDescription(productInfo.getDescription());
		product.setDiscount(productInfo.getDiscount());

		if (productInfo.getFileData() != null) {
			byte[] image = productInfo.getFileData().getBytes();
			if (image != null && image.length > 0) {
				product.setImage(image);
			}
		}
		if (isNew) {

			session.persist(product);
		}

		session.flush();
	}

	// Query san pham theo trang
	public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage,
			String likeName) {

		String sql = "Select new " + ProductInfo.class.getName() //
				+ "(p.id, p.name, p.price, p.description, p.discount, p.category.id) " + " from "//
				+ Product.class.getName() + " p ";
		if (likeName != null && likeName.length() > 0) {
			sql += " Where lower(p.name) like :likeName ";
		}
		sql += " order by p.created desc ";
		//
		Session session = sessionFactory.getCurrentSession();

		// Co duoc doi tuong Query
		Query query = session.createQuery(sql);
		if (likeName != null && likeName.length() > 0) {
			query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
		}

		return new PaginationResult<ProductInfo>(query, page, maxResult, maxNavigationPage);
	}

	// Query san pham theo category
	public PaginationResult<ProductInfo> queryProductsCategory(int page, int maxResult, int maxNavigationPage,
			String categoryId, String likeName) {

		// Cach viet 2: (Cach nay khong can join, su dung thuoc tinh
		// p.category.id ==> De truy van Id cua Category
		String sql = "Select new " + ProductInfo.class.getName()
				+ "(p.id, p.name, p.price, p.description, p.discount, p.category.id) " + " from "
				+ Product.class.getName() + " p ";
		
		if (categoryId != null && categoryId.length() > 0) {
			
			sql +="Where lower(p.category.id) like :categoryId";
			
			if (likeName != null && likeName.length() > 0) {
				sql += " and lower(p.name) like :likeName ";
			}
		}
		sql += " order by p.created desc ";

		Session session = sessionFactory.getCurrentSession();

		// Co duoc doi tuong Query
		Query query = session.createQuery(sql);

		if (categoryId != null) {
			query.setParameter("categoryId",categoryId);
		}
		if (likeName != null && likeName.length() > 0) {
			query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
		}

		return new PaginationResult<ProductInfo>(query, page, maxResult, maxNavigationPage);
	}

	// Danh sach cac san pham ban chay
	public PaginationResult<ProductInfo> listPopulerProduct(int page, int maxResult, int maxNavigationPage) {

		String sql = "Select distinct new " + ProductInfo.class.getName()
				+ "(p.id, p.name, p.price, p.description, p.discount, p.category.id) " + " from "
				+ Product.class.getName() + " p " 
				+ " join p.orderdetails o"
				+ " order by o.quantity desc ";

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		return new PaginationResult<ProductInfo>(query, page, maxResult, maxNavigationPage);
	}
	
	// Danh sach cac san pham khuyen mai
		public PaginationResult<ProductInfo> listSalesProduct(int page, int maxResult, int maxNavigationPage) {

			String sql = "Select new " + ProductInfo.class.getName()
					+ "(p.id, p.name, p.price, p.description, p.discount, p.category.id) " + " from "
					+ Product.class.getName() + " p " 
					+ " where p.discount > 0.0 ";

			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(sql);
			return new PaginationResult<ProductInfo>(query, page, maxResult, maxNavigationPage);
		}

	// Xoa san pham
	public void deleteProduct(String productId) {
		Session session = sessionFactory.getCurrentSession();

		Product product = this.findProduct(productId);
		if (product != null) {
			session.delete(product);
		}

	}

	// Query san pham theo trang
	public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage) {
		return queryProducts(page, maxResult, maxNavigationPage, null);
	}
}
