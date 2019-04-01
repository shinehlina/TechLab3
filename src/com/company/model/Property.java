package com.company.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = "name")
@AllArgsConstructor
@ToString
public class Property<T> {
    private String name;
    private T value;
}
