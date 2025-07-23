package net.exampleproject.ems.mapper;

import net.exampleproject.ems.dto.AddressDto;
import net.exampleproject.ems.entity.Address;

public class AddressMapper {

    public static AddressDto mapToAddressDto(Address address) {
        if (address == null) return null;

        AddressDto dto = new AddressDto();
        dto.setAddid(address.getAddid());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setZipCode(address.getZipCode());
        dto.setEmployee(address.getEmployee());
        return dto;
    }

    public static Address mapToAddress(AddressDto dto) {
        if (dto == null) return null;

        Address address = new Address();
        address.setAddid(dto.getAddid());
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        address.setEmployee(dto.getEmployee());
        return address;
    }
}
