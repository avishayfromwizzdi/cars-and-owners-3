package com.asaf.runtime.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class VehicleToCarOwner {

  @ManyToOne(targetEntity = Vehicle.class)
  private Vehicle vehicle;

  @ManyToOne(targetEntity = CarOwner.class)
  private CarOwner carOwner;

  @Id private String id;

  /** @return vehicle */
  @ManyToOne(targetEntity = Vehicle.class)
  public Vehicle getVehicle() {
    return this.vehicle;
  }

  /**
   * @param vehicle vehicle to set
   * @return VehicleToCarOwner
   */
  public <T extends VehicleToCarOwner> T setVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
    return (T) this;
  }

  /** @return carOwner */
  @ManyToOne(targetEntity = CarOwner.class)
  public CarOwner getCarOwner() {
    return this.carOwner;
  }

  /**
   * @param carOwner carOwner to set
   * @return VehicleToCarOwner
   */
  public <T extends VehicleToCarOwner> T setCarOwner(CarOwner carOwner) {
    this.carOwner = carOwner;
    return (T) this;
  }

  /** @return id */
  @Id
  public String getId() {
    return this.id;
  }

  /**
   * @param id id to set
   * @return VehicleToCarOwner
   */
  public <T extends VehicleToCarOwner> T setId(String id) {
    this.id = id;
    return (T) this;
  }
}
