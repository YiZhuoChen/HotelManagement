package uml.hotel.dao;

import java.util.Date;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uml.hotel.model.RoomStatus;

/**
 * A data access object (DAO) providing persistence and search support for
 * RoomStatus entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see uml.hotel.dao.RoomStatus
 * @author MyEclipse Persistence Tools
 */

public class RoomStatusDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(RoomStatusDAO.class);
	// property constants
	public static final String USER_ID = "userId";
	public static final String START_TIME = "startTime";
	public static final String END_TIME = "endTime";
	public static final String ROOM_ID = "roomId";
	public static final String DEPOSIT = "deposit";
	public static final String TIME = "time";
	public static final String HAS_REMINDED = "hasReminded";
	public static final String TYPE = "type";

	public void save(RoomStatus transientInstance) {
		log.debug("saving RoomStatus instance");
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

	public void delete(RoomStatus persistentInstance) {
		log.debug("deleting RoomStatus instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
		
	}

	public RoomStatus findById(java.lang.Integer id) {
		log.debug("getting RoomStatus instance with id: " + id);
		try {
			RoomStatus instance = (RoomStatus) getSession().get(
					"uml.hotel.model.RoomStatus", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(RoomStatus instance) {
		log.debug("finding RoomStatus instance by example");
		try {
			List results = getSession()
					.createCriteria("uml.hotel.model.RoomStatus")
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
		log.debug("finding RoomStatus instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from RoomStatus as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List findByStartTime(Object startTime) {
		return findByProperty(START_TIME, startTime);
	}

	public List findByEndTime(Object endTime) {
		return findByProperty(END_TIME, endTime);
	}

	public List findByRoomId(Object roomId) {
		return findByProperty(ROOM_ID, roomId);
	}

	public List findByDeposit(Object deposit) {
		return findByProperty(DEPOSIT, deposit);
	}

	public List findByTime(Object time) {
		return findByProperty(TIME, time);
	}

	public List findByHasReminded(Object hasReminded) {
		return findByProperty(HAS_REMINDED, hasReminded);
	}

	public List findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List findAll() {
		log.debug("finding all RoomStatus instances");
		try {
			String queryString = "from RoomStatus";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public RoomStatus merge(RoomStatus detachedInstance) {
		log.debug("merging RoomStatus instance");
		try {
			RoomStatus result = (RoomStatus) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(RoomStatus instance) {
		log.debug("attaching dirty RoomStatus instance");
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

	public void attachClean(RoomStatus instance) {
		log.debug("attaching clean RoomStatus instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}