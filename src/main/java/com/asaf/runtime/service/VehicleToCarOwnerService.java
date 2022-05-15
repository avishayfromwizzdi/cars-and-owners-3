package com.asaf.runtime.service;

import com.asaf.runtime.data.VehicleToCarOwnerRepository;
import com.asaf.runtime.model.VehicleToCarOwner;
import com.asaf.runtime.request.VehicleToCarOwnerCreate;
import com.asaf.runtime.request.VehicleToCarOwnerFilter;
import com.asaf.runtime.request.VehicleToCarOwnerUpdate;
import com.asaf.runtime.response.PaginationResponse;
import com.asaf.runtime.security.UserSecurityContext;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.metamodel.SingularAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleToCarOwnerService {

  @Autowired private VehicleToCarOwnerRepository repository;

  /**
   * @param vehicleToCarOwnerCreate Object Used to Create VehicleToCarOwner
   * @param securityContext
   * @return created VehicleToCarOwner
   */
  public VehicleToCarOwner createVehicleToCarOwner(
      VehicleToCarOwnerCreate vehicleToCarOwnerCreate, UserSecurityContext securityContext) {
    VehicleToCarOwner vehicleToCarOwner =
        createVehicleToCarOwnerNoMerge(vehicleToCarOwnerCreate, securityContext);
    this.repository.merge(vehicleToCarOwner);
    return vehicleToCarOwner;
  }

  /**
   * @param vehicleToCarOwnerCreate Object Used to Create VehicleToCarOwner
   * @param securityContext
   * @return created VehicleToCarOwner unmerged
   */
  public VehicleToCarOwner createVehicleToCarOwnerNoMerge(
      VehicleToCarOwnerCreate vehicleToCarOwnerCreate, UserSecurityContext securityContext) {
    VehicleToCarOwner vehicleToCarOwner = new VehicleToCarOwner();
    vehicleToCarOwner.setId(UUID.randomUUID().toString());
    updateVehicleToCarOwnerNoMerge(vehicleToCarOwner, vehicleToCarOwnerCreate);

    return vehicleToCarOwner;
  }

  /**
   * @param vehicleToCarOwnerCreate Object Used to Create VehicleToCarOwner
   * @param vehicleToCarOwner
   * @return if vehicleToCarOwner was updated
   */
  public boolean updateVehicleToCarOwnerNoMerge(
      VehicleToCarOwner vehicleToCarOwner, VehicleToCarOwnerCreate vehicleToCarOwnerCreate) {
    boolean update = false;

    if (vehicleToCarOwnerCreate.getCarOwner() != null
        && (vehicleToCarOwner.getCarOwner() == null
            || !vehicleToCarOwnerCreate
                .getCarOwner()
                .getId()
                .equals(vehicleToCarOwner.getCarOwner().getId()))) {
      vehicleToCarOwner.setCarOwner(vehicleToCarOwnerCreate.getCarOwner());
      update = true;
    }

    if (vehicleToCarOwnerCreate.getVehicle() != null
        && (vehicleToCarOwner.getVehicle() == null
            || !vehicleToCarOwnerCreate
                .getVehicle()
                .getId()
                .equals(vehicleToCarOwner.getVehicle().getId()))) {
      vehicleToCarOwner.setVehicle(vehicleToCarOwnerCreate.getVehicle());
      update = true;
    }

    return update;
  }
  /**
   * @param vehicleToCarOwnerUpdate
   * @param securityContext
   * @return vehicleToCarOwner
   */
  public VehicleToCarOwner updateVehicleToCarOwner(
      VehicleToCarOwnerUpdate vehicleToCarOwnerUpdate, UserSecurityContext securityContext) {
    VehicleToCarOwner vehicleToCarOwner = vehicleToCarOwnerUpdate.getVehicleToCarOwner();
    if (updateVehicleToCarOwnerNoMerge(vehicleToCarOwner, vehicleToCarOwnerUpdate)) {
      this.repository.merge(vehicleToCarOwner);
    }
    return vehicleToCarOwner;
  }

  /**
   * @param vehicleToCarOwnerFilter Object Used to List VehicleToCarOwner
   * @param securityContext
   * @return PaginationResponse<VehicleToCarOwner> containing paging information for
   *     VehicleToCarOwner
   */
  public PaginationResponse<VehicleToCarOwner> getAllVehicleToCarOwners(
      VehicleToCarOwnerFilter vehicleToCarOwnerFilter, UserSecurityContext securityContext) {
    List<VehicleToCarOwner> list =
        listAllVehicleToCarOwners(vehicleToCarOwnerFilter, securityContext);
    long count =
        this.repository.countAllVehicleToCarOwners(vehicleToCarOwnerFilter, securityContext);
    return new PaginationResponse<>(list, vehicleToCarOwnerFilter.getPageSize(), count);
  }

  /**
   * @param vehicleToCarOwnerFilter Object Used to List VehicleToCarOwner
   * @param securityContext
   * @return List of VehicleToCarOwner
   */
  public List<VehicleToCarOwner> listAllVehicleToCarOwners(
      VehicleToCarOwnerFilter vehicleToCarOwnerFilter, UserSecurityContext securityContext) {
    return this.repository.listAllVehicleToCarOwners(vehicleToCarOwnerFilter, securityContext);
  }

  public <T, I> List<T> listByIds(Class<T> c, SingularAttribute<T, I> idField, Set<I> ids) {
    return repository.listByIds(c, idField, ids);
  }

  public <T, I> T getByIdOrNull(Class<T> c, SingularAttribute<T, I> idField, I id) {
    return repository.getByIdOrNull(c, idField, id);
  }

  public void merge(java.lang.Object base) {
    this.repository.merge(base);
  }

  public void massMerge(List<?> toMerge) {
    this.repository.massMerge(toMerge);
  }
}
