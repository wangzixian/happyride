package io.vividcode.happyride.passengerservice.support;

import com.github.javafaker.Faker;
import io.vividcode.happyride.passengerservice.api.web.CreatePassengerRequest;
import io.vividcode.happyride.passengerservice.api.web.CreateUserAddressRequest;
import io.vividcode.happyride.passengerservice.api.web.PassengerView;
import io.vividcode.happyride.passengerservice.api.web.UserAddressView;
import io.vividcode.happyride.passengerservice.domain.Passenger;
import io.vividcode.happyride.passengerservice.domain.UserAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

public class PassengerUtils {

  private static final Faker faker = new Faker(Locale.CHINA);

  public static CreatePassengerRequest buildCreatePassengerRequest(int numberOfAddresses) {
    CreatePassengerRequest request = new CreatePassengerRequest();
    request.setName(faker.name().name());
    request.setEmail(faker.internet().emailAddress());
    request.setMobilePhoneNumber(faker.phoneNumber().phoneNumber());
    int count = Math.max(0, numberOfAddresses);
    List<CreateUserAddressRequest> addresses = new ArrayList<>(count);
    for (int i = 0; i < count; i++) {
      addresses.add(buildCreateUserAddressRequest());
    }
    request.setUserAddresses(addresses);
    return request;
  }

  public static CreateUserAddressRequest buildCreateUserAddressRequest() {
    CreateUserAddressRequest request = new CreateUserAddressRequest();
    request.setName(faker.pokemon().name());
    request.setAddressId(UUID.randomUUID().toString());
    return request;
  }

  public static PassengerView createPassengerView(Passenger passenger) {
    return new PassengerView(passenger.getId(),
        passenger.getName(),
        passenger.getEmail(),
        passenger.getMobilePhoneNumber(),
        passenger.getUserAddresses().stream().map(PassengerUtils::createUserAddressView)
            .collect(Collectors.toList()));
  }

  public static UserAddressView createUserAddressView(UserAddress userAddress) {
    return new UserAddressView(userAddress.getId(),
        userAddress.getName(),
        userAddress.getAddressId());
  }
}
