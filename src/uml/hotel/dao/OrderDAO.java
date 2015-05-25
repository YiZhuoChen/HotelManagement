package uml.hotel.dao;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uml.hotel.model.Order;

/**
 * A data access object (DAO) providing persistence and search support for Order
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see uml.hotel.dao.Order
 * @author MyEclipse Persistence Tools
 */

public class OrderDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(OrderDAO.class);
	// property constants
	public static final String ARRIVE_TIME = "arriveTime";
	public static final String ORDER_TIME = "orderTime";
	public static final String TYPE = "type";
	public static final String STATE = "state";
	public static final String ROOM_NUM = "roomNum";
	public static final String USER_NAME = "userName";
	public static final String COMPANY = "company";
	public static final String TEL = "tel";
	public static final String USER_FROM = "userFrom";
	public static final String HAS_REMINDED = "hasReminded";

	public void save(Order transientInstance) {
		log.debug("saving Order instance");
		Transaction trans = getSession().beginTransaction();
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		trans.commit();
		getSession().flush();
		getSession().close();
	}

	public void delete(Order persistentInstance) {
		log.debug("deleting Order instance");
		Transaction trans = getSession().beginTransaction();
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
		trans.commit();
		getSession().flush();
		getSession().close();
	}

	public Order findById(java.lang.Integer id) {
		log.debug("getting Order instance with id: " + id);
		try {
			Order instance = (Order) getSession()
					.get("uml.hotel.model.Order", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Order instance) {
		log.debug("finding Order instance by example");
		try {
			List results = getSession().createCriteria("uml.hotel.model.Order")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Order instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Order as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByArriveTime(Object arriveTime) {
		return findByProperty(ARRIVE_TIME, arriveTime);
	}

	public List findByOrderTime(Object orderTime) {
		return findByProperty(ORDER_TIME, orderTime);
	}

	public List findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findByRoomNum(Object roomNum) {
		return findByProperty(ROOM_NUM, roomNum);
	}

	public List findByUserName(Object userName) {
		return findByProperty(USER_NAME, userName);
	}

	public List findByCompany(Object company) {
		return findByProperty(COMPANY, company);
	}

	public List findByTel(Object tel) {
		return findByProperty(TEL, tel);
	}

	public List findByUserFrom(Object userFrom) {
		return findByProperty(USER_FROM, userFrom);
	}

	public List findByHasReminded(Object hasReminded) {
		return findByProperty(HAS_REMINDED, hasReminded);
	}

	public List findAll() {
		log.debug("finding all Order instances");
		try {
			String queryString = "from Order";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Order merge(Order detachedInstance) {
		log.debug("merging Order instance");
		try {
			Order result = (Order) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Order instance) {
		log.debug("attaching dirty Order instance");
		Transaction trans = getSession().beginTransaction();
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
		trans.commit();
		getSession().flush();
		getSession().close();
	}

	public void attachClean(Order instance) {
		log.debug("attaching clean Order instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}