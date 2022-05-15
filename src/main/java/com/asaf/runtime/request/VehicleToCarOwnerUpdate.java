package com.asaf.runtime.request;

import com.asaf.runtime.model.VehicleToCarOwner;
import com.fasterxml.jackson.annotation.JsonIgnore;

/** Object Used to Update VehicleToCarOwner */
@com.asaf.runtime.validation.IdValid.List({
  @com.asaf.runtime.validation.IdValid(
      targetField = "vehicleToCarOwner",
      field = "id",
      fieldType = com.asaf.runtime.model.VehicleToCarOwner.class,
      groups = {com.asaf.runtime.validation.Update.class})
})
public class VehicleToCarOwnerUpdate extends VehicleToCarOwnerCreate {

  private String id;

  @JsonIgnore private VehicleToCarOwner vehicleToCarOwner;

  /** @return id */
  public String getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return VehicleToCarOwnerUpdate
   */
  public <T extends VehicleToCarOwnerUpdate> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /** @return vehicleToCarOwner */
  @JsonIgnore
  public VehicleToCarOwner getVehicleToCarOwner() {
    return this.vehicleToCarOwner;
  }

  /**
   * @param vehicleToCarOwner vehicleToCarOwner to set
   * @return VehicleToCarOwnerUpdate
   */
  public <T extends VehicleToCarOwnerUpdate> T setVehicleToCarOwner(
      VehicleToCarOwner vehicleToCarOwner) {
    this.vehicleToCarOwner = vehicleToCarOwner;
    return (T) this;
  }
}
