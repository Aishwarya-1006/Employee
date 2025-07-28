package net.exampleproject.ems.controller;

import lombok.AllArgsConstructor;
import net.exampleproject.ems.dto.AddressDto;
import net.exampleproject.ems.response.CustomResponse;
import net.exampleproject.ems.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;

    private final String ADDRESS_CREATED = "Address created successfully";
    private final String ADDRESS_FETCHED = "Address fetched successfully";
    private final String ALL_ADDRESSES_FETCHED = "All addresses fetched successfully";
    private final String ADDRESS_UPDATED = "Address updated successfully";
    private final String ADDRESS_DELETED = "Address deleted successfully";

    @PostMapping
    public ResponseEntity<CustomResponse> createAddress(@RequestBody AddressDto dto) {
        AddressDto createdaddress = addressService.createAddress(dto);
        HttpStatus status = HttpStatus.CREATED;
        CustomResponse response = new CustomResponse(
          ADDRESS_CREATED,
          createdaddress,
          true,
          status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomResponse> getAddressById(@PathVariable Long id) {
        AddressDto address = addressService.getAddressById(id);
        HttpStatus status = HttpStatus.OK;
        CustomResponse response = new CustomResponse(
                ADDRESS_FETCHED,
                address,
                true,
                status.value()+ " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    @GetMapping
    public ResponseEntity<CustomResponse> getAllAddresses() {
        List<AddressDto> addresses = addressService.getAllAddresses();
        HttpStatus status = HttpStatus.OK;
        CustomResponse response = new CustomResponse(
                ALL_ADDRESSES_FETCHED,
                addresses,
                true,
                status.value()  + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomResponse> updateAddress(@PathVariable("id") Long id, @RequestBody AddressDto dto) {
        AddressDto updatedAddress = addressService.updateAddress(id, dto);
        HttpStatus status = HttpStatus.OK;
        CustomResponse response = new CustomResponse(
                ADDRESS_UPDATED,
                updatedAddress,
                true,
                status.value()  + " "+ status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CustomResponse> deleteAddress(@PathVariable("id") Long id) {
        addressService.deleteAddress(id);
        HttpStatus status = HttpStatus.OK;
        CustomResponse response = new CustomResponse(
                ADDRESS_DELETED,
                null,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }
}
