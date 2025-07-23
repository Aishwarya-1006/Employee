package net.exampleproject.ems.controller;

import lombok.AllArgsConstructor;
import net.exampleproject.ems.dto.AddressDto;
import net.exampleproject.ems.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDto> createAddress(@RequestBody AddressDto dto) {
        return ResponseEntity.ok(addressService.createAddress(dto));
    }

    @GetMapping("{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    @PutMapping("{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable("id") Long id, @RequestBody AddressDto dto) {
        return ResponseEntity.ok(addressService.updateAddress(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable("id") Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok("Address deleted successfully");
    }
}
