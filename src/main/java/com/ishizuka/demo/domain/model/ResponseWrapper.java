package com.ishizuka.demo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWrapper<T> {

    private T data;

}
