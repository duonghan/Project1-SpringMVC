package com.dnweb.springmvcshoeshop.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dnweb.springmvcshoeshop.entities.Account;
import com.dnweb.springmvcshoeshop.model.AccountInfo;
import com.dnweb.springmvcshoeshop.model.CustomerInfo;

//Transactional for Hibernate
@Transactional
@Repository
public class AccountDAO {

	// Neu co Autowire tại day ==> Spring chỉ gán giá trị cho sessionFactory, va
	// ko lam gi khac
	// Trươc khi Spring tiem giá trị cho cái này
	@Autowired // Do thieu cai nay.
	private SessionFactory sessionFactory;  

	// Doan nay chạy, và bị null pointer, vì sessionFactory chua được tiêm
	// vào==> sessionFactory nul
	// Loi nullPointerExcption
	// session = sessionFactory.getCurrentSession();
	// crit = session.createCriteria(Account.class);

	// Vì vậy viết Autowhire tại đây. để khi spring tiem gia trị vao
	// ==> Các cái kia sẽ dược khởi tạo giá trị.
	// Spring no se tu dong goi phuong thuc nay!!

	// Khi do cai nay cung duoc thuc thi!
	// session = sessionFactory.getCurrentSession(); // Vì vậy gọi trong thơi
	// điểm DI (Tiêm) là luon trả về null.
	// crit = session.createCriteria(Account.class);

	// Khong duoc lam the nay.
	// sessionFactory chưa được tiêm vào AccounDAO.

	// Tim kiem tai khoan theo userName
	public Account findAccountByUsername(String userName) {
      try {
    	  // sessionFactory null, vi no chua duoc tiem gia tri vao.
    	  System.out.println("SessionFactory ="+ this.sessionFactory);
    	  
		Session session = sessionFactory.getCurrentSession();// Sau khi spring
																// OK, no moi
																// tao ra
																// Session
		Criteria crit = session.createCriteria(Account.class);
		crit.add(Restrictions.eq("username", userName));
		Account account= (Account) crit.uniqueResult();
		System.out.println("Account "+ account);
		
		return account;
      }catch(Exception e) {
    	  System.out.println("Error: "+ e);
    	  e.printStackTrace( );
    	  return null;
      }
	}

	// Tim kiem tai khoan theo email
	public Account findAccoutByEmail(String email) {

		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Account.class);
		crit.add(Restrictions.eq("email", email));
		return (Account) crit.uniqueResult();
	}

	// Tim kiem thong tin khach hang theo userName
	public CustomerInfo findCustomerInfo(String userName) {
		Account account = this.findAccountByUsername(userName);

		if (account == null) {
			return null;
		}
		return new CustomerInfo(account);
	}

	// dang ky hoac update thong tin nguoi dung
	//a kiem tra xem chỗ này e viết như thế này ddusng chưa
	//e xem bài hd của a là trên hibernate 5 còn khi vào hiber nate 4 đúng k nữa
	
	
	public void saveAccount(AccountInfo accountInfo) {
		Session session = sessionFactory.getCurrentSession();

		String userName = accountInfo.getUsername();
		Account account = null;

		boolean isNew = false;
		if (userName != null) {
			account = this.findAccountByUsername(userName);
		}

		if (account == null) {
			isNew = true;
			account = new Account();
			account.setUsername(userName);
			account.setCreated(new Date());
		}

		account.setPassword(accountInfo.getPassword());
		account.setName(accountInfo.getName());
		account.setEmail(accountInfo.getEmail());
		account.setAddress(accountInfo.getAddress());
		account.setPhone(accountInfo.getPhone());
		account.setGender(accountInfo.getGender());

		// Chay kiem tra đi!
		//tu tu đẫ a, tại còn nhiều chỗ chưa sửa quá
		//đầu tiên là dao đã
		// Trong DAO da co cong them roi
		account.setRole("USER"); // Spring các ROLE phải có tiếp đầu ngữ là ROLE_
		account.setActive(true);

		// dua account ve trang thai persistent
		if (isNew) {
			session.persist(account);
		}
		// chu dong dua du lieu xung DB
		session.flush();
	}

	// Dua ra danh sach tat ca thong tin nguoi dung
	public List<CustomerInfo> listCustomerInfo() {
		String sql = "Select new " + CustomerInfo.class.getName() + //
				"(c.username, c.name, c.email, c.phone, c.address, c.gender) " + //
				" from " + CustomerInfo.class.getName() + " c ";

		Session session = sessionFactory.getCurrentSession();

		Query query = session.createQuery(sql);

		return query.list();
	}

	//Vay con cho xoa tai khoan nay thi sao a
	
	//e thay a co làm theo 2 cach
	
	//vay nen lam theo cai nao a
	
	// Xoa tai khoan
	// public void deleteAccount(String userName){
	//
	// Session session = sessionFactory.getCurrentSession();
	//
	// String sql = "Delete " + Account.class.getName() //a e quen no khong phan biet chua hoa thuong
	
	// +" a " + "where a.username =: userName";
	//
	// Query query = session.createQuery(sql);
	// query.setParameter("userName", userName);
	//
	// query.executeUpdate();
	// }

	// Cach nao cung dc. Ko quan trong 
	// Nhung có thể sử dụng cách này cung dc.
	//ok a
	public void deleteAccount(String userName) {

		Session session = sessionFactory.getCurrentSession();

		Account account = this.findAccountByUsername(userName);

		if (account != null) {
			session.delete(account);
		}
	}

}
