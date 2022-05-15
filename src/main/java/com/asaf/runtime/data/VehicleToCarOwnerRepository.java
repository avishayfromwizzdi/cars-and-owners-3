package com.asaf.runtime.data;

import com.asaf.runtime.model.CarOwner;
import com.asaf.runtime.model.CarOwner_;
import com.asaf.runtime.model.Vehicle;
import com.asaf.runtime.model.VehicleToCarOwner;
import com.asaf.runtime.model.VehicleToCarOwner_;
import com.asaf.runtime.model.Vehicle_;
import com.asaf.runtime.request.VehicleToCarOwnerFilter;
import com.asaf.runtime.security.UserSecurityContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class VehicleToCarOwnerRepository {
  @PersistenceContext private EntityManager em;

  /**
   * @param vehicleToCarOwnerFilter Object Used to List VehicleToCarOwner
   * @param securityContext
   * @return List of VehicleToCarOwner
   */
  public List<VehicleToCarOwner> listAllVehicleToCarOwners(
      VehicleToCarOwnerFilter vehicleToCarOwnerFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<VehicleToCarOwner> q = cb.createQuery(VehicleToCarOwner.class);
    Root<VehicleToCarOwner> r = q.from(VehicleToCarOwner.class);
    List<Predicate> preds = new ArrayList<>();
    addVehicleToCarOwnerPredicate(vehicleToCarOwnerFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));
    TypedQuery<VehicleToCarOwner> query = em.createQuery(q);

    if (vehicleToCarOwnerFilter.getPageSize() != null
        && vehicleToCarOwnerFilter.getCurrentPage() != null
        && vehicleToCarOwnerFilter.getPageSize() > 0
        && vehicleToCarOwnerFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(
              vehicleToCarOwnerFilter.getPageSize() * vehicleToCarOwnerFilter.getCurrentPage())
          .setMaxResults(vehicleToCarOwnerFilter.getPageSize());
    }

    return query.getResultList();
  }

  public <T extends VehicleToCarOwner> void addVehicleToCarOwnerPredicate(
      VehicleToCarOwnerFilter vehicleToCarOwnerFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    if (vehicleToCarOwnerFilter.getCarOwner() != null
        && !vehicleToCarOwnerFilter.getCarOwner().isEmpty()) {
      Set<String> ids =
          vehicleToCarOwnerFilter.getCarOwner().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, CarOwner> join = r.join(VehicleToCarOwner_.carOwner);
      preds.add(join.get(CarOwner_.id).in(ids));
    }

    if (vehicleToCarOwnerFilter.getVehicle() != null
        && !vehicleToCarOwnerFilter.getVehicle().isEmpty()) {
      Set<String> ids =
          vehicleToCarOwnerFilter.getVehicle().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Vehicle> join = r.join(VehicleToCarOwner_.vehicle);
      preds.add(join.get(Vehicle_.id).in(ids));
    }

    if (vehicleToCarOwnerFilter.getId() != null && !vehicleToCarOwnerFilter.getId().isEmpty()) {
      preds.add(r.get(VehicleToCarOwner_.id).in(vehicleToCarOwnerFilter.getId()));
    }
  }
  /**
   * @param vehicleToCarOwnerFilter Object Used to List VehicleToCarOwner
   * @param securityContext
   * @return count of VehicleToCarOwner
   */
  public Long countAllVehicleToCarOwners(
      VehicleToCarOwnerFilter vehicleToCarOwnerFilter, UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<VehicleToCarOwner> r = q.from(VehicleToCarOwner.class);
    List<Predicate> preds = new ArrayList<>();
    addVehicleToCarOwnerPredicate(vehicleToCarOwnerFilter, cb, q, r, preds, securityContext);
    q.select(cb.count(r)).where(preds.toArray(new Predicate[0]));
    TypedQuery<Long> query = em.createQuery(q);
    return query.getSingleResult();
  }

  public <T, I> List<T> listByIds(Class<T> c, SingularAttribute<T, I> idField, Set<I> ids) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<T> q = cb.createQuery(c);
    Root<T> r = q.from(c);
    q.select(r).where(r.get(idField).in(ids));
    return em.createQuery(q).getResultList();
  }

  public <T, I> T getByIdOrNull(Class<T> c, SingularAttribute<T, I> idField, I id) {
    return listByIds(c, idField, Collections.singleton(id)).stream().findFirst().orElse(null);
  }

  @Transactional
  public void merge(java.lang.Object base) {
    em.merge(base);
  }

  @Transactional
  public void massMerge(List<?> toMerge) {
    for (Object o : toMerge) {
      em.merge(o);
    }
  }
}
