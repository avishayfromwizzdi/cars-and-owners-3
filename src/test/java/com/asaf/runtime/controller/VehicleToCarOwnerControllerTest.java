package com.asaf.runtime.controller;

import com.asaf.runtime.AppInit;
import com.asaf.runtime.model.CarOwner;
import com.asaf.runtime.model.Vehicle;
import com.asaf.runtime.model.VehicleToCarOwner;
import com.asaf.runtime.request.LoginRequest;
import com.asaf.runtime.request.VehicleToCarOwnerCreate;
import com.asaf.runtime.request.VehicleToCarOwnerFilter;
import com.asaf.runtime.request.VehicleToCarOwnerUpdate;
import com.asaf.runtime.response.PaginationResponse;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AppInit.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class VehicleToCarOwnerControllerTest {

  private VehicleToCarOwner testVehicleToCarOwner;
  @Autowired private TestRestTemplate restTemplate;

  @Autowired private CarOwner carOwner;

  @Autowired private Vehicle vehicle;

  @BeforeAll
  private void init() {
    ResponseEntity<Object> authenticationResponse =
        this.restTemplate.postForEntity(
            "/login",
            new LoginRequest().setUsername("admin@flexicore.com").setPassword("admin"),
            Object.class);
    String authenticationKey =
        authenticationResponse.getHeaders().get(HttpHeaders.AUTHORIZATION).stream()
            .findFirst()
            .orElse(null);
    restTemplate
        .getRestTemplate()
        .setInterceptors(
            Collections.singletonList(
                (request, body, execution) -> {
                  request.getHeaders().add("Authorization", "Bearer " + authenticationKey);
                  return execution.execute(request, body);
                }));
  }

  @Test
  @Order(1)
  public void testVehicleToCarOwnerCreate() {
    VehicleToCarOwnerCreate request = new VehicleToCarOwnerCreate();

    request.setCarOwnerId(this.carOwner.getId());

    request.setVehicleId(this.vehicle.getId());

    ResponseEntity<VehicleToCarOwner> response =
        this.restTemplate.postForEntity(
            "/VehicleToCarOwner/createVehicleToCarOwner", request, VehicleToCarOwner.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testVehicleToCarOwner = response.getBody();
    assertVehicleToCarOwner(request, testVehicleToCarOwner);
  }

  @Test
  @Order(2)
  public void testListAllVehicleToCarOwners() {
    VehicleToCarOwnerFilter request = new VehicleToCarOwnerFilter();
    ParameterizedTypeReference<PaginationResponse<VehicleToCarOwner>> t =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<VehicleToCarOwner>> response =
        this.restTemplate.exchange(
            "/VehicleToCarOwner/getAllVehicleToCarOwners",
            HttpMethod.POST,
            new HttpEntity<>(request),
            t);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<VehicleToCarOwner> body = response.getBody();
    Assertions.assertNotNull(body);
    List<VehicleToCarOwner> VehicleToCarOwners = body.getList();
    Assertions.assertNotEquals(0, VehicleToCarOwners.size());
    Assertions.assertTrue(
        VehicleToCarOwners.stream().anyMatch(f -> f.getId().equals(testVehicleToCarOwner.getId())));
  }

  public void assertVehicleToCarOwner(
      VehicleToCarOwnerCreate request, VehicleToCarOwner testVehicleToCarOwner) {
    Assertions.assertNotNull(testVehicleToCarOwner);

    if (request.getCarOwnerId() != null) {

      Assertions.assertNotNull(testVehicleToCarOwner.getCarOwner());
      Assertions.assertEquals(request.getCarOwnerId(), testVehicleToCarOwner.getCarOwner().getId());
    }

    if (request.getVehicleId() != null) {

      Assertions.assertNotNull(testVehicleToCarOwner.getVehicle());
      Assertions.assertEquals(request.getVehicleId(), testVehicleToCarOwner.getVehicle().getId());
    }
  }

  @Test
  @Order(3)
  public void testVehicleToCarOwnerUpdate() {
    VehicleToCarOwnerUpdate request =
        new VehicleToCarOwnerUpdate().setId(testVehicleToCarOwner.getId());
    ResponseEntity<VehicleToCarOwner> response =
        this.restTemplate.exchange(
            "/VehicleToCarOwner/updateVehicleToCarOwner",
            HttpMethod.PUT,
            new HttpEntity<>(request),
            VehicleToCarOwner.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testVehicleToCarOwner = response.getBody();
    assertVehicleToCarOwner(request, testVehicleToCarOwner);
  }
}
