package uml.hotel.dao;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uml.hotel.model.Server;

/**
 * A data access object (DAO) providing persistence and search support for
 * Server entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see uml.hotel.dao.Server
 * @author MyEclipse Persistence Tools
 */

public class ServerDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(ServerDAO.class);
	// property constants
	public static final String ITEM_ID = "itemId";
	public static final String COUNT = "count";
	public static final String ROOM_ID = "roomId";
	public static final String FINISHED = "finished";

	public void save(Server transientInstance) {
		log.debug("saving Server instance");
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

	public void delete(Server persistentInstance) {
		log.debug("deleting Server instance");
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

	public Server findById(java.lang.Integer id) {
		log.debug("getting Server instance with id: " + id);
		try {
			Server instance = (Server) getSession().get("uml.hotel.model.Server",
					id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Server instance) {
		log.debug("finding Server instance by example");
		try {
			List results = getSession().createCriteria("uml.hotel.model.Server")
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
		log.debug("finding Server instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Server as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByItemId(Object itemId) {
		return findByProperty(ITEM_ID, itemId);
	}

	public List findByCount(Object count) {
		return findByProperty(COUNT, count);
	}

	public List findByRoomId(Object roomId) {
		return findByProperty(ROOM_ID, roomId);
	}

	public List findByFinished(Object finished) {
		return findByProperty(FINISHED, finished);
	}
	
	public List findAll() {
		log.debug("finding all Server instances");
		try {
			String queryString = "from Server";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Server merge(Server detachedInstance) {
		log.debug("merging Server instance");
		try {
			Server result = (Server) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Server instance) {
		log.debug("attaching dirty Server instance");
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

	public void attachClean(Server instance) {
		log.debug("attaching clean Server instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}