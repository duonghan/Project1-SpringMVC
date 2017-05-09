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

import com.dnweb.springmvcshoeshop.entities.Order;
import com.dnweb.springmvcshoeshop.entities.Product;
import com.dnweb.springmvcshoeshop.model.PaginationResult;
import com.dnweb.springmvcshoeshop.model.ProductInfo;

@Transactional
@Repository
public class ProductDAO {
	@Autowired
	private SessionFactory sessionFactory;

	// ok

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

	//Them hoac sua san pham
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
			isNew = true;// Cờ báo rằng đây là bản ghi mới
			product = new Product();
			product.setCreated(new Date());
		}
		product.setId(id);
		product.setName(productInfo.getName());
		product.setPrice(productInfo.getPrice());

		if (productInfo.getFileData() != null) {
			byte[] image = productInfo.getFileData().getBytes();
			if (image != null && image.length > 0) {
				product.setImage(image);
			}
		}
		if (isNew) {
			// Khi la moi thì phải persist!!
			session.persist(product);
		}
		// If error in DB, Exceptions will be thrown out immediately
		// Nếu có lỗi tại DB, ngoại lệ sẽ ném ra ngay lập tức
		session.flush();
	}

	// Query sản phẩm theo trag
	public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage,
			String likeName) {

		String sql = "Select new " + ProductInfo.class.getName() //
				+ "(p.id, p.name, p.price) " + " from "//
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
		// Rồi đưa vào PaginationResult: Giúp lay ra dung các bản ghi theo phan
		// trang!
		return new PaginationResult<ProductInfo>(query, page, maxResult, maxNavigationPage);
	}

	// Xoa san pham
//	public void deleteProduct(String productId) {
//		Session session = sessionFactory.getCurrentSession();
//
//		String sql = "Delete " + Product.class.getName().toLowerCase() + " p " + "where p.id =: productId ";
//
//		Query query = session.createQuery(sql);
//
//		query.setParameter("productId", productId);
//
//		query.executeUpdate();
//	}
	
	public void deleteProduct(String productId){
		Session session = sessionFactory.getCurrentSession();
		
		Product product =  this.findProduct(productId);
		if (product != null) {
			session.delete(product);
		}
		
	}

	//query san pham theo trang
	public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage) {
		return queryProducts(page, maxResult, maxNavigationPage, null);
	}
}
