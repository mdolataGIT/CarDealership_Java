package jsf.hockshop.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jsf.hockshop.entities.Photo;

@Stateless
public class PhotoDAO {
	private final static String UNIT_NAME = "hockshop-simplePU";
	
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	
	public void create(Photo photo) {
		em.persist(photo);
	}

	public Photo merge (Photo photo) {
		return em.merge(photo);
	}
	
	public void remove (Photo photo) {
		em.remove(em.merge(photo));
	}
	
	public Photo find(Object id) {
		return em.find(Photo.class,id);
	}
	
	public List<Photo> getFullList(){
		List<Photo> list = null;
		
		Query query = em.createQuery("select c from Photo c");
		
		try {
			list = query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
	}
	return list;
	}
	
	public List<Photo>getList(Map<String,Object> searchParams){
		List<Photo> list= null;
		
		String select = "select c ";
		String from = " from Photo c ";
		String where = "";
		String orderby = "order by c.url";
		
		String url = (String) searchParams.get("url");
		if (url!=null) {
			if(where.isEmpty()) {
				where="where ";
			}else {
				where+="and ";
			}
			where +="c.url like :url ";
		}
		
		Integer carId = (Integer) searchParams.get("carId");
		if (carId!=null) {
			if(where.isEmpty()) {
				where="where ";
			}else {
				where+="and ";
			}
			where +=" c.car.idCar = :idCa ";
		}		
		
		Query query = em.createQuery(select + from + where + orderby);
		
		if(url != null) {
			query.setParameter("url", url+"%");
		}
		
		if(carId!=null) {
			query.setParameter("idCa",carId);
		}
		
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
}
