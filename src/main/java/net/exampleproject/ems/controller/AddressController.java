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

    public final String ADDRESS_CREATED = "Address created successfully";
    public final String ADDRESS_FETCHED = "Address fetched successfully";
    public final String ALL_ADDRESSES_FETCHED = "All addresses fetched successfully";
    public final String ADDRESS_UPDATED = "Address updated successfully";
    public final String ADDRESS_DELETED = "Address deleted successfully";

    // ✅ Create Address
    @PostMapping
    public ResponseEntity<CustomResponse<AddressDto>> createAddress(@RequestBody AddressDto dto) {
        AddressDto created = addressService.createAddress(dto);
        HttpStatus status = HttpStatus.CREATED;
        CustomResponse<AddressDto> response = new CustomResponse<>(
                ADDRESS_CREATED,
                created,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    // ✅ Get Address by ID
    @GetMapping("{id}")
    public ResponseEntity<CustomResponse<AddressDto>> getAddressById(@PathVariable Long id) {
        AddressDto address = addressService.getAddressById(id);
        HttpStatus status = HttpStatus.OK;
        CustomResponse<AddressDto> response = new CustomResponse<>(
                ADDRESS_FETCHED,
                address,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    // ✅ Get All Addresses
    @GetMapping
    public ResponseEntity<CustomResponse<List<AddressDto>>> getAllAddresses() {
        List<AddressDto> addresses = addressService.getAllAddresses();
        HttpStatus status = HttpStatus.OK;
        CustomResponse<List<AddressDto>> response = new CustomResponse<>(
                ALL_ADDRESSES_FETCHED,
                addresses,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    // ✅ Update Address
    @PutMapping("{id}")
    public ResponseEntity<CustomResponse<AddressDto>> updateAddress(@PathVariable("id") Long id, @RequestBody AddressDto dto) {
        AddressDto updated = addressService.updateAddress(id, dto);
        HttpStatus status = HttpStatus.OK;
        CustomResponse<AddressDto> response = new CustomResponse<>(
                ADDRESS_UPDATED,
                updated,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    // ✅ Delete Address
    @DeleteMapping("{id}")
    public ResponseEntity<CustomResponse<Void>> deleteAddress(@PathVariable("id") Long id) {
        addressService.deleteAddress(id);
        HttpStatus status = HttpStatus.OK;
        CustomResponse<Void> response = new CustomResponse<>(
                ADDRESS_DELETED,
                null,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }
}
