package com.dnweb.springmvcshoeshop.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dnweb.springmvcshoeshop.dao.AccountDAO;
import com.dnweb.springmvcshoeshop.dao.ProductDAO;
import com.dnweb.springmvcshoeshop.entities.Account;
import com.dnweb.springmvcshoeshop.entities.CustomerOrder;
import com.dnweb.springmvcshoeshop.entities.OrderDetail;
import com.dnweb.springmvcshoeshop.entities.Product;
import com.dnweb.springmvcshoeshop.model.CartInfo;
import com.dnweb.springmvcshoeshop.model.CartLineInfo;
import com.dnweb.springmvcshoeshop.model.CustomerInfo;
import com.dnweb.springmvcshoeshop.model.OrderDetailInfo;
import com.dnweb.springmvcshoeshop.model.OrderInfo;
import com.dnweb.springmvcshoeshop.model.PaginationResult;

@Transactional
@Repository
public class OrderDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private AccountDAO accountDAO;

	// Lay ra so hieu ban ghi lon nhat hien tai
	private int getMaxOrderNum() {
		 String sql = "Select max(o.ordernum) from " + CustomerOrder.class.getName() + " o ";
		 
		Session session = sessionFactory.getCurrentSession();
		
		//Criteria criteria = session
		//	    .createCriteria(Order.class)
		//	    .setProjection(Projections.max("ordernum"));
		Query query = session.createQuery(sql);
		Number value = (Number)query.uniqueResult();
		
		// Number value = (Number)criteria.uniqueResult();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>MAX:" + value);
		
		//Query query = session.createQuery(sql);
		//Integer value = (Integer) query.uniqueResult();
		
		if (value == null) {
			return 0;
		}
		
		return value.intValue(); 
	}

	// Luu hoa don moi
	public void saveOrder(CartInfo cartInfo) {
		Session session = sessionFactory.getCurrentSession();

		int orderNum = this.getMaxOrderNum() + 1;
		CustomerOrder order = new CustomerOrder();

		order.setId(UUID.randomUUID().toString()); 
		order.setOrdernum(orderNum);
		order.setCreated(new Date());
		order.setAmount(cartInfo.getAmountTotal());

		CustomerInfo customerInfo = cartInfo.getCustomerInfo();
		Account account = this.accountDAO.findAccountByUsername(customerInfo.getUsername());

		order.setAccount(account);

		session.persist(order);

		List<CartLineInfo> lines = cartInfo.getCartLines();

		for (CartLineInfo line : lines) {
			OrderDetail detail = new OrderDetail();

			detail.setId(UUID.randomUUID().toString());
			detail.setCustomerOrder(order);
			detail.setAmount(line.getAmount());
			detail.setPrice(line.getProductInfo().getPrice());
			detail.setQuantity(line.getQuantity());

			String code = line.getProductInfo().getId();
			Product product = this.productDAO.findProduct(code);
			detail.setProduct(product);

			session.persist(detail);
		}

		// Set OrderNum for report.
		cartInfo.setOrderNum(orderNum);
	}

	// @page = 1, 2, ...

	public PaginationResult<OrderInfo> listOrderInfo(int page, int maxResult, int maxNavigationPage) {

		// Chon ra nhieu cot dung hibernate + java bean
		String sql = "Select new " + OrderInfo.class.getName()//
				+ "(ord.id, ord.created, ord.ordernum, ord.amount, "//
				+ " ord.account.name, ord.account.address, "
				+ "ord.account.email, ord.account.phone) "
				+ " from " + CustomerOrder.class.getName() + " ord " 
				+ " order by ord.ordernum desc";

		Session session = this.sessionFactory.getCurrentSession();

		Query query = session.createQuery(sql);

		return new PaginationResult<OrderInfo>(query, page, maxResult, maxNavigationPage);
	}

	// Tim kiem don hang theo id
	public CustomerOrder findOrder(String orderId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(CustomerOrder.class);
		crit.add(Restrictions.eq("id", orderId));
		return (CustomerOrder) crit.uniqueResult();
	}

	// Lay ra thong tin don hang
	public OrderInfo getOrderInfo(String orderId) {
		CustomerOrder order = this.findOrder(orderId);
		if (order == null) {
			return null;
		}

		return new OrderInfo(order.getId(), order.getCreated(), //
				order.getOrdernum(), order.getAmount(), order.getAccount().getName(), //
				order.getAccount().getAddress(), order.getAccount().getEmail(), order.getAccount().getPhone());
	}

	// Dua ra danh sach thong tin don hang
	public List<OrderDetailInfo> listOrderDetailInfos(String orderId) {

		String sql = "Select new " + OrderDetailInfo.class.getName() //
				+ "(d.id, d.product.id, d.product.name , d.quantity,d.price,d.amount) "//
				+ " from " + OrderDetail.class.getName() + " d "//
				+ " where d.customerOrder.id = :orderId ";

		Session session = this.sessionFactory.getCurrentSession();

		Query query = session.createQuery(sql);
		query.setParameter("orderId", orderId);

		return query.list();
	}

	public void deleteOrder(String orderId) {
		Session session = this.sessionFactory.getCurrentSession();

		CustomerOrder order = this.findOrder(orderId);

		if (order != null) {
			session.delete(order);
		}
	}
}
