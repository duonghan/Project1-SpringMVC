package com.dnweb.springmvcshoeshop.dao;

import java.util.ArrayList;
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

import com.dnweb.springmvcshoeshop.entities.Category;
import com.dnweb.springmvcshoeshop.model.CategoryInfo;

@Transactional
@Repository
public class CategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	// Chú ý: Current Session nó chưa được tạo ra. Trong qua trinh Spring dang
	// còn tạo Bean, và tiem giá trị
	// Vì vậy các chỗ này ko dc đâu.
	// Phải tự tạo trong mỗi phương thức

	//Tim kiem danh muc san pham
	public Category findCategory(String id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Category.class);
		crit.add(Restrictions.eq("id", id));
		return (Category) crit.uniqueResult();
	}

	//Tim kiem thong tin danh muc san pham
	public CategoryInfo findCategoryInfo(String id) {

		Category category = this.findCategory(id);

		if (category == null) {
			return null;
		}
		return new CategoryInfo(category);
	}

	//Them moi hoac sua doi thong tin danh muc san pham
	public void saveCategory(CategoryInfo categoryInfo) {
		Session session = sessionFactory.getCurrentSession();
		
		String id = categoryInfo.getId();
		
		Category category = null;
		
		if (id != null) {
			category = this.findCategory(id);
		}
		
		boolean isNew = false;
		
		if (category == null) {
			isNew = true;
			category = new Category();
			category.setId(UUID.randomUUID().toString());
		}
		
		category.setName(categoryInfo.getName());
		
		if (isNew) {
			session.persist(category);
		}
		
		session.flush();
	}

	//Hien thi ra cac danh muc san pham
	public List<Category> getAllCategory() {
		Session session = sessionFactory.getCurrentSession();

		String sql = "Select c.* " + " from " + CategoryInfo.class.getName()
				+ " c ";
		Query query = session.createQuery(sql);
		return query.list();
	}
	
	//Xoa danh muc san pham
//	public void deleteCategory(String categoryId){
//		Session session = sessionFactory.getCurrentSession();
//		
//		String sql = "Delete " + Category.class.getName().toLowerCase() + " c "
//				+"where c.id := categoryid";
//		
//		Query query = session.createQuery(sql);
//		query.setParameter("categoryid", categoryId);
//		
//		query.executeUpdate();
//	}

	public void deleteCategory(String categoryId){
		Session session = this.sessionFactory.getCurrentSession();
		Category category = this.findCategory(categoryId);
		
		if (category != null) {
			session.delete(category);
		}
	}

}
