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
import com.dnweb.springmvcshoeshop.entities.Order;
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
			// Cho nay no tim xem trong DB co product chua??
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
	
	//cai cho nay a ạ
	//câu truy vấn của e nó bị sai chỗ nà v a
	//e chạy nó báo lỗi 
	// Xong roi day
	//vay ve cach viet la no k sai a nhi
	
	public PaginationResult<ProductInfo> queryProductsCategory(int page, int maxResult, int maxNavigationPage,
			String categoryId, String likeName) {

		// Cach viet 1: (Join 2 bang, ko can dien kien on, vi hibernate tu biet DK).
		//ok a
		String sql = "Select new " + ProductInfo.class.getName()
				+ "(p.id, p.name, p.price, p.description, p.discount, c.id) " + " from "
				+ Product.class.getName() + " p " + " join p.category c " ;
		
	// Cach viet 2: (Cach nay khong can join, su dung thuoc tinh p.category.id ==> De truy van Id cua Category
		String sql2 = "Select new " + ProductInfo.class.getName()
				+ "(p.id, p.name, p.price, p.description, p.discount, p.category.id) " + " from "
				+ Product.class.getName() +" p "  ;
		
//				+ Category.class.getName() + " c " + " on "
			//	+ " p.category.id = c.id";

		if (likeName != null && likeName.length() > 0) {
			sql += " Where lower(p.name) like :likeName ";
		}
		sql += " order by p.created desc ";

		Session session = sessionFactory.getCurrentSession();

		// Co duoc doi tuong Query
		Query query = session.createQuery(sql);
		if (likeName != null && likeName.length() > 0) {
			query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
		}

		return new PaginationResult<ProductInfo>(query, page, maxResult, maxNavigationPage);
	}

	// Danh sach cac san pham moi
	public PaginationResult<ProductInfo> listNewProduct(int page, int maxResult, int maxNavigationPage) {

		String sql = "Select new " + ProductInfo.class.getName()
				+ "(p.id, p.name, p.price, p.description, p.discount, c.id) " + " from "
				+ Product.class.getName() + " p "// 
				+ " join p.category c " // + " on " "p.category.id = c.id" // Khong can dieu kien on! vi p.category (Da tu join roi)
				+ " order by p.created desc ";

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);

		return new PaginationResult<ProductInfo>(query, page, maxResult, maxNavigationPage);
	}

	// Danh sach cac san pham ban chay
	public PaginationResult<ProductInfo> listPopulerProduct(int page, int maxResult, int maxNavigationPage) {

		String sql = "Select new " + ProductInfo.class.getName()
				+ "((p.id, p.name, p.price, p.description, p.discount, p.category.id)) " + " from "
				+ Product.class.getName() + " p " + " join " + OrderDetail.class.getName() 
				+ " o " + " on " + "p.id = o.product.id" 
				+ " order by o.quantity desc ";

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

	// query san pham theo trang
	public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage) {
		return queryProducts(page, maxResult, maxNavigationPage, null);
	}
}
