package net.exampleproject.ems.service;

import net.exampleproject.ems.dto.AddressDto;

import java.util.List;

public interface AddressService {
    AddressDto createAddress(AddressDto dto);
    AddressDto getAddressById(Long id);
    List<AddressDto> getAllAddresses();
    AddressDto updateAddress(Long id, AddressDto dto);
    void deleteAddress(Long id);
}
