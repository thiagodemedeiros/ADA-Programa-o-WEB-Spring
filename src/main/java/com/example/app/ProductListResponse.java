package com.example.app;

import lombok.Data;
import java.util.List;

@Data
public class ProductListResponse {
    private List<Product> products;
    private int total;
    private int skip;
    private int limit;
}
