package uml.hotel.dao;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uml.hotel.model.Room;

/**
 * A data access object (DAO) providing persistence and search support for Room
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see uml.hotel.dao.Room
 * @author MyEclipse Persistence Tools
 */

public class RoomDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(RoomDAO.class);
	// property constants
	public static final String STATUS = "status";
	public static final String NUMBER = "number";
	public static final String TYPE = "type";
	public static final String PHONE_NUM = "phoneNum";
	public static final String COST = "cost";
	public static final String PEOPLE_COUNT = "peopleCount";
	public static final String LOCATION = "location";

	public void save(Room transientInstance) {
		log.debug("saving Room instance");
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

	public void delete(Room persistentInstance) {
		log.debug("deleting Room instance");
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

	public Room findById(java.lang.Integer id) {
		log.debug("getting Room instance with id: " + id);
		try {
			Room instance = (Room) getSession().get("uml.hotel.model.Room", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Room instance) {
		log.debug("finding Room instance by example");
		try {
			List results = getSession().createCriteria("uml.hotel.dao.Room")
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
		log.debug("finding Room instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Room as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByNumber(Object number) {
		return findByProperty(NUMBER, number);
	}

	public List findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List findByPhoneNum(Object phoneNum) {
		return findByProperty(PHONE_NUM, phoneNum);
	}

	public List findByCost(Object cost) {
		return findByProperty(COST, cost);
	}

	public List findByPeopleCount(Object peopleCount) {
		return findByProperty(PEOPLE_COUNT, peopleCount);
	}
	
	public List findByLocation(Object location) {
		return findByProperty(LOCATION, location);
	}
	
	public List findByRoomTypePrefix(String prefix) {
		getSession().beginTransaction();  
		String strSQL = "from Room as model where model.number like :number";  		  
		Query query = getSession().createQuery(strSQL);  
		query.setString("number", prefix +"%");  		         
		List result = query.list();  
		return result;
	}

	public List findAll() {
		log.debug("finding all Room instances");
		try {
			String queryString = "from Room";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Room merge(Room detachedInstance) {
		log.debug("merging Room instance");
		try {
			Room result = (Room) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Room instance) {
		log.debug("attaching dirty Room instance");
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

	public void attachClean(Room instance) {
		log.debug("attaching clean Room instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}