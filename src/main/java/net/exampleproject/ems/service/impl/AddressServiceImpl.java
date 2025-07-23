package net.exampleproject.ems.service.impl;

import net.exampleproject.ems.exception.ResourceNotFoundException;
import net.exampleproject.ems.mapper.AddressMapper;
import net.exampleproject.ems.dto.AddressDto;
import net.exampleproject.ems.entity.Address;
import net.exampleproject.ems.repository.AddressRepo;
import net.exampleproject.ems.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;

    @Autowired
    public AddressServiceImpl(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }

    @Override
    public AddressDto createAddress(AddressDto dto) {
        Address address = AddressMapper.mapToAddress(dto);
        Address saved = addressRepo.save(address);
        return AddressMapper.mapToAddressDto(saved);
    }

    @Override
    public AddressDto getAddressById(Long id) {
        Address address = addressRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + id));
        return AddressMapper.mapToAddressDto(address);
    }

    @Override
    public List<AddressDto> getAllAddresses() {
        return addressRepo.findAll()
                .stream()
                .map(AddressMapper::mapToAddressDto)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDto updateAddress(Long id, AddressDto dto) {
        Address address = addressRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + id));
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        return AddressMapper.mapToAddressDto(addressRepo.save(address));
    }

    @Override
    public void deleteAddress(Long id) {
        if (!addressRepo.existsById(id)) {
            throw new ResourceNotFoundException("Address not found with ID: " + id);
        }
        addressRepo.deleteById(id);
    }

}
