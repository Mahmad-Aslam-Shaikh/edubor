package com.enotes_api.utility;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MapperUtil {

    private final ModelMapper modelMapper;

    // Generic method to map one object to another
    public <D, T> D map(T source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    // Optional: List conversion
    public <D, T> List<D> mapList(List<T> sourceList, Class<D> destinationType) {
        return sourceList.stream()
                .map(element -> modelMapper.map(element, destinationType))
                .collect(Collectors.toList());
    }
}