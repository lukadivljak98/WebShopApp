package org.unibl.etf.webshop.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Filter {
    private String priceOd;
    private String priceDo;
    private String location;
    private String isNew;
    private String map;
    private String category;
}
