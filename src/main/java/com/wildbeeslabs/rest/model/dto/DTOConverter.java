/*
 * The MIT License
 *
 * Copyright 2017 Pivotal Software, Inc..
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.rest.model.dto;

import com.wildbeeslabs.rest.controller.ABaseController;
import com.wildbeeslabs.rest.model.BaseEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alex
 */
@Service("dtoConverter")
public final class DTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public <M extends BaseEntity, N extends BaseDTO> N convertToDTO(final M itemEntity, final Class<? extends N> clazz) {
        N itemDto = modelMapper.map(itemEntity, clazz);
        return itemDto;
    }

    public <M extends BaseEntity, N extends BaseDTO> BaseDTOListWrapper<? extends N> convertToDTO(final List<? extends M> itemEntityList, final Class<? extends N> clazz, final Class<? extends BaseDTOListWrapper> classListWrapper) {
        LinkedList<N> ll = new LinkedList<>();
        //Class<? extends N> cl = (Class<? extends N>) LinkedList.class;
        return null;
    }

    public <M extends BaseEntity, N extends BaseDTO> BaseDTOListWrapper<? extends N> convertToDTOList(final List<? extends M> itemEntityList, final Class<? extends N> clazz, final Class<? extends BaseDTOListWrapper> classListWrapper) {
        List<? extends N> itemDtoList = itemEntityList.stream()
                .map(item -> convertToDTO(item, clazz))
                .collect(Collectors.toCollection(LinkedList::new));
        BaseDTOListWrapper<N> listWrapper = null;
        try {
            listWrapper = classListWrapper.newInstance();
            listWrapper.setItems(itemDtoList);
        } catch (InstantiationException | IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ABaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listWrapper;
    }

    public <N extends BaseDTO, M extends BaseEntity> M convertToEntity(final N itemDto, final Class<? extends M> clazz) {
        M itemEntity = modelMapper.map(itemDto, clazz);
        return itemEntity;
    }

    public <N extends BaseDTO, M extends BaseEntity> List<? extends M> convertToEntityList(final BaseDTOListWrapper<? extends N> itemDtoList, final Class<? extends M> clazz) {
        List<M> itemEntityList = itemDtoList.getItems().stream()
                .map(item -> convertToEntity(item, clazz))
                .collect(Collectors.toCollection(LinkedList::new));
        return itemEntityList;
    }

    //    protected T convertToEntity(final E itemDto, final Class<? extends T> clazz) {
//        T itemEntity = modelMapper.map(itemDto, clazz);
//        return itemEntity;
//    }
    //    protected E convertToDTO(final T itemEntity, final Class<? extends E> clazz) {
//        E itemDto = modelMapper.map(itemEntity, clazz);
//        return itemDto;
//        //return this.convertToDTO(itemEntity, clazz);
//    }
}
