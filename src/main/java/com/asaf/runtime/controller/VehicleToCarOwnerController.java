package com.asaf.runtime.controller;

import com.asaf.runtime.model.VehicleToCarOwner;
import com.asaf.runtime.request.VehicleToCarOwnerCreate;
import com.asaf.runtime.request.VehicleToCarOwnerFilter;
import com.asaf.runtime.request.VehicleToCarOwnerUpdate;
import com.asaf.runtime.response.PaginationResponse;
import com.asaf.runtime.security.UserSecurityContext;
import com.asaf.runtime.service.VehicleToCarOwnerService;
import com.asaf.runtime.validation.Create;
import com.asaf.runtime.validation.Update;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("VehicleToCarOwner")
@Tag(name = "VehicleToCarOwner")
public class VehicleToCarOwnerController {

  @Autowired private VehicleToCarOwnerService vehicleToCarOwnerService;

  @PostMapping("getAllVehicleToCarOwners")
  @Operation(summary = "getAllVehicleToCarOwners", description = "lists VehicleToCarOwners")
  public PaginationResponse<VehicleToCarOwner> getAllVehicleToCarOwners(
      @Valid @RequestBody VehicleToCarOwnerFilter vehicleToCarOwnerFilter,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return vehicleToCarOwnerService.getAllVehicleToCarOwners(
        vehicleToCarOwnerFilter, securityContext);
  }

  @PostMapping("createVehicleToCarOwner")
  @Operation(summary = "createVehicleToCarOwner", description = "Creates VehicleToCarOwner")
  public VehicleToCarOwner createVehicleToCarOwner(
      @Validated(Create.class) @RequestBody VehicleToCarOwnerCreate vehicleToCarOwnerCreate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return vehicleToCarOwnerService.createVehicleToCarOwner(
        vehicleToCarOwnerCreate, securityContext);
  }

  @PutMapping("updateVehicleToCarOwner")
  @Operation(summary = "updateVehicleToCarOwner", description = "Updates VehicleToCarOwner")
  public VehicleToCarOwner updateVehicleToCarOwner(
      @Validated(Update.class) @RequestBody VehicleToCarOwnerUpdate vehicleToCarOwnerUpdate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return vehicleToCarOwnerService.updateVehicleToCarOwner(
        vehicleToCarOwnerUpdate, securityContext);
  }
}
